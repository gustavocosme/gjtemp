package prudential.pobmobilecustomerappandroid.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_extract_payment.view.recycler_extract_payment_arrow_right
import kotlinx.android.synthetic.main.recycler_extract_payment.view.recycler_extract_payment_bottom_line
import kotlinx.android.synthetic.main.recycler_extract_payment.view.recycler_extract_payment_button
import kotlinx.android.synthetic.main.recycler_extract_payment.view.recycler_extract_payment_date
import kotlinx.android.synthetic.main.recycler_extract_payment.view.recycler_extract_payment_icon
import kotlinx.android.synthetic.main.recycler_extract_payment.view.recycler_extract_payment_mode
import kotlinx.android.synthetic.main.recycler_extract_payment.view.recycler_extract_payment_paidvalue
import kotlinx.android.synthetic.main.recycler_extract_payment.view.recycler_extract_payment_payent_try_text
import kotlinx.android.synthetic.main.recycler_extract_payment.view.recycler_extract_payment_title
import kotlinx.android.synthetic.main.recycler_extract_payment.view.recycler_extract_payment_tooltip
import kotlinx.android.synthetic.main.recycler_extract_payment.view.recycler_extract_payment_top_line
import kotlinx.android.synthetic.main.recycler_extract_payment2.view.*
import org.jetbrains.anko.backgroundColor
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.*
import prudential.pobmobilecustomerappandroid.model.PaymentBasicaModel
import prudential.pobmobilecustomerappandroid.ui.activity.app.ExtractComprovantActivity
import prudential.pobmobilecustomerappandroid.ui.activity.app.Tab
import prudential.pobmobilecustomerappandroid.ui.fragment.ExtractFragment
import prudential.pobmobilecustomerappandroid.ui.fragment.ExtractModuleFragment
import prudential.pobmobilecustomerappandroid.ui.viewCustom.DataFilterExtract
import prudential.pobmobilecustomerappandroid.utils.Dialogs
import prudential.pobmobilecustomerappandroid.utils.TypeExtractDialogSheet
import java.text.SimpleDateFormat

/**
 * Adapter class to handle payment vertical list.
 */
class PaymentExtractAdapter : RecyclerView.Adapter<PaymentExtractAdapter.PaymentViewHolder>() {

    private val payments = mutableListOf<PaymentBasicaModel>()
    private val paymentsWithouFilter = mutableListOf<PaymentBasicaModel>()
    private val paymentsFullList = mutableListOf<PaymentBasicaModel>()
    private var sorted = false

