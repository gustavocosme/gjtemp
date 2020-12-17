package prudential.pobmobilecustomerappandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_payment_edit_data.view.*
import kotlinx.android.synthetic.main.recycler_payment_edit_data_payment.view.*
import org.jetbrains.anko.backgroundDrawable
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.showOrGone
import prudential.pobmobilecustomerappandroid.model.PolicyBasicModel

/**
 * Adapter to handle payment data into each payment data card.
 * @param paymentType Type of payment to create custom adapter.
 */
class PaymentDataAdapter(
    private val paymentType: Int
) : RecyclerView.Adapter<PaymentDataAdapter.PaymentDataViewHolder>() {

    private val policies = mutableListOf<PolicyBasicModel>()

    /**
     * Inflate view to use on each item.
     * @param parent ViewGroup of layout.
     * @param viewType Type of view to be inlated.
     * @return Instance of PaymentDataViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentDataViewHolder =
        when (viewType) {
            PaymentDataType.PAYMENT_METHOD -> PaymentDataViewHolderMethod(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.recycler_payment_edit_data_payment, parent, false
                )
            )
            else -> PaymentDataViewHolderAddressAndOften(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.recycler_payment_edit_data, parent, false
                )
            )
        }


    /**
     * Bind each item to view holder.
     * @param holder Current view holder to receive data.
     * @param position Position of item to be binded.
     */
    override fun onBindViewHolder(holder: PaymentDataViewHolder, position: Int) {
        val itemType = getItemViewType(position)
        when (itemType) {
            PaymentDataType.PAYMENT_METHOD -> (holder as PaymentDataViewHolderMethod).bind(
                policies[position]
            )
            else -> (holder as PaymentDataViewHolderAddressAndOften).bind(
                policies[position],
                paymentType
            )
        }
    }

    /**
     * Get view type according to the view constructor param.
     * @param position Position of item to get Type.
     * @return Type got in constructor.
     */
    override fun getItemViewType(position: Int): Int = paymentType

    /**
     * Get size of list.
     * @return Size of list.
     */
    override fun getItemCount(): Int = policies.size

    /**
     * Payment list to be inserted into adapter.
     * @param payments List of payment.
     */
    fun submitList(paymentsList: List<PolicyBasicModel>) {
        policies.clear()
        policies.addAll(paymentsList)
        notifyDataSetChanged()
    }


    /**
     * Open class to handle different type of view.
     * @property itemView Layout inflated by onCreateVieHolder.
     */
    open class PaymentDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    /**
     * Class to handle view holder that represent single item.
     * @property itemView Each view inflated.
     */
    class PaymentDataViewHolderMethod(itemView: View) : PaymentDataViewHolder(itemView) {
        fun bind(paymentBasicaModel: PolicyBasicModel) {
            itemView.apply {
                when {
                    paymentBasicaModel.paymentTypeBankAgency.isNotEmpty() -> setBankInfo(
                        paymentBasicaModel
                    )
                    paymentBasicaModel.paymentTypeCreditCardNumber.isNotEmpty() -> setCreditCardInfo(
                        paymentBasicaModel
                    )
                }
            }
        }

        /**
         * Set text to accordint item view.
         * @receiver Item view layout.
         * @param paymentBasicaModel Model to get into.
         */
        private fun View.setCreditCardInfo(paymentBasicaModel: PolicyBasicModel) {
            recycler_payment_edit_data_payment_bank_group.showOrGone(false)
            recycler_payment_edit_data_payment_credit_group.showOrGone()
            recycler_payment_edit_data_payment_icon.backgroundDrawable =
                context.getDrawable(R.drawable.ic_extract_card)
            recycler_payment_edit_data_payment_subtitle.text =
                paymentBasicaModel.paymentTypeCreditCardNumber
            recycler_payment_edit_data_payment_card_date.text =
                "Vencimento do cartão: "+paymentBasicaModel.paymentTypeCreditDate

        }

        /**
         * Set text to accordint item view.
         * @receiver Item view layout.
         * @param paymentBasicaModel Model to get into.
         */
        private fun View.setBankInfo(paymentBasicaModel: PolicyBasicModel) {
            recycler_payment_edit_data_payment_bank_group.showOrGone()
            recycler_payment_edit_data_payment_credit_group.showOrGone(false)
            recycler_payment_edit_data_payment_icon.backgroundDrawable =
                context.getDrawable(R.drawable.ic_bradesco)
            recycler_payment_edit_data_payment_name_bank.text =
                paymentBasicaModel.paymentNameOwner
            recycler_payment_edit_data_payment_account.text =
                paymentBasicaModel.paymentTypeBankAccount
            recycler_payment_edit_data_payment_agency.text =
                paymentBasicaModel.paymentTypeBankAgency
        }
    }


    /**
     * Class to handle view holder that represent single item.
     * @property itemView Each view inflated.
     */
    class PaymentDataViewHolderAddressAndOften(itemView: View) :
        PaymentDataViewHolder(itemView) {

        /**
         * Bind itens to layout.
         * @param paymentBasicaModel Model to be used to inflate.
         * @param paymentType Type pf payment to be binded.
         */
        fun bind(paymentBasicaModel: PolicyBasicModel, paymentType: Int) {
            itemView.apply {
                when (paymentType) {
                    PaymentDataType.OFTEN -> setPaymentOften(paymentBasicaModel)
                    PaymentDataType.ADDRESS -> setPaymentAdress(paymentBasicaModel)
                }
            }
        }

        /**
         * Set text to accordint item view.
         * @receiver Item view layout.
         * @param paymentBasicaModel Model to get into.
         */
        private fun View.setPaymentAdress(paymentBasicaModel: PolicyBasicModel) {
            recycler_payment_edit_data_title.text =
                paymentBasicaModel.policyDescription
            recycler_payment_edit_data_subtitle.text =
               paymentBasicaModel.policyAdress
        }

        /**
         * Set text to accordint item view.
         * @receiver Item view layout.
         * @param paymentBasicaModel Model to get into.
         */
        private fun View.setPaymentOften(paymentBasicaModel: PolicyBasicModel) {
            recycler_payment_edit_data_title.text =
                paymentBasicaModel.policyDescription
            recycler_payment_edit_data_subtitle.text =
                paymentBasicaModel.paymentFrequency
        }
    }
}

/**
 * Object to handle differents type of Payment.
 */
object PaymentDataType {
    const val OFTEN = 1
    const val ADDRESS = 2
    const val PAYMENT_METHOD = 3
}