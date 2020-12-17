package prudential.pobmobilecustomerappandroid.ui.fragment


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import gustavocosme.ui.BaseFragment
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.*
import prudential.pobmobilecustomerappandroid.interfaces.OnRecyclerClickListener
import prudential.pobmobilecustomerappandroid.model.PaymentBasicaModel
import prudential.pobmobilecustomerappandroid.model.PolicyBasicModel
import prudential.pobmobilecustomerappandroid.model.Resource
import prudential.pobmobilecustomerappandroid.ui.activity.app.Tab
import prudential.pobmobilecustomerappandroid.ui.adapter.PaymentExtractAdapter
import prudential.pobmobilecustomerappandroid.ui.adapter.PolicyExtractAdapter
import prudential.pobmobilecustomerappandroid.ui.viewCustom.DataFilterExtract
import prudential.pobmobilecustomerappandroid.ui.viewmodel.PaymentViewModel
import prudential.pobmobilecustomerappandroid.ui.viewmodel.PolicyViewModel
import prudential.pobmobilecustomerappandroid.ui.viewmodel.PaymentViewModelFactory
import prudential.pobmobilecustomerappandroid.ui.viewmodel.PolicyViewModelFactory


/**
 * Fragment to represent extract screen.
 */
class ExtractFragment : BaseFragment(), OnRecyclerClickListener<PolicyBasicModel> {

    private val policyAdapter by lazy { PolicyExtractAdapter(this) }
    private val paymentAdapter by lazy { PaymentExtractAdapter() }
    private lateinit var paymentViewModel: PaymentViewModel
    private lateinit var policyViewModel: PolicyViewModel

    private lateinit var container: ConstraintLayout
    private lateinit var recyclerviewExtract: RecyclerView
    private lateinit var load: LottieAnimationView
    private lateinit var txtAviso0: TextView
    private lateinit var iconAviso0: ImageView
    private var paymentFilter: DataFilterExtract? = null

    /**
     * Inflate custom layout fragment.
     */
    init {
        setLayout(R.layout.fragment_extract);
    }

    /**
     * Initial methods
     */
    override fun init() {

        container = cachedView!!.findViewById(R.id.fragment_extract_root)
        txtAviso0 = cachedView!!.findViewById(R.id.txtAviso0)
        iconAviso0 = cachedView!!.findViewById(R.id.iconAviso0)
        recyclerviewExtract =
            cachedView!!.findViewById(R.id.bottom_sheet_extract_recyclerview)
        load = cachedView!!.findViewById(R.id.load)

        container.alpha = 0.0f;

        Looper.myLooper()?.let {
            Handler(it).postDelayed({
                container.showAnime();
                load.hideAnime({
                    load.pauseAnimation();
                });
                inicialize();
            }, 2000);
        }
    }

    /**
     * Initial main methods after animation
     */
    private fun inicialize() {
        initViewModels()
        openFilterListener()
        initBottomSheet()
        initPolicyRecyclerView()
        sortListListener()
        showOnlyOnePolicy()
    }

    /**
     * Show only one policy.
     */
    private fun showOnlyOnePolicy() {
        val title = cachedView?.findViewById<TextView>(R.id.fragment_extract_title)
        title?.setOnClickListener {
            if (policyAdapter.itemCount > 1) {
                val policies = mutableListOf(
                    PolicyBasicModel(
                        isSelected = true,
                        policyId = "000000001",
                        policyDescription = "Vida Inteira Mais",
                        policyInsuredName = "Sandra Regina Pereira",
                        isPaid = false
                    )
                )
                policyAdapter.submitList(policies)
            } else {
                initPolicyRecyclerView()
            }
        }
    }

    /**
     * set sort views listeners and handle it.
     */
    private fun sortListListener() {
        cachedView?.let {
            val sortIcon = it.findViewById<ImageView>(R.id.fragment_extract_sort_icon)
            val sortText = it.findViewById<TextView>(R.id.fragment_extract_sort_text)
            sortIcon.setOnClickListener {
                sortList(sortIcon, sortText)
            }
            sortText.setOnClickListener {
                sortList(sortIcon, sortText)
            }
        }
    }

