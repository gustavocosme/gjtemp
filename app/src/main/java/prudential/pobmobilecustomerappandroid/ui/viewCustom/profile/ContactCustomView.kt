package prudential.pobmobilecustomerappandroid.ui.viewCustom.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import prudential.pobmobilecustomerappandroid.R


/**
 * Class build to Extract Filter.
 */
class ContactCustomView(context: Context) : RelativeLayout(context) {

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
        cachedView = mInflater.inflate(R.layout.fragment_contract, this, true)

        inicialize();

    }

    /**
     * Inicialie Views and objects.
     */
    fun inicialize() {



    }



}