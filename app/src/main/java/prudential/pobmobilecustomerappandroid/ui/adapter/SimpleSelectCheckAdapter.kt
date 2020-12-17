package prudential.pobmobilecustomerappandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_simple_select_check.view.*
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.interfaces.OnRecyclerClickListener
import prudential.pobmobilecustomerappandroid.model.SimpleCheckItem

/**
 * Adapter class to handle itens with check box.
 * @param onRecyclerClickListener Listener to receive click.
 */
class SimpleSelectCheckAdapter(
    private val onRecyclerClickListener: OnRecyclerClickListener.ChckeckListener<SimpleCheckItem>
) :
    ListAdapter<SimpleCheckItem, SimpleSelectCheckAdapter.SimpleSelectCheckViewHolder>(
        diffUtil
    ) {

    /**
     * Static val that implements DiffUtil, and handle difference between itens on list.
     */
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SimpleCheckItem>() {
            override fun areItemsTheSame(
                oldItem: SimpleCheckItem,
                newItem: SimpleCheckItem
            ): Boolean = oldItem.title == newItem.title

            override fun areContentsTheSame(
                oldItem: SimpleCheckItem,
                newItem: SimpleCheckItem
            ): Boolean = oldItem.title == newItem.title

        }
    }

    /**
     * Inflate recycler view layout.
     * @param parent ViewGroup that holds layout.
     * @param viewType Type of view that is inflating.
     * @return SimpleSelectCheckViewHolder inflated from layout.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleSelectCheckViewHolder =
        SimpleSelectCheckViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_simple_select_check, parent, false),
            onRecyclerClickListener
        )

    /**
     * Bind all data and set to each item position.
     * @param holder SimpleSelectCheckViewHolder to handle individual item of list.
     * @param position Position of each item.
     */
    override fun onBindViewHolder(holder: SimpleSelectCheckViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * Rturn titles of checked itens, separated by ";".
     */
    fun getCheckedItensTitle(): String = currentList
        .filter { it.isChecked }.joinToString(";") { it.title + " " }

    /**
     * Find and mark as check item at position.
     * @param position Position of item to be checed.
     */
    fun checkAtPosition(position: Int) {
        val list = currentList.toMutableList()
        list[position].isChecked = !list[position].isChecked
        submitList(list)
    }

    /**
     * Check if "North-American" is marked.
     */
    fun hasAmericanText(): Boolean = currentList.filter { it.isChecked }
        .any { it.title == "Norte-americano(a)" }

    /**
     * Class to hold and manager each item of recycler list.
     * @property itemView View inflated by onCreateViewHolder.
     * @property onRecyclerClickListener Listener to handle itemClick.
     */
    class SimpleSelectCheckViewHolder(
        itemView: View,
        private val onRecyclerClickListener: OnRecyclerClickListener.ChckeckListener<SimpleCheckItem>
    ) : RecyclerView.ViewHolder(itemView) {


        /**
         * Bind itens data to the layout.
         * @param item Object got by position.
         */
        fun bind(item: SimpleCheckItem) {
            itemView.recycle_simple_select_check_text.apply {
                text = item.title
                isChecked = item.isChecked
                setOnClickListener {
                    onRecyclerClickListener.onChckedItem(item, adapterPosition)
                }
            }
        }

    }
}