    /**
     * Change views properties when list is sorted and call sortList on payment adapter.
     */
    private fun sortList(sortIcon: ImageView, sortText: TextView) {

        sortText.showAnimeToggle();
        sortIcon.showAnimeRotationToggle();
        recyclerviewExtract.showAnimeToggle(1500);

        paymentAdapter.sortList()
        if (paymentAdapter.isSorted()) {
            sortText.text = "Mais antigas"
        } else {
            sortText.text = "Mais recentes"
        }
    }

    /**
     * Init view models with application context.
     */
    private fun initViewModels() {
        activity?.application?.let {
            val paymentViewModel by viewModels<PaymentViewModel> { PaymentViewModelFactory(it) }
            val policyViewModel by viewModels<PolicyViewModel> { PolicyViewModelFactory(it) }
            this.paymentViewModel = paymentViewModel
            this.policyViewModel = policyViewModel
        }
    }

    /**
     * Handle click button to open side menu filter.
     */
    fun openFilterListener() {
        val filterButton = cachedView?.findViewById<Button>(R.id.fragment_extract_filter_button)
        val badge = cachedView?.findViewById<TextView>(R.id.fragment_extract_badge)
        filterButton?.setOnClickListener {
            Tab.INSTANCE.onOpenFilterExtract { filter ->
                paymentFilter = filter
                showBadgeAndFilter(filter, badge)
            }
        }
    }

    /**
     * Show bagde on filter button and filter list according to filter choice.
     * @param filter Fiter object with user choices.
     * @param badge Badge view to show how many filters are setted.
     */
    private fun showBadgeAndFilter(
        filter: DataFilterExtract,
        badge: TextView?
    ) {
        if (filter.count > 0) {
            badge?.apply {
                showAnimeToggle();
                showOrGone(true)
                text = filter.count.toString()
                paymentAdapter.filterPayments(filter)

                if (paymentAdapter.itemCount <= 0) {
                    txtAviso0.visibility = View.VISIBLE;
                    iconAviso0.visibility = View.VISIBLE;

                    txtAviso0.showAnime(1000);
                    iconAviso0.showAnime(1000);

                } else {

                    txtAviso0.visibility = View.GONE;
                    iconAviso0.visibility = View.GONE;
                }


            }
        } else {
            badge?.showOrGone(false)
            paymentAdapter.restoreState()

            if (paymentAdapter.itemCount <= 0) {
                txtAviso0.visibility = View.VISIBLE;
                iconAviso0.visibility = View.VISIBLE;

                txtAviso0.showAnime(1000);
                iconAviso0.showAnime(1000);

            } else {

                txtAviso0.visibility = View.GONE;
                iconAviso0.visibility = View.GONE;
            }


        }
    }

