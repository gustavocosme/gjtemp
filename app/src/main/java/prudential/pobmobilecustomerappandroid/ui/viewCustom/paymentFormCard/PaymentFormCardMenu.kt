package prudential.pobmobilecustomerappandroid.ui.viewCustom.paymentFormCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.payment_form_card_menu_custom_view.view.*
import kotlinx.android.synthetic.main.payment_update_period.view.*
import prudential.pobmobilecustomerappandroid.R


/**
 * Class build to Menu Slide APP.
 */
class PaymentFormCardMenu(
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
        cachedView = mInflater.inflate(R.layout.payment_form_card_menu_custom_view, this, true)

        inicialize();
    }

    /**
     * Inicialie Views and objects.
     */
    fun inicialize() {

        cachedView.apply {


            btnDebit.setOnClickListener {

                PaymentFormCard.INSTANCE!!.showNav(PaymentFormCard.INSTANCE!!.debitBank);
            }


        }






    }


    /**
     * Remove all views from layout.
     */
    override fun removeAllViews() {


        super.removeAllViews()


    }






}//END CLASS

