package prudential.pobmobilecustomerappandroid.model

import android.content.Context
import prudential.pobmobilecustomerappandroid.R
import java.io.Serializable

data class PaymentBasicaModel(
    val isPayed: Boolean,
    val isFuturePayed: Boolean = false,
    val isSPL: Boolean = false,
    val isCreditCard: Boolean = false,
    val isDebit: Boolean = false,
    val isBillet: Boolean = false,
    val paymentDescription: String,
    val totalPaid: Double,
    val paidDate: String,
    val paymentType: String = "",
    val isAlert: Boolean = false,
    val isAlertType: Int = 0,
    val isAlertMessage: String = "Por conta do horário, a sua solicitação não poderá ser realizada. Como o seu seguro completou 60 dias de vencido, procure o atendimento da Prudential."

) : Serializable {


    fun getIcon(context: Context) = when {
        isPayed and !isSPL -> context.getDrawable(R.drawable.ic_extract_ok)
        !isPayed and !isFuturePayed -> context.getDrawable(R.drawable.ic_extract_atencion_orange)
        isFuturePayed -> context.getDrawable(R.drawable.ic_extract_time_future)
        isSPL and isPayed -> context.getDrawable(R.drawable.ic_extract_premium)
        else -> context.getDrawable(R.drawable.ic_extract_atencion_orange)
    }

    fun getLineColor(context: Context) = when {
        isPayed -> context.getColor(R.color.colorGreenSuccess)
        !isPayed and !isFuturePayed -> context.getColor(R.color.attentionColor)
        isFuturePayed -> context.getColor(R.color.colorWhite)
        else -> context.getColor(R.color.colorGreenSuccess)
    }

    fun showOrHideTooltip(): Boolean = when {
        isFuturePayed or isPayed -> false
        else -> true
    }

    fun showOrHidePayButton() = when {
        !isPayed and isDebit -> true
        !isPayed and isCreditCard and !isFuturePayed -> true
        !isPayed and isBillet and isFuturePayed -> true
        !isPayed and isFuturePayed -> false
        else -> false
    }

    fun showOrHideTryText() = when {
        !isPayed and isCreditCard and !isFuturePayed-> true
        else -> false
    }

    fun showOrHideDate() = !showOrHidePayButton()

    fun showOrHideFutureText() = when {
        isFuturePayed and isBillet -> false
        !isFuturePayed -> false
        else -> true
    }

    fun showOrHideArrowRight() = when {
        isFuturePayed and !isBillet -> false
        else -> true
    }

    fun getPaidValue() = when {
        //isFuturePayed and isBillet -> "R$ $totalPaid - $paidDate"
        else -> "R$ $totalPaid"
    }

}