package prudential.pobmobilecustomerappandroid.ui.viewCustom.paymentFormCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.payment_form_card_debit_ok_resume.view.*
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.ui.activity.app.Tab


/**
 * Class build to Menu Slide APP.
 */
class PaymentFormCardOkResume(
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
        cachedView = mInflater.inflate(R.layout.payment_form_card_debit_ok_resume, this, true)

        inicialize();
    }

    /**
     * Inicialie Views and objects.
     */
    fun inicialize() {

        cachedView.apply {

            btnNext.setOnClickListener {
                Tab.INSTANCE.onCloseSlide();
            }

        }


    }


    /**
     * Remove views from layout.
     */
    override fun removeAllViews() {


        super.removeAllViews()


    }






}//END CLASS

