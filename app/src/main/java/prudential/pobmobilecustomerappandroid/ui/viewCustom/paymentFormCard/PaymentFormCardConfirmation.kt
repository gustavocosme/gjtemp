package prudential.pobmobilecustomerappandroid.ui.viewCustom.paymentFormCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.payment_form_card.view.*
import kotlinx.android.synthetic.main.payment_form_card_debit_confirmation.view.*
import prudential.pobmobilecustomerappandroid.R


/**
 * Class build to Menu Slide APP.
 */
class PaymentFormCardConfirmation(
    context: Context, attrs: AttributeSet
): RelativeLayout(context,attrs) {

    lateinit var cachedView: View



    init {
        inflate();
    }

    /**
     * Inflate View Class.
     */
    private fun inflate() {

        val mInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        cachedView = mInflater.inflate(R.layout.payment_form_card_debit_confirmation, this, true)

        inicialize();
    }

    /**
     * Inicialie Views and objects.
     */
    fun inicialize() {

        cachedView.apply {

            btnNext.setOnClickListener {
                PaymentFormCard.INSTANCE!!.debitOk.onRemoveFieldSelect();
                PaymentFormCard.INSTANCE!!.showNav(PaymentFormCard.INSTANCE!!.debitOk);
                PaymentFormCard.INSTANCE!!.showNav(PaymentFormCard.INSTANCE!!.debitOk);
            }
            btnBack.setOnClickListener {
                PaymentFormCard.INSTANCE!!.showNav(PaymentFormCard.INSTANCE!!.bank);
            }

            car2.select();

        }


    }


    /**
     * Remove all views from layout.
     */
    override fun removeAllViews() {


        super.removeAllViews()


    }






}//END CLASS

