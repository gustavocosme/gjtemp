package prudential.pobmobilecustomerappandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_simple_select.view.*
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.interfaces.OnRecyclerClickListener

/**
 * Adapter class to handle list of itens.
 * @property onRecyclerClickListener Listener passed by class who is calling, to manage item click.
 */
class SimpleSelectAdapter(
    private val onRecyclerClickListener: OnRecyclerClickListener<String>
) : RecyclerView.Adapter<SimpleSelectAdapter.SimpleSelectAdapterHolder>() {

    private val itens = mutableListOf<String>()

    /**
     * Inflate policy recycler view layout.
     * @param parent ViewGroup that holds layout.
     * @param viewType Type of view that is inflating.
     * @return SimpleSelectAdapterHolder inflated from layout.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleSelectAdapterHolder =
        SimpleSelectAdapterHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_simple_select, parent, false),
            onRecyclerClickListener
        )

    /**
     * Bind all data and set to each item position.
     * @param holder SimpleSelectAdapterHolder to handle individual item of list.
     * @param position Position of each item.
     */
    override fun onBindViewHolder(holder: SimpleSelectAdapterHolder, position: Int) {
        holder.bind(itens[position])
    }

    /**
     * Get size of list.
     * @return Size of list.
     */
    override fun getItemCount(): Int = itens.size

    /**
     * Check if new list is not empety and replace old list with new one.
     * @param newItens New list of itens to be inserted on main list.
     */
    fun submitList(newItens: MutableList<String>) {
        itens.clear()
        itens.addAll(newItens)
        notifyDataSetChanged()
    }

    /**
     * Class to hold and manager each item of recycler list.
     * @property itemView View inflated by onCreateViewHolder.
     */
    class SimpleSelectAdapterHolder(
        itemView: View,
        private val onRecyclerClickListener: OnRecyclerClickListener<String>
    ) : RecyclerView.ViewHolder(itemView) {

        /**
         * Bind itens data to the layout.
         * @param item String object got by position.
         */
        fun bind(item: String) {
            itemView.apply {
                recycler_simple_select_text.text = item
                setOnClickListener {
                    onRecyclerClickListener.onRecyclerItemClick(item, adapterPosition)
                }
            }
        }
    }
}