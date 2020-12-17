package prudential.pobmobilecustomerappandroid.ui.viewCustom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.view.menu.MenuAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.ui.adapter.MenuSlideAdapter
import prudential.pobmobilecustomerappandroid.ui.adapter.PaymentExtractAdapter

/**
 * Class build to Menu Slide APP.
 */
class MenuSlide(context: Context, attrs: AttributeSet): RelativeLayout(context,attrs) {

    lateinit var cachedView: View;
    private val adp by lazy { MenuSlideAdapter() }


    init {

        inflate();

    }

    /**
     * Inflate View Class.
     */
    private fun inflate() {

        val mInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        cachedView = mInflater.inflate(R.layout.menu_slide, this, true)

        inicialize();

    }

    /**
     * Inicialie Views and objects.
     */
    fun inicialize() {

        val list = cachedView.findViewById<RecyclerView>(R.id.list);
        list.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = adp
        }

    }

}//END CLASS

