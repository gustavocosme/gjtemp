package prudential.pobmobilecustomerappandroid.ui.viewCustom.paymentFormCard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import org.jetbrains.anko.view
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.showAnime
import prudential.pobmobilecustomerappandroid.extensions.showOrGone
import prudential.pobmobilecustomerappandroid.model.PolicyBasicModel

/**
 * Class build to Menu Slide APP.
 */
class PaymentFormCard(
    context: Context,
    val policies: List<PolicyBasicModel>
): RelativeLayout(context) {






    lateinit var cachedView: View

    lateinit var menu:PaymentFormCardMenu;
    lateinit var debitBank:PaymentFormCardBank;
    lateinit var debitConfirmation:PaymentFormCardConfirmation;
    lateinit var debitOk:PaymentFormCardOk;
    lateinit var debitResume:PaymentFormCardOkResume;
    var views:ArrayList<RelativeLayout> = ArrayList();

    init {
        inflate();
    }

    /**
     * Inflate View Class.
     */
    private fun inflate() {

        val mInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        cachedView = mInflater.inflate(R.layout.payment_form_card,this, true)

        inicialize();
    }

    /**
     * Inicialie Views and objects.
     */
    fun inicialize() {

        menu = cachedView.findViewById(R.id.menu) as PaymentFormCardMenu;
        debitBank = cachedView.findViewById(R.id.bank) as PaymentFormCardBank
        debitConfirmation = cachedView.findViewById(R.id.confirmation) as PaymentFormCardConfirmation;
        debitOk = cachedView.findViewById(R.id.Ok) as PaymentFormCardOk;
        debitResume = cachedView.findViewById(R.id.OkResume) as PaymentFormCardOkResume;

        views.add(menu);
        views.add(debitBank);
        views.add(debitConfirmation);
        views.add(debitOk);
        views.add(debitResume);

        INSTANCE = this;

    }

    /**
     * Show payment options menu.
     */
    fun showNav(view:RelativeLayout)
    {
        for(i in views)
        {
            i.visibility = View.GONE;
        }

        view.showAnime();
    }

    /**
     * Remove all views from layout.
     */
    override fun removeAllViews() {

        INSTANCE = null;

        super.removeAllViews()

    }


    companion object{

        var INSTANCE:PaymentFormCard? = null;

    }




}//END CLASS

