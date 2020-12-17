package prudential.pobmobilecustomerappandroid.ui.fragment;

import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gustavocosme.ui.BaseFragment
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.model.DataConfirmationUpdate
import prudential.pobmobilecustomerappandroid.ui.activity.app.Tab
import prudential.pobmobilecustomerappandroid.ui.adapter.ProfileConfirmationUpdateAdapter

/**
 * ProfileUpdateConfirmationFragment subclass BaseFragment to create view
 * and it's actions.
 */
class ProfileUpdateConfirmationFragment : BaseFragment() {

    private val profileConfirmationUpdateAdapter by lazy { ProfileConfirmationUpdateAdapter() }

    /**
     * Init method to set layout to the cachedView variable.
     */
    init {
        setLayout(
            R.layout.fragment_profile_update_confirmation
        );
    }

    /**
     * Called inside onCreateView.
     * It's called once, if the view was not created yet.
     */
    override fun init() {
        initRecyclerView()
        initCloseButton()
    }

    /**
     * init close button and handle click listener to return to main fragment.
     */
    private fun initCloseButton() {
        val closeButton =
            cachedView?.findViewById<Button>(R.id.fragment_profile_update_confirmation_close_button)
        closeButton?.setOnClickListener {
            Tab.INSTANCE.fragNavController?.popFragments(3)
            finishChange?.invoke(dataConfirmationUpdateList)
        }
    }

    /**
     * Init recyccler view with changed data.
     */
    private fun initRecyclerView() {
        val recyclerView =
            cachedView?.findViewById<RecyclerView>(R.id.fragment_profile_update_confirmation_recycler)
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = profileConfirmationUpdateAdapter
        }
        profileConfirmationUpdateAdapter.submitList(dataConfirmationUpdateList)
    }

    /**
     * Static method do create instance of ProfileUpdateConfirmationFragment.
     */
    companion object {

        private lateinit var dataConfirmationUpdateList: ArrayList<DataConfirmationUpdate>
        var finishChange: ((ArrayList<DataConfirmationUpdate>) -> Unit?)? = null

        /**
         * Create instance of ProfileUpdateConfirmationFragment.
         * @param instance Index of instance to be created.
         * @param array List of changes.
         * @return ProfileUpdateConfirmationFragment created.
         */
        fun newInstance(
            instance: Int,
            array: ArrayList<DataConfirmationUpdate>
        ): ProfileUpdateConfirmationFragment {
            dataConfirmationUpdateList = array
            val args = Bundle()
            args.putInt(ARGS_INSTANCE, instance)
            val fragment =
                ProfileUpdateConfirmationFragment()
            fragment.setArguments(args)
            return fragment
        }

    }
}