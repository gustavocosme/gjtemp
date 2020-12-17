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
import prudential.pobmobilecustomerappandroid.ui.viewCustom.paymentUpdatePeriod.PaymentUpdatePeriod

/**
 * Class build to Menu Slide APP.
 */
class Protocol(context: Context, attrs: AttributeSet): RelativeLayout(context,attrs) {

    lateinit var cachedView: View;


    init {

        inflate();

    }

    /**
     * Inflate View Class.
     */
    private fun inflate() {

        val mInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        cachedView = mInflater.inflate(R.layout.protocal, this, true)

        inicialize();

    }

    /**
     * Inicialie Views and objects.
     */
    fun inicialize() {



    }



}//END CLASS

