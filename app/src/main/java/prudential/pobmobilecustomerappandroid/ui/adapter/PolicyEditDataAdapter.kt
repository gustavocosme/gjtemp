package prudential.pobmobilecustomerappandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.payment_update_period_item_check_sub.view.*
import kotlinx.android.synthetic.main.recycler_simple_select.view.*
import kotlinx.android.synthetic.main.recycler_simple_select_check.view.*
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.interfaces.OnRecyclerClickListener
import prudential.pobmobilecustomerappandroid.model.PolicyBasicModel

/**
 * Adapter class to handle policy selection vertical list.
 * @property onRecyclerClickListener Listener passed by class who is calling, to manage item click.
 */
class PolicyEditDataAdapter(
    private val onRecyclerClickListener: OnRecyclerClickListener<PolicyBasicModel>
) : RecyclerView.Adapter<PolicyEditDataAdapter.PolicyEditDataViewHolder>() {

    val policies = mutableListOf<PolicyBasicModel>()

     lateinit var onSelectChange: (array:ArrayList<PolicyBasicModel>) -> Any

    var isSelectAll = false;



    /**
     * Inflate policy recycler view layout.
     * @param parent ViewGroup that holds layout.
     * @param viewType Type of view that is inflating.
     * @return PolicyEditDataViewHolder inflated from layout.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PolicyEditDataViewHolder =
        when (viewType) {
            0 -> PolicyEditDataViewHolderSelectAll(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_simple_select_check, parent, false),
                onRecyclerClickListener
            )
            else -> PolicyEditDataViewHolderSelectOne(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.payment_update_period_item_check_sub, parent, false),
                onRecyclerClickListener
            )
        }

    /**
     * Bind all data and set to each policy position.
     * @param holder PolicyEditDataViewHolder to handle individual item of list.
     * @param position Position of each item.
     */
    override fun onBindViewHolder(holder: PolicyEditDataViewHolder, position: Int) {
        when (position) {
            0 -> (holder as PolicyEditDataViewHolderSelectAll).bind(policies[position])
            else -> (holder as PolicyEditDataViewHolderSelectOne).bind(policies[position])
        }
    }

    /**
     * Get type pf view to be inflated.
     * @param position Position of item to get type.
     * @return Type of view.
     */
    override fun getItemViewType(position: Int): Int = position

    /**
     * Check if new list is not empety and replace old list with new one.
     * @param newPolicies New list of itens to be inserted on main list.
     */
    fun submitList(newPolicies: List<PolicyBasicModel>) {
        if (newPolicies.isNotEmpty()) {
            policies.clear()
            policies.addAll(newPolicies)
            handleSelectAllFirstItem();
            notifyDataSetChanged()
        }
    }

    /**
     * Mark check box as checked at given position.
     * @param policy Model to be updated.
     * @param position Position to update item.
     */
    fun checkAtPosition(policy: PolicyBasicModel, position: Int) {

        if (position == 0) {

            policies.forEach {

                it.isSelected = policy.isSelected

            }

        } else {
            policies[position].isSelected = policy.isSelected
            handleSelectAllFirstItem()
        }

        notifyDataSetChanged()

        var p = 0;
        val array = ArrayList<PolicyBasicModel>();

        for(a in policies)
        {
            if(p!=0)
            {
                if(a.isSelected)
                {
                    array.add(a);
                }
            }
            p++;
        }

        onSelectChange(array);

    }

    /**
     * Handle "Select All" item to check it or not according to the selected itens.
     */
    private fun handleSelectAllFirstItem() {
        val policiesWithouSelectAll = mutableListOf<PolicyBasicModel>().apply {
            addAll(policies)
            removeAt(0)
        }
        val updateAllSelect = policiesWithouSelectAll.all { it.isSelected } and !policies[0].isSelected
        updateSelectAllModel(updateAllSelect)
    }

    /**
     * Update item of select all with check or not.
     * @param isSelected If all itens is selected or not.
     */
    private fun updateSelectAllModel(isSelected: Boolean) {
        val policyAll = policies[0]
        policyAll.isSelected = isSelected
        policies.set(0, policyAll)
        notifyDataSetChanged();
    }

    /**
     * Get size of list.
     * @return Size of list.
     */
    override fun getItemCount(): Int = policies.size

    /**
     * Open class view holder to be instantiated by another viewholders.
     * @param itemView View passed on bind view holder.
     */
    open class PolicyEditDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    /**
     * Class to hold and manage each item of recycler list.
     * @property itemView View inflated by onCreateViewHolder.
     * @property onRecyclerClickListener Listener to get item click.
     */
    class PolicyEditDataViewHolderSelectOne(
        itemView: View,
        private val onRecyclerClickListener: OnRecyclerClickListener<PolicyBasicModel>
    ) : PolicyEditDataViewHolder(itemView) {

        /**
         * Bind itens data to the layout.
         * @param policy Policy object got by position.
         */
        fun bind(policy: PolicyBasicModel) {
            itemView.apply {

                period_item_check_sub_title.apply {
                    isChecked = policy.isSelected
                    text = policy.policyDescription+" 00976234"
                    setOnClickListener {
                        onRecyclerClickListener.onRecyclerItemClick(policy, adapterPosition)
                    }
                }
            }
        }
    }

    /**
     * Class to hold and manage each item of recycler list.
     * @property itemView View inflated by onCreateViewHolder.
     * @property onRecyclerClickListener Listener to get item click.
     */
    class PolicyEditDataViewHolderSelectAll(
        itemView: View,
        private val onRecyclerClickListener: OnRecyclerClickListener<PolicyBasicModel>
    ) : PolicyEditDataViewHolder(itemView) {

        /**
         * Bind itens data to the layout.
         * @param policy Policy object got by position.
         */
        fun bind(policy: PolicyBasicModel) {
            itemView.recycle_simple_select_check_text.apply {
                isChecked = policy.isSelected
                setOnClickListener {
                    onRecyclerClickListener.onRecyclerItemClick(policy, adapterPosition)
                }
            }
        }
    }

}