    /**
     * Inflate policy recycler view layout.
     * @param parent ViewGroup that holds layout.
     * @param viewType Type of view that is inflating.
     * @return PolicyViewHolder inflated from layout.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder =
        PaymentViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_extract_payment2, parent, false)
        )

    /**
     * Bind all data and set to each payment position.
     * @param holder PaymentViewHolder to handle individual item of list.
     * @param position Position of each item.
     */
    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val payment = payments[position]
        val lastPayment = when {
            position != 0 -> payments[position - 1]
            else -> payment
        }
        holder.bind(lastPayment, payment, paymentsFullList.size)
    }

    /**
     * Get size of list.
     * @return Size of list.
     */
    override fun getItemCount(): Int = payments.size

    /**
     * Check if new list is not empety and replace old list with new one.
     * @param newPaymentBasicaModels New list of itens to be inserted on main list.
     */
    fun submitList(newPaymentBasicaModels: List<PaymentBasicaModel>) {
        if (paymentsWithouFilter.isEmpty()) {
            paymentsWithouFilter.addAll(newPaymentBasicaModels)
        }
        if (paymentsFullList.isEmpty()) {
            paymentsFullList.addAll(newPaymentBasicaModels)
        }
        payments.clear()

        if (newPaymentBasicaModels.size > 6) {
            payments.addAll(newPaymentBasicaModels.subList(0, 5))
        } else {
            payments.addAll(newPaymentBasicaModels)
        }
        notifyDataSetChanged()
    }

    /**
     * Load more itens when list has more then 6 itens.
     */
    fun loadMore() {
        if (payments.size + 6 <= paymentsFullList.size) {
            val pagingList = paymentsFullList.subList(payments.size, payments.size + 6)
            payments.addAll(pagingList)
            notifyItemRangeInserted(payments.size, payments.size + 6)
        } else {
            val size = paymentsFullList.size - payments.size
            val pagingList = paymentsFullList.subList(payments.size, payments.size + size - 1)
            payments.addAll(pagingList)
            notifyItemRangeInserted(payments.size, payments.size + size - 1)
        }
    }

    /**
     * Return list of payment.
     * @return List of payments.
     */
    fun getPayments() = payments

    fun getPaymentsFullList() = paymentsFullList

    /**
     * Reverse list order.
     */
    fun sortList() {
        sorted = !sorted
        val reverseList = mutableListOf<PaymentBasicaModel>()
        reverseList.addAll(payments)

        val isNotPaid = reverseList.filter { !it.isPayed }
        reverseList.removeAll(isNotPaid)
        reverseList.reverse()
        reverseList.addAll(0, isNotPaid)
        submitList(reverseList)
    }

    /**
     * Return sorted value.
     * @return True if is sorted, false if not.
     */
    fun isSorted() = sorted

    /**
     * Clear adapter list.
     */
    fun clear() {
        payments.clear()
        notifyDataSetChanged()
    }

    /**
     * Filter payments with DataFilterExtract model.
     * @param filter Base object to be used as filter.
     */
    fun filterPayments(filter: DataFilterExtract) {
        when {
            filter.isLate != null && filter.isAccomplished != null -> filterCombined(
                filter,
                null,
                true
            )
            filter.isAccomplished != null && filter.isLate == null -> filterCombined(filter, true)
            filter.isLate != null && filter.isAccomplished == null -> filterCombined(filter, false)
            filter.isLate == null && filter.isAccomplished == null -> filterCombined(filter)
        }
    }

    /**
     * validation filter and filter list.
     * @param filter Base object to be filtered.
     * @param isPaid Payment status.
     */
    private fun filterCombined(
        filter: DataFilterExtract,
        isPaid: Boolean? = null,
        bothPaid: Boolean = false
    ) {
        paymentsWithouFilter.apply {
            val filtered = mutableListOf<PaymentBasicaModel>()
            when {
                isPaid != null -> when {
                    filter.isAllNull() -> filtered.addAll(filter { it.isPayed == isPaid })
                    else -> {
                        filtered.addAll(filter { it.isSPL == filter.isFinancial && isPaid == it.isPayed })
                        filtered.addAll(filter { it.isCreditCard == filter.isCredit && isPaid == it.isPayed })
                        filtered.addAll(filter { it.isDebit == filter.isDebit && isPaid == it.isPayed })
                        filtered.addAll(filter { it.isBillet == filter.isBillet && isPaid == it.isPayed })
                    }
                }
                else -> {
                    filtered.addAll(filter { it.isSPL == filter.isFinancial })
                    filtered.addAll(filter { it.isCreditCard == filter.isCredit })
                    filtered.addAll(filter { it.isDebit == filter.isDebit })
                    filtered.addAll(filter { it.isBillet == filter.isBillet })
                }
            }
            if (bothPaid and filtered.isEmpty()) {
                filtered.addAll(paymentsWithouFilter)
            }
            submitList(filtered)
            filterIfDateIsNotNull(filter, filtered)
        }
    }

    /**
     * Check if date on filter object is null. If is not null, filter list.
     * @param filter Filter object.
     */
    private fun filterIfDateIsNotNull(
        filter: DataFilterExtract,
        filtered: MutableList<PaymentBasicaModel>
    ) {
        if (!filter.isDateNull()) {
            val listTobeFiltered = when {
                filter.isAllNull() and filtered.isEmpty() -> paymentsWithouFilter
                else -> payments
            }

            val initialDate = filter.dateInS.toDate()
            val finalDate = filter.dateOut.toDate()
            val filteredDate = listTobeFiltered.filter {
                it.paidDate.toDate()
                    .after(initialDate) and it.paidDate.toDate()
                    .before(finalDate) or it.paidDate.toDate()
                    .equals(initialDate) or it.paidDate.toDate().equals(finalDate)
            }
            submitList(filteredDate)
        }
    }

    /**
     * Convert date string to Date object.
     * @receiver String to be converted.
     * @return Date converted.
     */
    private fun String.toDate() = SimpleDateFormat("dd/MM/yyyy").parse(this)

    /**
     * Restore list initial state with initial values.
     */
    fun restoreState() {
        submitList(paymentsWithouFilter)
    }

    /**
     * Clear filter list.
     */
    fun clearFilter() {
        paymentsWithouFilter.clear()
    }

    /**
     * Class to hold and manager each item of recycler list.
     * @property itemView View inflated by onCreateViewHolder.
     */
    class PaymentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /**
         * Bind itens data to the layout.
         * @param paymentBasicaModel Payment object got by position.
         */
        fun bind(
            lastPaymentBasicaModel: PaymentBasicaModel,
            paymentBasicaModel: PaymentBasicaModel,
            lastIndex: Int
        ) {
            hideFirstAndLastLine(lastIndex)
            itemView.apply {
                setColorIfNotPaid(paymentBasicaModel)
                setBackgroundIcon(paymentBasicaModel)
                showOrHideItens(paymentBasicaModel)
                tooltipClick()
                setTextsToView(paymentBasicaModel)
                setLineColor(lastPaymentBasicaModel, paymentBasicaModel)
                paymentItemClick(paymentBasicaModel)
                paymentButtonClick(paymentBasicaModel)
            }
        }

        /**
         * Change text color if policy is not paid.
         * @receiver reference to the layout.
         * @param paymentBasicaModel Payment model.
         */
        private fun View.setColorIfNotPaid(paymentBasicaModel: PaymentBasicaModel) {
            recycler_extract_payment_title.setTextColor(itemView.context.getColor(R.color.colorPrimary))
            if (!paymentBasicaModel.isPayed and !paymentBasicaModel.isFuturePayed) {
                recycler_extract_payment_title.setTextColor(itemView.context.getColor(R.color.attentionColor))
            }
        }

        /**
         * Set background icon according to the model.
         * @receiver reference to the layout.
         * @param paymentBasicaModel Payment model.
         */
        private fun View.setBackgroundIcon(paymentBasicaModel: PaymentBasicaModel) {
            recycler_extract_payment_icon.background =
                paymentBasicaModel.getIcon(itemView.context)
        }

        /**
         * Show or hide item according to the it's status.
         * @receiver reference to the layout.
         * @param paymentBasicaModel Payment model.
         */
        private fun View.showOrHideItens(paymentBasicaModel: PaymentBasicaModel) {
            val tooltip = paymentBasicaModel.showOrHideTooltip()
            recycler_extract_payment_tooltip.showOrGone(tooltip)
            recycler_extract_payment_button.showOrGone(paymentBasicaModel.showOrHidePayButton())
            l.showOrGone(paymentBasicaModel.showOrHidePayButton())

            recycler_extract_payment_payent_try_text.showOrGone(paymentBasicaModel.showOrHideTryText())

            //recycler_extract_payment_date.showOrGone(paymentBasicaModel.showOrHideDate())
            //recycler_extract_payment_payent_future_text.showOrGone(paymentBasicaModel.showOrHideFutureText())
            recycler_extract_payment_arrow_right.showOrGone(paymentBasicaModel.showOrHideArrowRight())
        }

        /**
         * Set text to the views according to the mode.
         * @receiver reference to the layout.
         * @param paymentBasicaModel Payment model.
         */
        private fun View.setTextsToView(paymentBasicaModel: PaymentBasicaModel) {
            //recycler_extract_payment_mode.text = paymentBasicaModel.paymentType
            recycler_extract_payment_mode.text = paymentBasicaModel.paidDate;
            recycler_extract_payment_title.text = paymentBasicaModel.paymentDescription
            recycler_extract_payment_paidvalue.text = paymentBasicaModel.getPaidValue()
            recycler_extract_payment_date.text =
                paymentBasicaModel.paidDate.removeRange(5, paymentBasicaModel.paidDate.length)
        }

        /**
         * Handle paymnt button click.
         * @receiver reference to the layout.
         * @param paymentBasicaModel Payment model.
         */
        private fun View.paymentButtonClick(paymentBasicaModel: PaymentBasicaModel) {
            recycler_extract_payment_button.setOnClickListener {
                if (paymentBasicaModel.isBillet) {
                    Tab.INSTANCE.listMenuPagmentBilletCopy(object : Dialogs.CALL {
                        override fun call(value: String?, p: Int) {
                            if (p == 1) {
                                val copy = "Protocolo de solicitação: 1828738\n" +
                                        "Seguro: 1283747\n" +
                                        "Vida Inteira Mais\n" +
                                        "Pagamento de prêmio\n" +
                                        "Em: 10/11/2020 às 19:09"
                                Tab.INSTANCE.copyString(copy)
                            }
                        }
                    });
                }
                if (paymentBasicaModel.isCreditCard || paymentBasicaModel.isDebit) {
                    Tab.INSTANCE.listMenuPagment(object : Dialogs.CALL {
                        override fun call(value: String?, position: Int) {
                            if (position == 0) {
                                addAlertEmission(paymentBasicaModel)
                            }
                            if (position == 1) {
                                Tab.INSTANCE.listMenuPagmentCard(object : Dialogs.CALL {
                                    override fun call(value: String?, p: Int) {
                                        if (p == 0) {
                                            addAlertEmission(paymentBasicaModel)
                                        }
                                        if (p == 2) {
                                            val copy = "Protocolo de solicitação: 1828738\n" +
                                                    "Seguro: 1283747\n" +
                                                    "Vida Inteira Mais\n" +
                                                    "Pagamento de prêmio\n" +
                                                    "Em: 10/11/2020 às 19:09"
                                            Tab.INSTANCE.copyString(copy)
                                        }
                                        if (p == 3) {
                                        }
                                    }
                                });
                            }
                        }
                    })
                }

                if (paymentBasicaModel.isDebit) {


                }


            }
        }

        /**
         * Show extract copy alert with custom message
         * @param paymentBasicaModel Model to extract data to build alert dialog.
         */
        fun addAlertEmission(paymentBasicaModel: PaymentBasicaModel) {
            if (paymentBasicaModel.isAlertType == 0) {
                Tab.INSTANCE.alertShowExtractCopy(
                    "Reemissão de cobrança",
                    "Cobrança débito em conta Santander 1232",
                    "Reemissão de cobrança efetuada com sucesso! ",
                    "16/11/2020 21:30",
                    "13377987",
                    "Se você tiver uma ou mais cobranças em aberto todas serão agendadas para pagamento nos próximos seis dias úteis.",
                    true,
                    TypeExtractDialogSheet.NORMAL
                )
            }

            if (paymentBasicaModel.isAlertType == 1) {
                Tab.INSTANCE.alertShowExtractCopy(
                    "Reemissão de cobrança",
                    "Cobrança débito em conta Santander 1232",
                    "Reemissão de cobrança efetuada com sucesso! ",
                    "16/11/2020 21:30",
                    "13377987",
                    "",
                    true,
                    TypeExtractDialogSheet.NORMAL
                )
            }


            if (paymentBasicaModel.isAlertType == 2) {
                Tab.INSTANCE.alertShowExtractCopy(
                    "Reemissão de cobrança",
                    "Cobrança no cartão de crédito final  8293",
                    "Solicitação de reemissão de cobrança não realizada.",
                    "",
                    "",
                    "Por favor verifique com sua operadora de cartão de crédito ou altere a forma de pagamento.",
                    false,
                    TypeExtractDialogSheet.ATENCION
                )
            }

            if (paymentBasicaModel.isAlertType == 3) {
                Tab.INSTANCE.alertShowText(
                    "Aviso Importante",
                    "Por conta do horário, a sua solicitação só será realizada no próximo dia útil."
                )
                return;
            }

            if (paymentBasicaModel.isAlertType == 4) {
                Tab.INSTANCE.alertShowText(
                    "Aviso Importante",
                    "Por conta do horário, a sua solicitação não poderá ser realizada. Como o seu seguro completou 60 dias de vencido, procure o atendimento da Prudential."
                )
                return;
            }

        }

        /**
         * Handle payment item click.
         * @param paymentBasicaModel Model to handle validation.
         */
        private fun View.paymentItemClick(paymentBasicaModel: PaymentBasicaModel) {
            setOnClickListener {
                validateAndShowDialog(paymentBasicaModel)
            }
        }

        /**
         * Validate data and show according dialog.
         * @param paymentBasicaModel Model to handle validation.
         */
        private fun validateAndShowDialog(paymentBasicaModel: PaymentBasicaModel) {

            var sheetType = when {
                paymentBasicaModel.isPayed and !paymentBasicaModel.isSPL -> TypeExtractDialogSheet.NORMAL
                !paymentBasicaModel.isPayed -> TypeExtractDialogSheet.ATENCION
                paymentBasicaModel.isSPL -> TypeExtractDialogSheet.ASSISTENT
                else -> TypeExtractDialogSheet.ATENCION
            }

            if (paymentBasicaModel.isFuturePayed) {
                sheetType = TypeExtractDialogSheet.FUTURE
            }

            //val i = Intent(Tab.INSTANCE, ExtractComprovantActivity::class.java);
            //i.putExtra("type", sheetType);
            //Tab.INSTANCE.startActivity(i);

            //val d = ExtractDialogSheet.newInstance(0, sheetType, null)
            //d.show(Tab.INSTANCE.supportFragmentManager, "Gloria a Deus!")

            Tab.INSTANCE.nav(ExtractModuleFragment.newInstance(0,sheetType))

        }

        /**
         * Handle tooltip icon click.
         */
        private fun View.tooltipClick() {
            recycler_extract_payment_tooltip.setOnClickListener {
                Tab.INSTANCE.alertCustomTips(
                    "Toda cobrança tem prazo mínimo de dois dias úteis para baixar após o dia de pagamento.",
                    recycler_extract_payment_tooltip
                )
            }
        }

        /**
         * Set custom color to line.
         * @param lastPaymentBasicaModel Last item on list to get color reference.
         * @param paymentBasicaModel Current model to set values.
         */
        private fun View.setLineColor(
            lastPaymentBasicaModel: PaymentBasicaModel,
            paymentBasicaModel: PaymentBasicaModel
        ) {
            val lineColorBottom = paymentBasicaModel.getLineColor(itemView.context)
            recycler_extract_payment_bottom_line.backgroundColor = lineColorBottom

            val lineColorTop = when (adapterPosition) {
                1 -> lastPaymentBasicaModel.getLineColor(itemView.context)
                else -> paymentBasicaModel.getLineColor(itemView.context)
            }
            recycler_extract_payment_top_line.backgroundColor = lineColorTop
        }

        /**
         * Hide first top and last bottom lines.
         * @param lastIndex Last index of list.
         */
        private fun hideFirstAndLastLine(lastIndex: Int) {
            when (adapterPosition) {
                0 -> {
                    itemView.recycler_extract_payment_top_line.visibility = View.INVISIBLE
                    itemView.recycler_extract_payment_bottom_line.visibility = View.VISIBLE
                }
                (lastIndex - 1) -> {
                    itemView.recycler_extract_payment_top_line.visibility = View.VISIBLE
                    itemView.recycler_extract_payment_bottom_line.visibility = View.INVISIBLE
                }
                else -> {
                    itemView.recycler_extract_payment_bottom_line.visibility = View.VISIBLE
                    itemView.recycler_extract_payment_top_line.visibility = View.VISIBLE
                }
            }
        }
    }
}
