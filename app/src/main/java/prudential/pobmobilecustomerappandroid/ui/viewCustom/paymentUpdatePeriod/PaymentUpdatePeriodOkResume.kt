package prudential.pobmobilecustomerappandroid.ui.viewCustom.paymentUpdatePeriod

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.payment_update_period_ok_resume_custom_view.view.*
import kotlinx.android.synthetic.main.payment_update_period_ok_resume_item_custom_view.view.*
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.showOrGone
import prudential.pobmobilecustomerappandroid.model.PolicyBasicModel
import prudential.pobmobilecustomerappandroid.ui.activity.app.Tab

/**
 * Class build to Menu Slide APP.
 */
class PaymentUpdatePeriodOkResume(context: Context, attrs: AttributeSet): RelativeLayout(context,attrs) {

    lateinit var cachedView: View;

    var views = ArrayList<View>();

    init {
        inflate();
    }

    /**
     * Inflate View Class.
     */
    private fun inflate() {
        val mInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        cachedView = mInflater.inflate(R.layout.payment_update_period_ok_resume_custom_view, this, true)
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
     * Add list of policcies.
     * @param array List of policies.
     */
    fun addArray(array:ArrayList<PolicyBasicModel>)
    {
        cachedView.apply {

            for(i in views)
            {
                container.removeView(i);
            }

            views.clear();

            for(i in array)
            {
                val item = LayoutInflater.from(context).inflate(R.layout.payment_update_period_ok_resume_item_custom_view, null, false)
                item.apply {

                    txt.text = i.policyDescription;
                }

                views.add(item);
                container.addView(item);
            }

        }

    }

    /**
     * Add day value.
     * @param value Day selected.
     */
    fun addDay(value:String)
    {
        cachedView.apply {

            txtAnualV.text = value;

        }
    }


    /**
     * Hide yeay selected elements.
     */
    fun onChangeYear()
    {
        cachedView.apply {

            if(PaymentUpdatePeriod.INSTANCE!!.selectPaymentUpdate.isYear)
            {
                txtAnual.showOrGone(false);
                txtAnualV.showOrGone(false);
            }


        }
    }





}//END CLASS