    /**
     * Init recycler view with policy adapter.
     */
    fun initPolicyRecyclerView() {
        val policyRecyclerView =
            cachedView?.findViewById<RecyclerView>(R.id.fragment_extract_policy_recycler)
        policyRecyclerView?.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = policyAdapter
        }
        initPolicyList()
    }

    /**
     * Init policy list with view model and live data
     */
    private fun initPolicyList() {
        policyViewModel.policies.observe(this, Observer {
            if (it is Resource.Success) {
                val policies = it.data
                showOrHideSubTitle(policies)
                policyAdapter.submitList(policies)
            }
        })
    }

    /**
     * Show or hide subtile if list has more than one item.
     * @param mockList List to be chacked.
     */
    private fun showOrHideSubTitle(mockList: List<PolicyBasicModel>) {
        val subtitle = cachedView?.findViewById<TextView>(R.id.fragment_extract_subtitle)
        subtitle?.showOrGone(mockList.size > 1)
    }

    /**
     * Init bottom sheet with payment list.
     */
    fun initBottomSheet() {
        val bottomSheetInclude =
            cachedView?.findViewById<View>(R.id.fragment_extract_include_bottomsheet)
        bottomSheetInclude?.let {
            val bottomSheet = BottomSheetBehavior.from(bottomSheetInclude)
            setCustomHeight(it, bottomSheet)
            initPaymentRecycler(bottomSheetInclude)
        }

    }

    /**
     * Show payment progress bar when data is loading.
     */
    private fun setPaymentProgressbar(bottomSheetInclude: View, visible: Boolean) {
        val progressBar = bottomSheetInclude.findViewById<View>(R.id.bottom_sheet_extract_loading)
        progressBar.showOrGone(visible)
    }

    /**
     * Init payment recycler with list.
     * @param bottomSheetInclude View to init recycler view.
     */
    private fun initPaymentRecycler(bottomSheetInclude: View) {
        val bottomSheetRecycler =
            bottomSheetInclude.findViewById<RecyclerView>(R.id.bottom_sheet_extract_recyclerview)
        bottomSheetRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = paymentAdapter
        }
        initPaymentList(bottomSheetInclude)
    }

    /**
     * Init payment list with view model and live data
     */
    private fun initPaymentList(bottomSheetInclude: View) {
        paymentViewModel.payments.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    paymentAdapter.clear()
                    setPaymentProgressbar(bottomSheetInclude, true)
                }
                is Resource.Success -> {
                    val payments = it.data
                    applyFilterAndSubmitData(payments, bottomSheetInclude)
                    setPaymentProgressbar(bottomSheetInclude, false)
                    recyclerviewExtract.showAnimeToggle(1500);

                    if (payments.size <= 0) {

                        txtAviso0.visibility = View.VISIBLE;
                        iconAviso0.visibility = View.VISIBLE;

                        txtAviso0.showAnime();
                        iconAviso0.showAnime();

                    } else {

                        txtAviso0.visibility = View.GONE;
                        iconAviso0.visibility = View.GONE;
                    }


                }
            }
        })
    }

    /**
     * Receive data and apply filter to all selected policies if filter object is not null.
     * @param payments List of payments to be applied.
     */
    private fun applyFilterAndSubmitData(
        payments: List<PaymentBasicaModel>,
        bottomSheetInclude: View
    ) {
        paymentAdapter.apply {
            clearFilter()
            submitList(payments)
            paymentFilter?.let {
                if (!it.isAllNull() or !it.isDateNull() or !it.isPaidNull()) {
                    filterPayments(it)
                }
            }
        }
        fakePaging(bottomSheetInclude)
    }

    /**
     * Simulate paging with current list.
     * @param bottomSheetInclude Reference to BottomSheet.
     */
    private fun fakePaging(bottomSheetInclude: View) {
        val bottomSheetRecycler =
            bottomSheetInclude.findViewById<RecyclerView>(R.id.bottom_sheet_extract_recyclerview)

        bottomSheetRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    setPaymentProgressbar(bottomSheetInclude, true)
                    Handler(Looper.getMainLooper()).postDelayed({
                        if (paymentAdapter.getPaymentsFullList().size != paymentAdapter.getPayments().size) {
                            paymentAdapter.loadMore()
                            setPaymentProgressbar(bottomSheetInclude, false)
                        }
                    }, 2000)
                }
            }
        })
    }


    /**
     *  Set custom height according to the layout size.
     *  @param bottomSheetInclude Root layout.
     *  @param bottomSheet BottomSheet element.
     */
    private fun setCustomHeight(
        bottomSheetInclude: View,
        bottomSheet: BottomSheetBehavior<View>
    ) {
        bottomSheetInclude.viewTreeObserver.addOnGlobalLayoutListener {
            cachedView?.findViewById<View>(R.id.constraintLayout4)?.bottom?.let {
                cachedView?.height?.let { height ->
                    bottomSheet.peekHeight = height - it
                }
            }
        }
    }


    /**
     * Handles policy element click.
     */
    override fun onRecyclerItemClick(model: PolicyBasicModel, position: Int) {
        model.isSelected = true
        policyAdapter.updateAtPosition(model, position)
        paymentViewModel.setPaymentIndex(position)
    }

    companion object {
        /**
         * Create new Instance of Fragent.
         * @param instance New instance with index.
         * @return New fragment created.
         */
        fun newInstance(instance: Int): ExtractFragment {
            val args = Bundle()
            args.putInt(ARGS_INSTANCE, instance)
            val fragment =
                ExtractFragment()
            fragment.setArguments(args)
            return fragment
        }
    }

}
