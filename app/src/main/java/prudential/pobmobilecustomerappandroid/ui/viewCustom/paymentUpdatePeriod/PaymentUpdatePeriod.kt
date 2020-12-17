package prudential.pobmobilecustomerappandroid.ui.viewCustom.paymentUpdatePeriod

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.showAnime
import prudential.pobmobilecustomerappandroid.extensions.showOrGone
import prudential.pobmobilecustomerappandroid.model.PolicyBasicModel

/**
 * Class build to Menu Slide APP.
 */
class PaymentUpdatePeriod(
    context: Context,
    val policies: List<PolicyBasicModel>
): RelativeLayout(context) {

    lateinit var cachedView: View;
    lateinit var selectPaymentUpdate: PaymentUpdatePeriodSelect;
    lateinit var ok: PaymentUpdatePeriodOk;
    lateinit var resume: PaymentUpdatePeriodOkResume;

//    var array: List<PolicyBasicModel>

//    init {
//
//        array = policies;
//
//    }

    companion object{

        var INSTANCE:PaymentUpdatePeriod? = null;

    }


    enum class TypePeriod{
        PERIOD, OK, RESUME
    }

    var currentNav = TypePeriod.PERIOD;

    /**
     * Default init method.
     */
    init {
        inflate();
    }

    /**
     * Inflate View Class.
     */
    private fun inflate() {

        val mInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        cachedView = mInflater.inflate(R.layout.payment_update_period, this, true)

        inicialize();



        PaymentUpdatePeriod.INSTANCE = this;
    }



    /**
     * Inicialie Views and objects.
     */
    fun inicialize() {
        ok             = findViewById(R.id.ok) as PaymentUpdatePeriodOk;
        selectPaymentUpdate         = findViewById(R.id.select) as PaymentUpdatePeriodSelect;
        resume         = findViewById(R.id.resume) as PaymentUpdatePeriodOkResume;



    }

    /**
     * Change visibility of OK item.
     */
    fun onNavOk()
    {
        ok.onRemoveFieldSelect();
        resume.showOrGone(false);
        selectPaymentUpdate.showOrGone(false);
        ok.showAnime()
        currentNav = TypePeriod.OK;
    }

    /**
     * Change visibility of item.
     */
    fun onNavSelect()
    {
        resume.showOrGone(false);
        ok.showOrGone(false);
        selectPaymentUpdate.showAnime()
    }

    /**
     * Change visibility of item.
     */
    fun onNavResume()
    {
        selectPaymentUpdate.showOrGone(false);
        ok.showOrGone(false);
        resume.showAnime();
    }

    /**
     * Remove all view from layout.
     */
    override fun removeAllViews() {

        INSTANCE = null;

        ok.removeAllViews();
        resume.removeAllViews();
        selectPaymentUpdate.removeAllViews();

        super.removeAllViews()


    }






}//END CLASS

