package prudential.pobmobilecustomerappandroid.ui.activity.autentication.tutorial

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.showAnime
import prudential.pobmobilecustomerappandroid.model.DataTutorial

/**
 * Class to handle tutorial page with custom data.
 * @param context Context of class that is calling.
 */
class TutorialItem(context: Context) : RelativeLayout(context) {

    lateinit var cachedView: View;

    /**
     * Initial method.
     */
    init {
        inflate();
    }

    /**
     * Inflate custom layout.
     */
    private fun inflate() {
        val mInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        cachedView = mInflater.inflate(R.layout.activity_tutorial_item, this, true)

    }

    /**
     * Set custom data to the view inflated.
     * @param data [DataTutorial] with custom attributes.
     */
    fun set(data: DataTutorial) {
        cachedView.findViewById<TextView>(R.id.title).setText(data.title)
        cachedView.findViewById<TextView>(R.id.txtDescription).setText(data.description)
        cachedView.findViewById<ImageView>(R.id.icon).setImageResource(data.icon);
        cachedView.findViewById<ImageView>(R.id.icon).showAnime(800)
        cachedView.findViewById<TextView>(R.id.title).showAnime(1000)
        cachedView.findViewById<TextView>(R.id.txtDescription).showAnime(600)
    }
}

//class DataTutorial {
//    var title = "";
//    var description = "";
//    var icon: Int = -1;
//}