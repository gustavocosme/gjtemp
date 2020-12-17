package prudential.pobmobilecustomerappandroid.ui.fragment


import android.os.Bundle
import gustavocosme.ui.BaseFragment
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.showAnime
import prudential.pobmobilecustomerappandroid.extensions.showAnimeDelay
import prudential.pobmobilecustomerappandroid.ui.viewCustom.MenuSlide

class MoreFragment : BaseFragment() {

    /**
     * Inflate custom layout fragment.
     */
    init {

        title = "Menu"
        setLayout(R.layout.fragment_more);

    }

    /**
     * Initial methods anime
     */
    override fun init() {

        cachedView!!.findViewById<MenuSlide>(R.id.menu).showAnimeDelay()

    }

    /**
     * Create new Instance of Fragent.
     * @param instance New instance with index.
     * @return New fragment created.
     */
    companion object {

        fun newInstance(instance: Int): MoreFragment {
            val args = Bundle()
            args.putInt(ARGS_INSTANCE, instance)
            val fragment =
                MoreFragment()
            fragment.setArguments(args)
            return fragment
        }

    }


}//END CLASS
