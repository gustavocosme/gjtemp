package prudential.pobmobilecustomerappandroid.ui.viewCustom.paymentFormCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.payment_form_card_debit_ok.view.*
import kotlinx.android.synthetic.main.payment_form_card_debit_ok.view.btnNext
import kotlinx.android.synthetic.main.payment_form_card_debit_ok.view.button2
import kotlinx.android.synthetic.main.payment_form_card_debit_ok.view.layoutDay
import kotlinx.android.synthetic.main.payment_form_card_debit_ok.view.txt2
import kotlinx.android.synthetic.main.payment_update_period_ok_custom_view.view.*
import lt.neworld.spanner.Spanner
import lt.neworld.spanner.Spans
import lt.neworld.spanner.Spans.font
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.showAnime
import prudential.pobmobilecustomerappandroid.extensions.showOrGone
import prudential.pobmobilecustomerappandroid.interfaces.OnRecyclerClickListener
import prudential.pobmobilecustomerappandroid.model.PolicyBasicModel
import prudential.pobmobilecustomerappandroid.ui.activity.app.Tab
import prudential.pobmobilecustomerappandroid.ui.adapter.PolicyEditDataAdapter
import prudential.pobmobilecustomerappandroid.ui.viewCustom.paymentUpdatePeriod.PaymentUpdatePeriod

/**
 * Class build to Menu Slide APP.
 */
class PaymentFormCardOk(
    context: Context,
    attrs: AttributeSet
) : RelativeLayout(context, attrs), OnRecyclerClickListener<PolicyBasicModel> {

    lateinit var cachedView: View

    lateinit var list: RecyclerView
    lateinit var listCard: MaterialCardView
    var isList = false
    var isClose = true
    private val policyEditDataAdapter by lazy { PolicyEditDataAdapter(this) }


    init {

        inflate()

    }

    /**
     * Inflate View Class.
     */
    private fun inflate() {

        val mInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        cachedView = mInflater.inflate(R.layout.payment_form_card_debit_ok, this, true)

        inicialize()

    }

    /**
     * Inicialie Views and objects.
     */
    fun inicialize() {

        list = cachedView.findViewById(R.id.list) as RecyclerView
        listCard = cachedView.findViewById(R.id.listCard) as MaterialCardView
        listCard.showOrGone(false)

        cachedView.apply {

            btnSelect.setOnClickListener {
                onClickSelect()
            }

            btnNext.setOnClickListener {
                if(isClose)
                {
                    Tab.INSTANCE.onCloseSlide();
                }else{
                    PaymentFormCard.INSTANCE!!.showNav(PaymentFormCard.INSTANCE!!.debitResume);

                }
            }

            kotlin.run {

                val spannable =
                    Spanner("Deseja usar forma de pagamaneto para seus outros seguros? (opcional)")
                        .span(
                            "(opcional)",
                            Spans.foreground(context.getColor(R.color.colorGrayLight))
                        )
                        .span("(opcional)", Spans.sizeDP(14))
                        .span("(opcional)", font("sans-serif-light"))

                txt2.text = spannable

            }


        }

        initRecyclerView()
        onRemoveFieldSelect()

    }

    /**
     * Change visibility of field if is null or empty.
     */
    fun onRemoveFieldSelect() {

        val show = !PaymentFormCard.INSTANCE?.policies.isNullOrEmpty()

        cachedView.apply {
            txt2.showOrGone(show);
            layoutDay.showOrGone(show);
            btnSelect.showOrGone(show);

        }



    }


    /**
     * Init recycler view and adapter.
     */
    fun initRecyclerView() {



        list.apply {
            layoutManager = LinearLayoutManager(cachedView.context)
            adapter = policyEditDataAdapter




        }

        PaymentFormCard.INSTANCE?.policies?.let {
            val newList = mutableListOf<PolicyBasicModel>().apply {
                add(0, PolicyBasicModel())
                addAll(it)
            }
            policyEditDataAdapter.submitList(newList)
        }


        policyEditDataAdapter.onSelectChange = {

            if (it.count() == 0) {
                isClose = true

                cachedView.apply {

                    btnNext.text = "Fechar"

                }

            } else {

                cachedView.apply {
                    btnNext.text = "Salvar"
                }
                isClose = false;
            }

        }



    }




    /**
     * On cliclk select slide
     */
    fun onClickSelect() {

        if (isList) {
            listCard.showOrGone(false)
            isList = false
        } else {
            listCard.showOrGone(true)
            listCard.showAnime()
            isList = true
            initRecyclerView()
        }

    }

    /**
     * Handle item click.
     * @param model Item clicked at position.
     * @param position Position of item.
     */
    override fun onRecyclerItemClick(model: PolicyBasicModel, position: Int) {
        model.isSelected = !model.isSelected
        policyEditDataAdapter.checkAtPosition(model, position)
    }

}//END CLASS

