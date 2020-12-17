package prudential.pobmobilecustomerappandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_profile_update_item.view.*
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.model.DataConfirmationUpdate

/**
 * Adapter class to handle list of itens.
 */
class ProfileConfirmationUpdateAdapter() :
    ListAdapter<DataConfirmationUpdate, ProfileConfirmationUpdateAdapter.ProfileConfirmationUpdateAdapterHolder>(
        diffUtil
    ) {


    /**
     * Static val that implements DiffUtil, and handle difference between itens on list.
     */
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<DataConfirmationUpdate>() {
            override fun areItemsTheSame(
                oldItem: DataConfirmationUpdate,
                newItem: DataConfirmationUpdate
            ): Boolean = oldItem.label1 == newItem.label1

            override fun areContentsTheSame(
                oldItem: DataConfirmationUpdate,
                newItem: DataConfirmationUpdate
            ): Boolean = oldItem.label1 == newItem.label1
        }
    }

    /**
     * Inflate policy recycler view layout.
     * @param parent ViewGroup that holds layout.
     * @param viewType Type of view that is inflating.
     * @return ProfileConfirmationUpdateAdapterHolder inflated from layout.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileConfirmationUpdateAdapterHolder =
        ProfileConfirmationUpdateAdapterHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_profile_update_item, parent, false)
        )

    /**
     * Bind all data and set to each item position.
     * @param holder ProfileConfirmationUpdateAdapterHolder to handle individual item of list.
     * @param position Position of each item.
     */
    override fun onBindViewHolder(holder: ProfileConfirmationUpdateAdapterHolder, position: Int) {
        holder.bind(getItem(position))
    }


    /**
     * Class to hold and manager each item of recycler list.
     * @property itemView View inflated by onCreateViewHolder.
     */
    class ProfileConfirmationUpdateAdapterHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        /**
         * Bind itens data to the layout.
         * @param item DataConfirmationUpdate object got by position.
         */
        fun bind(item: DataConfirmationUpdate) {
            itemView.apply {
                recycler_profile_update_item_title.text = "${item.label1} solicitado"
                recycler_profile_update_item_input.setText(item.current)
            }
        }
    }
}