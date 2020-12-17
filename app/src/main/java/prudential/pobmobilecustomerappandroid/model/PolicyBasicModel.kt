package prudential.pobmobilecustomerappandroid.model

import android.content.Context
import prudential.pobmobilecustomerappandroid.R

data class PolicyBasicModel(
    var isSelected: Boolean = false,
    var isPaid: Boolean = true,
    val policyId: String = "",
    val policyDescription: String = "",
    val policyInsuredName: String = "",
    val paymentType: String = "",
    val paymentTypeBankAgency: String = "",
    val paymentTypeBankAccount: String = "",
    val paymentTypeCreditCardNumber: String = "",
    val paymentTypeCreditDate: String = "",
    val paymentNameOwner: String = "",
    val paymentFrequency: String = "",
    val policyAdress: String = ""
) {

    fun getLineColor(context: Context) = when {
        isPaid -> context.getColor(R.color.colorPrimary)
        else -> context.getColor(R.color.attentionColor)
    }

    fun getPolicyIdAndInsuredTitleColor(context: Context) = when {
        isPaid and !isSelected -> context.getColor(R.color.colorGreyCard)
        isPaid and isSelected -> context.getColor(R.color.colorPrimary)
        else -> context.getColor(R.color.colorGreyCard)
    }

    fun getPolicyDescriptionColor(context: Context) = when {
        isPaid -> context.getColor(R.color.colorPrimary)
        !isPaid -> context.getColor(R.color.attentionColor)
        else -> context.getColor(R.color.colorPrimary)
    }

    fun getInsuredNameColor(context: Context) = when {
        isPaid and !isSelected -> context.getColor(R.color.colorBlackText)
        isPaid and isSelected -> context.getColor(R.color.colorPrimary)
        else -> context.getColor(R.color.colorBlackText)
    }

    fun getBackgroundColor(context: Context) = when {
        isPaid and isSelected -> context.getColor(R.color.colorBlueSelectPolicy)
        isPaid and !isSelected -> context.getColor(R.color.colorWhite)
        !isPaid and isSelected -> context.getColor(R.color.oolorAttentionExtractSelect)
        !isPaid and !isSelected -> context.getColor(R.color.colorWhite)
        else -> context.getColor(R.color.colorWhite)
    }

    object LayoutType {
        const val EXTRACT = 0
        const val PAYMENT_SELECT_ALL = 1
    }

    object PaymentType {
        const val DEBIT = "DEBIT"
        const val CREDIT_CARD = "CREDIT_CARD"
        const val BILLET = "BILLET"
        const val SPL = "SPL"
    }

}