package prudential.pobmobilecustomerappandroid.ui.fragment

import android.os.Bundle
import gustavocosme.ui.BaseFragment
import prudential.pobmobilecustomerappandroid.R

class ContractFragment : BaseFragment() {

    init {

        setLayout(R.layout.fragment_contract);
    }

    override fun init() {


    }

    companion object {

        fun newInstance(instance: Int): ContractFragment {
            val args = Bundle()
            args.putInt(ARGS_INSTANCE, instance)
            val fragment =
                ContractFragment()
            fragment.setArguments(args)
            return fragment
        }

    }


}//END CLASS
