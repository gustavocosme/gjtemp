package prudential.pobmobilecustomerappandroid.ui.viewCustom.paymentFormCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.dialog_extract_ok.view.*
import kotlinx.android.synthetic.main.payment_form_card_item.view.*
import kotlinx.android.synthetic.main.payment_form_card_item.view.txt1
import kotlinx.android.synthetic.main.payment_form_card_item.view.txt2
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.showAnime
import prudential.pobmobilecustomerappandroid.extensions.showOrGone
import prudential.pobmobilecustomerappandroid.model.PolicyBasicModel

/**
 * Class build to Menu Slide APP.
 */
class PaymentFormCardItem(
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
        cachedView = mInflater.inflate(R.layout.payment_form_card_item, this, true)

        inicialize();
    }

    /**
     * Inicialie Views and objects.
     */
    fun inicialize() {

    }

    /**
     * Set data to the select view.
     */
    fun select()
    {
        cachedView.apply {
            txt1.setText("Débito em conta");
            txt2.setText("Ag: 2283 Conta: 28398475-8");
            txt3.setText("Maura da Silva");
            card.setCardBackgroundColor(context.getColor(R.color.colorBlueBackCard));
            card.setBackgroundColor(context.getColor(R.color.colorBlueBackCard));
            card.strokeColor = context.getColor(R.color.colorPrimary)
            ic.setImageResource(R.drawable.ic_bradesco)
        }

    }

    /**
     * Remove all views from layout.
     */
    override fun removeAllViews() {


        super.removeAllViews()


    }






}//END CLASS

