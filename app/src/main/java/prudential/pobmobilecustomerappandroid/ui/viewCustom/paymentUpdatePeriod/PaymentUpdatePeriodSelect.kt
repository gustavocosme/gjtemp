package prudential.pobmobilecustomerappandroid.ui.viewCustom.paymentUpdatePeriod

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.payment_update_period_select_custom_view.view.*
import lt.neworld.spanner.Spanner
import lt.neworld.spanner.Spans.*
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.alertCustom
import prudential.pobmobilecustomerappandroid.extensions.showAnime
import prudential.pobmobilecustomerappandroid.extensions.showOrGone
import prudential.pobmobilecustomerappandroid.interfaces.OnRecyclerClickListener
import prudential.pobmobilecustomerappandroid.ui.activity.app.Tab
import prudential.pobmobilecustomerappandroid.ui.adapter.SimpleSelectAdapter
import prudential.pobmobilecustomerappandroid.ui.viewCustom.SlideNav
import prudential.pobmobilecustomerappandroid.utils.TypeAlertCustom

/**
 * Class build to Menu Slide APP.
 */
class PaymentUpdatePeriodSelect(
    context: Context,
    attrs: AttributeSet
) : RelativeLayout(context, attrs), OnRecyclerClickListener<String> {

    lateinit var containerG: ConstraintLayout;
    lateinit var cachedView: View;
    lateinit var icMonth: ImageView;
    lateinit var icYear: ImageView;
    lateinit var list: RecyclerView;
    lateinit var listCard: CardView;
    lateinit var txtDay: TextView;
    var isList = false;
    var isYear = false;

    private val selectDayAdapter by lazy { SimpleSelectAdapter(this) }

    companion object {
        var INSTANCE: PaymentUpdatePeriodSelect? = null;
    }

    init {
        inflate()
    }

    /**
     * Inflate View Class.
     */
    private fun inflate() {

        val mInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        cachedView = mInflater.inflate(R.layout.payment_update_period_select_custom_view, this, true)

        inicialize();
    }

    /**
     * Inicialie Views and objects.
     */
    fun inicialize() {

        containerG = cachedView.findViewById(R.id.containerG) as ConstraintLayout;
        list = cachedView.findViewById(R.id.list) as RecyclerView;
        listCard = cachedView.findViewById(R.id.listCard) as CardView;
        icMonth = cachedView.findViewById(R.id.icMensal) as ImageView;
        icYear = cachedView.findViewById(R.id.icAnual) as ImageView;
        txtDay = cachedView.findViewById(R.id.txtDay) as TextView;

        cachedView.apply {

            btnMensal.setOnClickListener { onMonth() }
            btnAnual.setOnClickListener { onYear() }

            btnDay.setOnClickListener {
                onClickDay();
            }

            btnBack.setOnClickListener {
                Tab.INSTANCE.onCloseSlide();
            }

            btnNext.setOnClickListener {
                PaymentUpdatePeriod.INSTANCE!!.onNavOk();
            }


            val spannable =
                Spanner("ATENÇÃO: Ao trocar para pagamento anual, o valor a ser pago será proporcional aos meses restantes até o aniversário da apólice (mês em que a apólices foi emitida). O valor apresentado ainda não considera esse cálculo. Caso haja cobranças(s) em aberto, elas(s) será(ão) reemitida(s)")
                    .span("ATENÇÃO", bold())
                    .span("ATENÇÃO", foreground(context.getColor(R.color.colorBlackText)))
                    .span("ATENÇÃO", sizeDP(15))

            txtMessage.setText(spannable);
        }

        listCard.showOrGone(false)

        INSTANCE = this;

        initRecyclerView()

    }

    /**
     * Init recycler view with mock data.
     */
    private fun initRecyclerView() {
        list.apply {
            layoutManager = LinearLayoutManager(cachedView.context)
            adapter = selectDayAdapter
        }
        val days = mutableListOf<String>()
        for (i in 1..31) {
            days.add("$i")
        }
        selectDayAdapter.submitList(days)
    }

    /**
     * Show or hide dropdown with list of days.
     */
    fun onClickDay() {

        if (isList) {
            listCard.showOrGone(false);
            isList = false;

        } else {

            listCard.showOrGone(true);
            listCard.showAnime();
            isList = true;

        }
    }

    /**
     * Change option to month and hide anual itens.
     */
    fun onMonth() {

        isYear = false;
        icYear.setImageResource(R.drawable.ic_form_radio_check_normal)
        icMonth.setImageResource(R.drawable.ic_form_radio_check_select)

        cachedView.apply {

            txt2.showOrGone(true);
            layoutDay.showOrGone(true);
            txtMessage.showOrGone(false);

            txt2.showAnime()
            layoutDay.showAnime()

        }

        PaymentUpdatePeriod.INSTANCE!!.ok.onChangeYear();
        PaymentUpdatePeriod.INSTANCE!!.resume.onChangeYear();

    }

    /**
     * Change option o anual and hide month itens.
     */
    fun onYear() {
        isYear = true;
        listCard.showOrGone(false);
        isList = false;

        icYear.setImageResource(R.drawable.ic_form_radio_check_select)
        icMonth.setImageResource(R.drawable.ic_form_radio_check_normal)

        cachedView.apply {

            txt2.showOrGone(false);
            layoutDay.showOrGone(false);
            txtMessage.showOrGone(true);
            txtMessage.showAnime();

            btnBack.setOnClickListener {
                Tab.INSTANCE.onCloseSlide();
            }

            btnNext.setOnClickListener {

                PaymentUpdatePeriod.INSTANCE!!.onNavOk()
            }


        }

        PaymentUpdatePeriod.INSTANCE!!.ok.onChangeYear();
        PaymentUpdatePeriod.INSTANCE!!.resume.onChangeYear();
    }

    /**
     * Model events
     */
    fun initModel() {
        SlideNav.INTANCE?.containerG?.alertCustom("Alterado com sucesso!", TypeAlertCustom.OK);
    }


    /**
     * Remove all views from layout.
     */
    override fun removeAllViews() {
        super.removeAllViews()

        INSTANCE = null;

    }

    /**
     * Handle recycler view item click.
     * @param model Item clicked at position.
     * @param position Position of item.
     */
    override fun onRecyclerItemClick(model: String, position: Int) {
        txtDay.text = "Dia $model"
        listCard.showOrGone(false)
        isList = false
        PaymentUpdatePeriod.INSTANCE!!.ok.addDay("Dia $model");
        PaymentUpdatePeriod.INSTANCE!!.resume.addDay("Dia $model");
    }

}//END CLASS

