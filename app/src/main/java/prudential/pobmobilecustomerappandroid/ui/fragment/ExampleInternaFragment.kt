package prudential.pobmobilecustomerappandroid.ui.fragment

import android.os.Bundle
import gustavocosme.ui.BaseFragment
import prudential.pobmobilecustomerappandroid.R

class ExampleInternaFragment : BaseFragment() {

    init {

        title = "Example irmão jonathan!"
        isLogo = false;
        setLayout(R.layout.fragment_contract);
    }

    override fun init() {
        val a = requireActivity().application
    }

    companion object {

        fun newInstance(instance: Int): ExampleInternaFragment {
            val args = Bundle()
            args.putInt(ARGS_INSTANCE, instance)
            val fragment =
                ExampleInternaFragment()
            fragment.setArguments(args)
            return fragment
        }

    }


}//END CLASS
