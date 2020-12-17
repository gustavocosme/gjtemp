package prudential.pobmobilecustomerappandroid.ui.viewCustom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.model.PolicyBasicModel
import prudential.pobmobilecustomerappandroid.ui.viewCustom.paymentFormCard.PaymentFormCard
import prudential.pobmobilecustomerappandroid.ui.viewCustom.paymentUpdatePeriod.PaymentUpdatePeriod

/**
 * Class build to Menu Slide APP.
 */
class SlideNav(context: Context, attrs: AttributeSet): RelativeLayout(context,attrs) {

    lateinit var cachedView: View;
    lateinit var containerG: ConstraintLayout;
    lateinit var container:RelativeLayout;
    lateinit var txtTitle:TextView;
    var paymentUpdatePeriod: PaymentUpdatePeriod? = null;
    var extractFilter: ExtractFilter? = null;
    var formPagament:PaymentFormCard? = null;

    companion object{

        var INTANCE:SlideNav? = null;

    }

    init {

        inflate();

    }

    /**
     * Inflate View Class.
     */
    private fun inflate() {

        val mInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        cachedView = mInflater.inflate(R.layout.slide_nav_view, this, true)

        inicialize();

    }

    /**
     * Inicialie Views and objects.
     */
    fun inicialize() {

        container = cachedView.findViewById(R.id.container) as RelativeLayout;
        txtTitle = cachedView.findViewById(R.id.txt_title) as TextView;
        containerG     = cachedView.findViewById(R.id.containerG) as ConstraintLayout;
        SlideNav.INTANCE = this;

    }

    /**
     * Inicialize Slide Period pagament.
     */
    fun inicializePeriod(policies: List<PolicyBasicModel>)
    {
        txtTitle.text  = "Frequência"
        paymentUpdatePeriod = PaymentUpdatePeriod(context, policies);
        container.addView(paymentUpdatePeriod);
    }

    /**
     * Inicialize Slide extract filter.
     */
    fun inicializeExtractFilter()
    {
        txtTitle.text  = "Filtros"
        extractFilter =
            ExtractFilter(
                context
            );
        container.addView(extractFilter);
    }

    /**
     * Inicialize Slide Form paymanet.
     */
    fun inicializeFormPayment(policies: List<PolicyBasicModel>)
    {
        txtTitle.text  = "Forma de pagamento"
        formPagament =
            PaymentFormCard(
                context
            ,policies);
        container.addView(formPagament);
    }

    /**
     * Clear Slide.
     */
    fun addClear()
    {
        if(paymentUpdatePeriod != null)
        {
            paymentUpdatePeriod!!.removeAllViews();
            paymentUpdatePeriod = null;
        }

        if(extractFilter != null)
        {
            extractFilter!!.removeAllViews();
            extractFilter = null;
        }

        if(formPagament != null)
        {
            formPagament!!.removeAllViews();
            formPagament = null;
        }


    }

    override fun removeAllViews() {
        super.removeAllViews()
        SlideNav.INTANCE = null;
    }



}//END CLASS

