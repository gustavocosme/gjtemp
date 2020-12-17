package prudential.pobmobilecustomerappandroid.ui.fragment


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import gustavocosme.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_user.view.*
import kotlinx.android.synthetic.main.menu_slide_header.view.*
import org.jetbrains.anko.support.v4.toast
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.ui.adapter.ProfileSlideAdapter
import prudential.pobmobilecustomerappandroid.utils.Image


/**
 * Fragment subclass to represent fragment view
 * and it's actions.
 */
class UserFragment : BaseFragment() {



    /**
     * Init method to set layout to the chacedView variable.
     */
    init {
        setLayout(R.layout.fragment_user);
    }

    /**
     * Called inside onCreateView.
     * It's called once, if the view was not created yet.
     */
    override fun init() {

        cachedView!!.apply {

            Image.load(foto,"http://gustavocosme.com/api_9873246uyetkjhertiuy49567/foto.png")
            pager.adapter = ProfileSlideAdapter(context)
            tab.indicatorHeight = 10;
            //tab.textSize = 44;
            tab.textColor = context.getColor(R.color.colorPrimary)
            val typeface = ResourcesCompat.getFont(context, R.font.opensans_regular)
            tab.setTypeface(typeface,Typeface.NORMAL)
            tab.setViewPager(pager);
            initPagerAnimation(pager, listOf<TextView>(tm1,tm2,tm3))

        }

    }

    fun initPagerAnimation(pager:ViewPager, txts: List<TextView>)
    {

        pager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {


            override fun onPageSelected(position: Int) {

                val typeface = ResourcesCompat.getFont(activity!!, R.font.opensans_regular)

                for (i in txts)
                {
                    i.setTypeface(typeface)
                }

                val typefaceB = ResourcesCompat.getFont(activity!!, R.font.opensans_bold)
                txts[position].setTypeface(typefaceB)
            }
        })


    }


    /**
     * Static method do create instance of UserFragment.
     */
    companion object {

        /**
         * Create instance of UserFragment.
         * @param instance Index of instance to be created.
         * @return UserFragment created.
         */
        fun newInstance(instance: Int): UserFragment {
            val args = Bundle()
            args.putInt(ARGS_INSTANCE, instance)
            val fragment =
                UserFragment()
            fragment.setArguments(args)
            return fragment
        }

    }
}
