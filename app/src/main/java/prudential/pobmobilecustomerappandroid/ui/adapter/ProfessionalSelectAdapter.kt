package prudential.pobmobilecustomerappandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.payment_update_period_item_select_day.view.*
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.interfaces.OnRecyclerClickListener

/**
 * Adapter class to handle day selection vertical list.
 * @property onRecyclerClickListener Listener passed by class who is calling, to manage item click.
 */
class ProfessionalSelectAdapter(
    private val onRecyclerClickListener: OnRecyclerClickListener<String>
) : RecyclerView.Adapter<ProfessionalSelectAdapter.PeriodSelectDayViewHolder>() {

    private val days = mutableListOf<String>()

    /**
     * Inflate policy recycler view layout.
     * @param parent ViewGroup that holds layout.
     * @param viewType Type of view that is inflating.
     * @return PeriodSelectDayViewHolder inflated from layout.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeriodSelectDayViewHolder =
        PeriodSelectDayViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.payment_update_period_item_select_day, parent, false),
            onRecyclerClickListener
        )

    /**
     * Bind all data and set to each day position.
     * @param holder PeriodSelectDayViewHolder to handle individual item of list.
     * @param position Position of each item.
     */
    override fun onBindViewHolder(holder: PeriodSelectDayViewHolder, position: Int) {
        holder.bind(days[position])
    }

    /**
     * Get size of list.
     * @return Size of list.
     */
    override fun getItemCount(): Int = days.size

    /**
     * Check if new list is not empety and replace old list with new one.
     * @param newDays New list of itens to be inserted on main list.
     */
    fun submitList(newDays: MutableList<String>) {
        days.clear()
        days.addAll(newDays)
        notifyDataSetChanged()
    }

    /**
     * Class to hold and manager each item of recycler list.
     * @property itemView View inflated by onCreateViewHolder.
     */
    class PeriodSelectDayViewHolder(
        itemView: View,
        private val onRecyclerClickListener: OnRecyclerClickListener<String>
    ) : RecyclerView.ViewHolder(itemView) {

        /**
         * Bind itens data to the layout.
         * @param day Payment object got by position.
         */
        fun bind(day: String) {
            itemView.period_item_select_day_title.apply {
                text = "$day"
            }
            itemView.setOnClickListener {

                onRecyclerClickListener.onRecyclerItemClick(day, adapterPosition)
            }
        }
    }

}