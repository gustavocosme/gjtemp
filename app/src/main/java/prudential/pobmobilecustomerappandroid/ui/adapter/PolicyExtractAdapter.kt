package prudential.pobmobilecustomerappandroid.ui.adapter

import android.graphics.Paint
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_extract_policy.view.*
import kotlinx.android.synthetic.main.recycler_extract_policy.view.tagEmAtraso
import kotlinx.android.synthetic.main.recycler_extract_policy_select_all.view.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.displayMetrics
import org.jetbrains.anko.textColor
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.drawUnderLine
import prudential.pobmobilecustomerappandroid.extensions.showOrGone
import prudential.pobmobilecustomerappandroid.interfaces.OnRecyclerClickListener
import prudential.pobmobilecustomerappandroid.model.PolicyBasicModel
import prudential.pobmobilecustomerappandroid.ui.activity.app.Tab

/**
 * Adapter class to handle policy horizontal list.
 */
class PolicyExtractAdapter(
    private val onRecyclerClickListener: OnRecyclerClickListener<PolicyBasicModel>,
    private val isEditable: Boolean = false,var isFirtsSelect:Boolean = true
) : RecyclerView.Adapter<PolicyExtractAdapter.PolicyViewHolder>() {

    private val policies = mutableListOf<PolicyBasicModel>()
    private val selectedIndexs = mutableListOf<Int>()

    /**
     * Inflate policy recycler view layout.
     * @param parent ViewGroup that holds layout.
     * @param viewType Type of view that is inflating.
     * @return PolicyViewHolder inflated from layout.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PolicyViewHolder =
        when (viewType) {
            PolicyBasicModel.LayoutType.PAYMENT_SELECT_ALL -> PolicyViewHolderSelectAll(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_extract_policy_select_all, parent, false),
                onRecyclerClickListener
            )
            else -> PolicyViewHolderItem(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_extract_policy, parent, false),
                onRecyclerClickListener
            )
        }

    /**
     * Bind all data and set to each policy position.
     * @param holder PolicyViewHolder to handle individual item of list.
     * @param position Position of each item.
     */
    override fun onBindViewHolder(holder: PolicyViewHolder, position: Int) {
        val policy = policies[position]
        if (isEditable) {
            when (position) {
                0 -> (holder as PolicyViewHolderSelectAll).bind(policy)
                else -> (holder as PolicyViewHolderItem).bind(policy, policies.size == 1, true)
            }
        } else {
            (holder as PolicyViewHolderItem).bind(policy, policies.size == 1, false)
        }
    }

    /**
     * Get size of list.
     * @return Size of list.
     */
    override fun getItemCount(): Int = policies.size

    /**
     * Get item view type if item is editable.
     * @param position Position of item.
     * @return ViewType according to the editable variable.
     */
    override fun getItemViewType(position: Int): Int {
        if (isEditable) {
            return when (position) {
                0 -> PolicyBasicModel.LayoutType.PAYMENT_SELECT_ALL
                else -> PolicyBasicModel.LayoutType.EXTRACT
            }
        }
        return PolicyBasicModel.LayoutType.EXTRACT
    }

    /**
     * Check if new list is not empety and replace old list with new one.
     * @param newPolicies New list of itens to be inserted on main list.
     */
    fun submitList(newPolicies: List<PolicyBasicModel>) {
        if (newPolicies.isNotEmpty()) {
            policies.clear()
            val policiesCheckd = newPolicies.checkAndSelectFirst()
            policies.addAll(policiesCheckd)
            notifyDataSetChanged()
        }
    }

    /**
     * Check how many itens have on list. If list have only one policy, unselect it.
     * If list have more than one, select first item .
     * @receiver List to be modified.
     * @return New list with selection.
     */
    private fun List<PolicyBasicModel>.checkAndSelectFirst(): List<PolicyBasicModel> {
        when (size) {
            1 -> unSelectAll()
            else -> when {
                !isEditable -> forEachIndexed { index, policyBasicModel ->
                    if (index == 0) {
                        policyBasicModel.isSelected = true
                        selectedIndexs.add(index)
                    }
                }
                else -> {
                    //GUSTAVOCOSME
                    get(1).isSelected = isFirtsSelect
                    selectedIndexs.add(0)
                }
            }
        }
        return this
    }

    /**
     * Unselect all payments.
     * @receiver Payment list.
     */
    private fun List<PolicyBasicModel>.unSelectAll() {
        forEach { it.isSelected = false }
    }

    /**
     * Update policy at given position.
     * @param policy Model with update data.
     */
    fun updateAtPosition(policy: PolicyBasicModel, position: Int) {
        if (policy.isSelected) {
            policies.forEachIndexed { index, policyBasicModel ->
                if (position != index) {
                    policyBasicModel.isSelected = false
                }
            }
        }
        policies.set(position, policy)
        notifyDataSetChanged()
    }

    /**
     * Mark check box as checked at given position.
     * @param policy Model to be updated.
     * @param position Position to update item.
     */
    fun checkAtPosition(policy: PolicyBasicModel, position: Int) {
        if (position == 0) {
            policies.forEach { it.isSelected = policy.isSelected }
            selectedIndexs.clear()
            if (policy.isSelected) {
                mutableListOf<PolicyBasicModel>().apply {
                    addAll(policies)
                    removeAt(0)
                    forEachIndexed { index, policyBasicModel -> selectedIndexs.add(index) }
                }
            }
        } else {
            if (policy.isSelected) {
                selectedIndexs.add(position - 1)
            } else {
                selectedIndexs.remove(position - 1)
            }
            policies.set(position, policy)
            handleSelectAllFirstItem()
        }
        notifyDataSetChanged()
    }

    /**
     * Handle "Select All" item to check it or not according to the selected itens.
     */
    private fun handleSelectAllFirstItem() {
        val policiesWithouSelectAll = mutableListOf<PolicyBasicModel>().apply {
            addAll(policies)
            removeAt(0)
        }
        if (policiesWithouSelectAll.all { it.isSelected } and !policies[0].isSelected) {
            updateSelectAllModel(true)
        } else {
            updateSelectAllModel(false)
        }
    }

    /**
     * Update item of select all with check or not.
     * @param isSelected If all itens is selected or not.
     */
    private fun updateSelectAllModel(isSelected: Boolean) {
        val policyAll = policies[0]
        policyAll.isSelected = isSelected
        policies.set(0, policyAll)
    }

    /**
     * Get all selected itens index.
     * @return list of indexs selected
     */
    fun getAllSelectItens() = selectedIndexs

    /**
     * Get list of intes tha are not selected.
     * @return list of unselected itens.
     */
    fun getAllUnselectItens() : List<PolicyBasicModel> {
        val newPolicyList = mutableListOf<PolicyBasicModel>().apply {
            addAll(policies)
            removeAt(0)
        }
        val filtered = newPolicyList.filter { !it.isSelected }
        return filtered
    }

    /**
     * Open class to handle view holdes.
     * @property itemView View inflated by onCreateViewHolder.
     * @param onRecyclerClickListener Listener to handle item view click.
     */
    open class PolicyViewHolder(
        itemView: View,
        private val onRecyclerClickListener: OnRecyclerClickListener<PolicyBasicModel>
    ) : RecyclerView.ViewHolder(itemView)

    /**
     * Class to hold and manager each item of recycler list.
     * @property itemView View inflated by onCreateViewHolder.
     * * @param onRecyclerClickListener Listener to handle item view click.
     */
    class PolicyViewHolderItem(
        itemView: View,
        private val onRecyclerClickListener: OnRecyclerClickListener<PolicyBasicModel>
    ) : PolicyViewHolder(itemView, onRecyclerClickListener) {

        /**
         * Bind itens data to the layout.
         * @param policy Policy object got by position.
         */
        fun bind(policy: PolicyBasicModel, isUniquePolicy: Boolean, isEditable: Boolean) {
            if (!isUniquePolicy) {
                setAccordingColors(policy)
                handleEditableCheck(isEditable, policy)
            } else {
                setCustomWidth()
            }
        }

        /**
         * Check if Adapter is editable and show or not checkbox.
         * @param isEditable If adapter is editable.
         * @param policy Model to be checked.
         */
        private fun handleEditableCheck(isEditable: Boolean, policy: PolicyBasicModel) {
            itemView.recycler_extract_policy_checkbox.apply {
                showOrGone(isEditable)
                isChecked = policy.isSelected
                setOnClickListener {
                    onRecyclerClickListener.onRecyclerItemClick(policy, adapterPosition)
                }
            }
        }

        /**
         * Set custom Width to item. If list hava only one item, adjust item to match parent.
         * If list have more than one, use specified width on layout.
         */
        private fun setCustomWidth() {
            itemView.recycler_extract_policy_card.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                rightMargin = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    16F,
                    itemView.context.displayMetrics
                ).toInt()
                leftMargin = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    16F,
                    itemView.context.displayMetrics
                ).toInt()
            }
        }

        /**
         * Set colors to each item on layout.
         * @param policy Policy model to set colors.
         */
        private fun setAccordingColors(policy: PolicyBasicModel) {
            itemView.apply {
                tagEmAtraso.showOrGone(!policy.isPaid)
                recycler_extract_policy_description.apply {
                    setTextColor(policy.getPolicyIdAndInsuredTitleColor(itemView.context))
                    drawUnderLine()
//                    paintFlags = getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG
                    text = policy.policyDescription
                    textColor = when {
                        policy.isPaid -> Tab.INSTANCE.getColor(R.color.colorPrimary);
                        else -> Tab.INSTANCE.getColor(R.color.attentionColor);
                    }
                }

                recycler_extract_policy_id.apply {
                    text = policy.policyId
                    setTextColor(policy.getPolicyIdAndInsuredTitleColor(itemView.context))
                }
                recycler_extract_policy_insured_title.apply {
                    setTextColor(policy.getPolicyIdAndInsuredTitleColor(itemView.context))
                }
                recycler_extract_policy_insured_name.apply {
                    text = policy.policyInsuredName
                    setTextColor(policy.getInsuredNameColor(itemView.context))
                }
                recycler_extract_policy_background.backgroundColor =
                    policy.getBackgroundColor(itemView.context)
                recycler_extract_policy_line.backgroundColor = policy.getLineColor(itemView.context)

                setOnClickListener {
                    onRecyclerClickListener.onRecyclerItemClick(policy, adapterPosition)
                }
            }
        }
    }

    /**
     * Class to hold and manager first item of list, if list is type of "Editable".
     * @property itemView View inflated by onCreateViewHolder.
     * * @param onRecyclerClickListener Listener to handle item view click.
     */
    class PolicyViewHolderSelectAll(
        itemView: View,
        private val onRecyclerClickListener: OnRecyclerClickListener<PolicyBasicModel>
    ) : PolicyViewHolder(itemView, onRecyclerClickListener) {

        /**
         * Bind model to view.
         * @param policy Model to be binded to the view.
         */
        fun bind(policy: PolicyBasicModel) {
            itemView.recycler_extract_policy_select_all_card.apply {
                backgroundColor = when {
                    policy.isSelected -> itemView.resources.getColor(
                        R.color.colorBlueSelectPolicy,
                        null
                    )
                    else ->
                        itemView.resources.getColor(R.color.colorWhite, null)
                }
                setOnClickListener {
                    onRecyclerClickListener.onRecyclerItemClick(policy, adapterPosition)
                }
            }
            itemView.recycler_extract_policy_select_all_checkbox.apply {
                isChecked = policy.isSelected
                setOnClickListener {
                    onRecyclerClickListener.onRecyclerItemClick(policy, adapterPosition)
                }
            }
        }
    }
}