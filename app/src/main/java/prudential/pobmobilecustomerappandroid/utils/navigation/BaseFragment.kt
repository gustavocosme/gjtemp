package gustavocosme.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    lateinit var mFragmentNavigation: FragmentNavigation
    var mInt = 0
    var cachedView: View? = null;
    var layoutA:Int = 0;
    var isLogo: Boolean? = true
    var title = ""
    var isInit: Boolean = false;

    fun setLayout(layoutA: Int) {
        this.layoutA = layoutA
    }

    open fun onBack()
    {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        if (args != null) {
            mInt = args.getInt(ARGS_INSTANCE)
        }
    }

    override fun onAttach(context:Context) {

        super.onAttach(context)

        if (context is FragmentNavigation) {
            mFragmentNavigation = context
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (cachedView == null) {


            cachedView = inflater.inflate(layoutA, container, false)
            setHasOptionsMenu(true)



            if ((!isInit)) {


                init()

                //ViewHelper.setAlpha(cachedView!!, 0f)
                //com.nineoldandroids.view.ViewPropertyAnimator.animate(cachedView).setDuration(1200)
                    //.alpha(1f)
            }


        }




        return cachedView
    }


    open fun init() {


    }

    interface FragmentNavigation {
        fun pushFragment(fragment: Fragment)
    }

    companion object {


        val ARGS_INSTANCE = "com.ncapdevi.sample.argsInstance"
    }


    //****************************//
    //MODEL
    //****************************//





}
