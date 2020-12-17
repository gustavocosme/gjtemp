package prudential.pobmobilecustomerappandroid.ui.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gustavocosme.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_user_confirmation_update.*
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.showOrGone
import prudential.pobmobilecustomerappandroid.interfaces.OnRecyclerClickListener
import prudential.pobmobilecustomerappandroid.model.DataConfirmationUpdate
import prudential.pobmobilecustomerappandroid.ui.activity.app.Tab
import prudential.pobmobilecustomerappandroid.ui.adapter.MenuSlideAdapter
import prudential.pobmobilecustomerappandroid.ui.adapter.UserConfirmationUpdateAdapter
import prudential.pobmobilecustomerappandroid.ui.viewCustom.paymentUpdatePeriod.PaymentUpdatePeriod

class UserConfirmationUpdateFragment : BaseFragment(), OnRecyclerClickListener<DataConfirmationUpdate> {

    var IS_CLEAR = false;


    /**
     * Inflate custom layout fragment.
     */
    init {
        isLogo = true;
        title = ""
        setLayout(R.layout.fragment_user_confirmation_update);
    }


    /**
     * Initial methods anime
     */

    @SuppressLint("UseRequireInsteadOfGet")
    override fun init() {

        var list = cachedView!!.findViewById<RecyclerView>(R.id.list);
        list.apply {
            layoutManager = LinearLayoutManager(Tab.INSTANCE, LinearLayoutManager.VERTICAL, false)
            val adp  = UserConfirmationUpdateAdapter(arrayList,this@UserConfirmationUpdateFragment,isCheck1,isCheck2)
            adapter = adp
        }
    }

    override fun onRecyclerItemClick(model: DataConfirmationUpdate, position: Int) {
        if(isToken) {

        } else if (!isCheck1 and !isCheck2) {
            IS_CLEAR = true;
            CALL!!();
             Tab.INSTANCE.fragNavController!!.popFragment();
        } else {
            Tab.INSTANCE.nav(ProfileUpdateConfirmationFragment.newInstance(0, arrayList))
        }
        CALL = null;
    }

    override fun onDestroy() {

        if(!IS_CLEAR)
        {
            if(CALL_NO != null)
                CALL_NO!!()!!;
        }

        super.onDestroy()

    }

    /**
     * Create new Instance of Fragent.
     * @param instance New instance with index.
     * @return New fragment created.
     */
    companion object {

        var CALL: (() -> Any)? = null
        var CALL_NO: (() -> Any)? = null
        private lateinit var arrayList: ArrayList<DataConfirmationUpdate>
        private var isCheck1: Boolean = false
        private var isCheck2: Boolean = false
        private var isToken: Boolean = false

        fun newInstance(instance: Int,array:ArrayList<DataConfirmationUpdate>,isCheck1:Boolean,isCheck2:Boolean,isToken:Boolean): UserConfirmationUpdateFragment {
            arrayList = array
            this.isCheck1 = isCheck1
            this.isCheck2 = isCheck2
            this.isToken = isToken
            val args = Bundle()
            args.putInt(ARGS_INSTANCE, instance)
            args.putSerializable("array",array);
            val fragment = UserConfirmationUpdateFragment()
            fragment.setArguments(args)
            return fragment
        }

    }


}//END CLASS
