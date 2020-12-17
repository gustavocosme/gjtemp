package prudential.pobmobilecustomerappandroid.ui.adapter

import java.util.ArrayList


import android.content.Context
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import prudential.pobmobilecustomerappandroid.ui.viewCustom.profile.ContactCustomView
import prudential.pobmobilecustomerappandroid.ui.viewCustom.profile.ProfessionalCustomView
import prudential.pobmobilecustomerappandroid.ui.viewCustom.profile.ProfileCustomView


class ProfileSlideAdapter(protected var mContext: Context) : PagerAdapter() {


    private val fragments = ArrayList<RelativeLayout>()
    private val fs = SparseArray<Any>();


    init {

        fragments.add(ProfileCustomView(mContext));
        fragments.add(ProfessionalCustomView(mContext));
        fragments.add(ContactCustomView(mContext));

    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        fs.remove(position)
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val v = fragments.get(position)
        container.addView(v)
        fs.put(position, v)
        return v

    }

    override fun getPageTitle(position: Int): CharSequence? {

        return ""

    }



}// END CLASS
