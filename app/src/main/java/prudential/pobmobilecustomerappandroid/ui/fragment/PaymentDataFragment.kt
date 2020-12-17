package prudential.pobmobilecustomerappandroid.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import gustavocosme.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_payment_update_data.*
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.hideAnime
import prudential.pobmobilecustomerappandroid.extensions.showAnime
import prudential.pobmobilecustomerappandroid.extensions.showOrGone
import prudential.pobmobilecustomerappandroid.interfaces.OnRecyclerClickListener
import prudential.pobmobilecustomerappandroid.model.PolicyBasicModel
import prudential.pobmobilecustomerappandroid.model.Resource
import prudential.pobmobilecustomerappandroid.ui.activity.app.Tab
import prudential.pobmobilecustomerappandroid.ui.adapter.PaymentDataAdapter
import prudential.pobmobilecustomerappandroid.ui.adapter.PaymentDataType
import prudential.pobmobilecustomerappandroid.ui.adapter.PolicyExtractAdapter
import prudential.pobmobilecustomerappandroid.ui.viewmodel.PolicyViewModel
import prudential.pobmobilecustomerappandroid.ui.viewmodel.PolicyViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [PaymentDataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PaymentDataFragment : BaseFragment(), OnRecyclerClickListener<PolicyBasicModel> {

    private val policyAdapter by lazy { PolicyExtractAdapter(this, true,false) }
    private val paymentMethodAdapter by lazy { PaymentDataAdapter(PaymentDataType.PAYMENT_METHOD) }
    private val paymentOftendAdapter by lazy { PaymentDataAdapter(PaymentDataType.OFTEN) }
    private val paymentAddressdapter by lazy { PaymentDataAdapter(PaymentDataType.ADDRESS) }
    private val policyViewModel by viewModels<PolicyViewModel> {
        PolicyViewModelFactory(
            requireActivity().application
        )
    }
    private lateinit var unSelectedPayments: List<PolicyBasicModel>
    /**
     * Inflate custom layout fragment.
     */
    init {
        setLayout(R.layout.fragment_payment_update_data);
    }

    override fun init() {
        initPolicyRecyclerView()
        initPaymentsSelectedList()
        hideBanner()
        editFrequencyButton();
        editFormPagamentButton();
        selectFirstPolicy()
        initAnime();

    }

    fun initAnime()
    {
        val container  = cachedView!!.findViewById(R.id.containerGG) as ConstraintLayout
        val load: LottieAnimationView  = cachedView!!.findViewById(R.id.load)
        container.alpha = 0.0f;

        Looper.myLooper()?.let {
            Handler(it).postDelayed({
                container.showAnime();
                load.hideAnime({
                    load.pauseAnimation();
                });
            }, 2000);
        }
    }

    private fun selectFirstPolicy() {
        //policyViewModel.setPolicySelectedIndex(mutableListOf(0))

    }

    private fun editFrequencyButton() {
        val editFrequencyButton = cachedView?.findViewById<Button>(R.id.fragment_payment_data_button_date)
        editFrequencyButton?.setOnClickListener {
            Tab.INSTANCE.onOpenPeriod(policyAdapter.getAllUnselectItens())
        }
    }

    private fun editFormPagamentButton() {
        val editFrequencyButton = cachedView?.findViewById<Button>(R.id.fragment_payment_data_button_pay_method)
        editFrequencyButton?.setOnClickListener {
            Tab.INSTANCE.onOpenPagamentForm(policyAdapter.getAllUnselectItens())
        }
    }

    private fun hideBanner() {
        val bannerCard = cachedView?.findViewById<CardView>(R.id.fragment_payment_data_banner_card)
        val bannerCardCloseButton =
            cachedView?.findViewById<ImageView>(R.id.fragment_pament_data_close_button)
        bannerCardCloseButton?.setOnClickListener {
            bannerCard?.showOrGone(false)
        }
    }

    /**
     * Init recycler view with policy adapter.
     */
    fun initPolicyRecyclerView() {
        val policyRecyclerView =
            cachedView?.findViewById<RecyclerView>(R.id.fragment_payment_data_policy_recycler)
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
                policies.add(0, PolicyBasicModel())
                policyAdapter.submitList(policies)
            }
        })
    }

    /**
     * getSelect payments.
     */
    private fun initPaymentsSelectedList() {
        policyViewModel.policiesSelected.observe(this, {
            when (it) {
                is Resource.Success -> {
                    val selectedPayments = it.data
                    initPaymentMethodAdapter(selectedPayments)
                    initPaymentOftenAdapter(selectedPayments)
                    initPaymentAddressAdapter(selectedPayments)
                }
            }
        })
    }

    private fun initPaymentMethodAdapter(payments: List<PolicyBasicModel>) {
        val payRecycler =
            cachedView?.findViewById<RecyclerView>(R.id.fragment_payment_data_recycler_pay_method)
        val subtitle = cachedView?.findViewById<TextView>(R.id.fragment_payment_data_subtitle_pay_method)
        val group = cachedView?.findViewById<Group>(R.id.fragment_payment_data_group_list)
        initRecyclerAndAdapterPaymentItem(
            payRecycler,
            paymentMethodAdapter,
            payments,
            subtitle,
            group
        )
    }

    private fun initPaymentOftenAdapter(payments: List<PolicyBasicModel>) {
        val oftenRecycler =
            cachedView?.findViewById<RecyclerView>(R.id.fragment_payment_data_recycler_date)
        val subtitle = cachedView?.findViewById<TextView>(R.id.fragment_payment_data_subtitle_date)
        val group = cachedView?.findViewById<Group>(R.id.fragment_payment_data_date_group_list)
        initRecyclerAndAdapterPaymentItem(
            oftenRecycler,
            paymentOftendAdapter,
            payments,
            subtitle,
            group
        )
    }

    private fun initPaymentAddressAdapter(payments: List<PolicyBasicModel>) {
        val addressRecycler =
            cachedView?.findViewById<RecyclerView>(R.id.fragment_payment_data_recycler_address)
        val subtitle = cachedView?.findViewById<TextView>(R.id.fragment_payment_data_subtitle_address)
        val group = cachedView?.findViewById<Group>(R.id.fragment_payment_data_address_group_list)
        initRecyclerAndAdapterPaymentItem(
            addressRecycler,
            paymentAddressdapter,
            payments,
            subtitle,
            group
        )
    }

    private fun initRecyclerAndAdapterPaymentItem(
        recyclerView: RecyclerView?,
        paymentAdapter: PaymentDataAdapter,
        payments: List<PolicyBasicModel>,
        subtitle: TextView?,
        group: Group?
    ) {
        subtitle?.showOrGone(payments.isEmpty())
        group?.showOrGone(payments.isNotEmpty())
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = paymentAdapter
        }
        paymentAdapter.submitList(payments)
    }


    /**
     * Handle policy recycler list click.
     * @param model PolicyBasicModel.
     * @param position Item position when cliced.
     */
    override fun onRecyclerItemClick(model: PolicyBasicModel, position: Int) {
        model.isSelected = !model.isSelected
        policyAdapter.checkAtPosition(model, position)
        policyViewModel.setPolicySelectedIndex(policyAdapter.getAllSelectItens())
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment PaymentDataFragment.
         */
        fun newInstance(instance: Int): PaymentDataFragment {
            val args = Bundle()
            args.putInt(ARGS_INSTANCE, instance)
            val fragment =
                PaymentDataFragment()
            fragment.setArguments(args)
            return fragment
        }
    }
}