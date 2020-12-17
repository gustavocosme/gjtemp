package prudential.pobmobilecustomerappandroid.repository

import android.app.Application
import kotlinx.coroutines.delay
import prudential.pobmobilecustomerappandroid.model.Payment
import prudential.pobmobilecustomerappandroid.model.PaymentBasicaModel
import prudential.pobmobilecustomerappandroid.network.RetrofitService
import prudential.pobmobilecustomerappandroid.network.api.PaymentService
import retrofit2.Response
import retrofit2.awaitResponse

/**
 * Repository to manage database and netowork access and returns data relative to [Payment] object.
 * @constructor Receive context to be used on databse instance creation.
 * @property application Contex to be used on database creation.
 */
class PaymentRepository(val application: Application) {

    val paymentService by lazy { RetrofitService.createService<PaymentService>() }

    /**
     * Get list of payments using payment service.
     * @return List of payments.
     */
    suspend fun getPaymentService(id: String): List<Payment> = paymentService.getPayments(id)
    /**
     * Get list of payments mocked by index.
     * @param index of methods.
     * @return List of payments according to the index.
     */
    suspend fun getPaymentsByIndex(index: Int) = when (index) {
        0 -> getPayments()
        1 -> getPayments2()
        2 -> getPayments3()
        else -> getPayments()
    }

    /**
     * Fake list os payments.
     * @return List of payments.
     */
    fun getPayments(): List<PaymentBasicaModel> = mutableListOf(
        PaymentBasicaModel(
            isPayed = false,
            paymentDescription = "Pagamento pendente",
            paidDate = "em 09/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            isAlert = true,
            isAlertType = 0,
            totalPaid = 197.90
        ),
        PaymentBasicaModel(
            isPayed = false,
            paymentDescription = "Pagamento pendente",
            paidDate = "em 09/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            isAlert = true,
            isAlertType = 1,
            totalPaid = 197.90
        ),
        PaymentBasicaModel(
            isPayed = false,
            paymentDescription = "Pagamento pendente",
            paidDate = "em 09/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            isAlert = true,
            isAlertType = 2,
            totalPaid = 197.90
        ),
        PaymentBasicaModel(
            isPayed = false,
            paymentDescription = "Pagamento pendente",
            paidDate = "em 09/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            isAlert = true,
            isAlertType = 3,
            totalPaid = 197.90
        ),
        PaymentBasicaModel(
            isPayed = false,
            paymentDescription = "Pagamento pendente",
            paidDate = "em 09/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            isAlert = true,
            isAlertType = 4,
            totalPaid = 197.90
        ),
        PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 08/11",
            paymentType = "Débito em conta Santander",
            isDebit = true,
            totalPaid = 343.2
        ),
        PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 07/11",
            paymentType = "Boleto",
            isBillet = true,
            totalPaid = 234.90
        ),
        PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 06/11",
            paymentType = "Cartão de crédito final 9292",
            totalPaid = 254.20,
            isCreditCard = true
        ),
        PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em em 11",
            paymentType = "Cartão de crédito final 9292",
            totalPaid = 244.10,
            isCreditCard = true
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            totalPaid = 245.90,
            isCreditCard = true
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            totalPaid = 986.90,
            isCreditCard = true
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            totalPaid = 567.60,
            isCreditCard = true
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            totalPaid = 456.20,
            isCreditCard = true
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            totalPaid = 234.90,
            isCreditCard = true
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            totalPaid = 345.90,
            isCreditCard = true
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            totalPaid = 667.30,
            isCreditCard = true
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            totalPaid = 543.90,
            isCreditCard = true
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            totalPaid = 746.90,
            isCreditCard = true
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            totalPaid = 874.90,
            isCreditCard = true
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            totalPaid = 765.20,
            isCreditCard = true
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            totalPaid = 123.90,
            isCreditCard = true
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            totalPaid = 477.90,
            isCreditCard = true
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            totalPaid = 432.20,
            isCreditCard = true
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            totalPaid = 133.90,
            isCreditCard = true
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            totalPaid = 233.90,
            isCreditCard = true
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            totalPaid = 798.90,
            isCreditCard = true
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            totalPaid = 753.30,
            isCreditCard = true
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            totalPaid = 543.50,
            isCreditCard = true
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            totalPaid = 783.90,
            isCreditCard = true
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            totalPaid = 457.90,
            isCreditCard = true
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            totalPaid = 354.90,
            isCreditCard = true
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            totalPaid = 345.90,
            isCreditCard = true
        )
    )

    /**
     * Fake list os payments.
     * @return List of payments.
     */
    fun getPayments2(): List<PaymentBasicaModel> = mutableListOf(
        PaymentBasicaModel(
            isPayed = false,
            isFuturePayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 09/11",
            paymentType = "Boleto",
            totalPaid = 197.90,
            isBillet = true
        ),
        PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 08/11",
            paymentType = "Débito em conta Santander",
            totalPaid = 197.90,
            isDebit = true
        ),
        PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 07/11",
            paymentType = "Boleto",
            totalPaid = 197.90,
            isBillet = true
        ),
        PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 06/11",
            paymentType = "Cartão de crédito final 9292",
            totalPaid = 197.90,
            isCreditCard = true
        ),
        PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em em 11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        ), PaymentBasicaModel(
            isPayed = true,
            paymentDescription = "Pagamento de prêmio",
            paidDate = "em 04/11",
            paymentType = "Cartão de crédito final 9292",
            isCreditCard = true,
            totalPaid = 197.90
        )
    )

    /**
     * Fake list os payments.
     * @return List of payments.
     */
    suspend fun getPayments3(): List<PaymentBasicaModel> {
        delay(3000)
        return mutableListOf(
            PaymentBasicaModel(
                isPayed = false,
                isFuturePayed = true,
                paymentDescription = "Pagamento futuro",
                paidDate = "em 09/11",
                paymentType = "Cartão de crédito final 9292",
                isCreditCard = true,
                totalPaid = 234.90
            ),
            PaymentBasicaModel(
                isPayed = true,
                isSPL = true,
                paymentDescription = "Pagamento do prêmio",
                paidDate = "em 08/11",
                paymentType = "AssistÊncia Financeira (SPL)",

                totalPaid = 542.10
            ),
            PaymentBasicaModel(
                isPayed = true,
                paymentDescription = "Pagamento de prêmio",
                paidDate = "em 07/11",
                paymentType = "Débito em conta Santander",
                isDebit = true,
                totalPaid = 345.60
            ),
            PaymentBasicaModel(
                isPayed = true,
                paymentDescription = "Pagamento de prêmio",
                paidDate = "em 06/11",
                paymentType = "Boleto",
                isBillet = true,
                totalPaid = 234.40
            ), PaymentBasicaModel(
                isPayed = true,
                paymentDescription = "Pagamento de prêmio",
                paidDate = "em 11/11",
                paymentType = "Cartão de crédito final 9292",
                isCreditCard = true,
                totalPaid = 764.20
            ),
            PaymentBasicaModel(
                isPayed = true,
                paymentDescription = "Pagamento de prêmio",
                paidDate = "em 04/11",
                paymentType = "Cartão de crédito final 9292",
                isCreditCard = true,
                totalPaid = 413.40
            ),
            PaymentBasicaModel(
                isPayed = true,
                paymentDescription = "Pagamento de prêmio",
                paidDate = "em 04/11",
                paymentType = "Cartão de crédito final 9292",
                isCreditCard = true,
                totalPaid = 197.90
            ),
            PaymentBasicaModel(
                isPayed = true,
                paymentDescription = "Pagamento de prêmio",
                paidDate = "em 04/11",
                paymentType = "Cartão de crédito final 9292",
                isCreditCard = true,
                totalPaid = 197.90
            ),
            PaymentBasicaModel(
                isPayed = true,
                paymentDescription = "Pagamento de prêmio",
                paidDate = "em 04/11",
                paymentType = "Cartão de crédito final 9292",
                isCreditCard = true,
                totalPaid = 197.90
            ),
            PaymentBasicaModel(
                isPayed = true,
                paymentDescription = "Pagamento de prêmio",
                paidDate = "em 04/11",
                paymentType = "Cartão de crédito final 9292",
                isCreditCard = true,
                totalPaid = 197.90
            ),
            PaymentBasicaModel(
                isPayed = true,
                paymentDescription = "Pagamento de prêmio",
                paidDate = "em 04/11",
                paymentType = "Cartão de crédito final 9292",
                isCreditCard = true,
                totalPaid = 197.90
            ),
            PaymentBasicaModel(
                isPayed = true,
                paymentDescription = "Pagamento de prêmio",
                paidDate = "em 04/11",
                paymentType = "Cartão de crédito final 9292",
                isCreditCard = true,
                totalPaid = 197.90
            ),
            PaymentBasicaModel(
                isPayed = true,
                paymentDescription = "Pagamento de prêmio",
                paidDate = "em 04/11",
                paymentType = "Cartão de crédito final 9292",
                isCreditCard = true,
                totalPaid = 197.90
            ),
            PaymentBasicaModel(
                isPayed = true,
                paymentDescription = "Pagamento de prêmio",
                paidDate = "em 04/11",
                paymentType = "Cartão de crédito final 9292",
                isCreditCard = true,
                totalPaid = 197.90
            ),
            PaymentBasicaModel(
                isPayed = true,
                paymentDescription = "Pagamento de prêmio",
                paidDate = "em 04/11",
                paymentType = "Cartão de crédito final 9292",
                isCreditCard = true,
                totalPaid = 197.90
            ),
            PaymentBasicaModel(
                isPayed = true,
                paymentDescription = "Pagamento de prêmio",
                paidDate = "em 04/11",
                paymentType = "Cartão de crédito final 9292",
                isCreditCard = true,
                totalPaid = 197.90
            ),
            PaymentBasicaModel(
                isPayed = true,
                paymentDescription = "Pagamento de prêmio",
                paidDate = "em 04/11",
                paymentType = "Cartão de crédito final 9292",
                isCreditCard = true,
                totalPaid = 197.90
            ),
            PaymentBasicaModel(
                isPayed = true,
                paymentDescription = "Pagamento de prêmio",
                paidDate = "em 04/11",
                paymentType = "Cartão de crédito final 9292",
                isCreditCard = true,
                totalPaid = 197.90
            ),
            PaymentBasicaModel(
                isPayed = true,
                paymentDescription = "Pagamento de prêmio",
                paidDate = "em 04/11",
                paymentType = "Cartão de crédito final 9292",
                isCreditCard = true,
                totalPaid = 197.90
            ),
            PaymentBasicaModel(
                isPayed = true,
                paymentDescription = "Pagamento de prêmio",
                paidDate = "em 04/11",
                paymentType = "Cartão de crédito final 9292",
                isCreditCard = true,
                totalPaid = 197.90
            ),
            PaymentBasicaModel(
                isPayed = true,
                paymentDescription = "Pagamento de prêmio",
                paidDate = "em 04/11",
                paymentType = "Cartão de crédito final 9292",
                isCreditCard = true,
                totalPaid = 197.90
            ),
            PaymentBasicaModel(
                isPayed = true,
                paymentDescription = "Pagamento de prêmio",
                paidDate = "em 04/11",
                paymentType = "Cartão de crédito final 9292",
                isCreditCard = true,
                totalPaid = 197.90
            ),
            PaymentBasicaModel(
                isPayed = true,
                paymentDescription = "Pagamento de prêmio",
                paidDate = "em 04/11",
                paymentType = "Cartão de crédito final 9292",
                isCreditCard = true,
                totalPaid = 197.90
            ),
            PaymentBasicaModel(
                isPayed = true,
                paymentDescription = "Pagamento de prêmio",
                paidDate = "em 04/11",
                paymentType = "Cartão de crédito final 9292",
                isCreditCard = true,
                totalPaid = 197.90
            ),
            PaymentBasicaModel(
                isPayed = true,
                paymentDescription = "Pagamento de prêmio",
                paidDate = "em 04/11",
                paymentType = "Cartão de crédito final 9292",
                isCreditCard = true,
                totalPaid = 197.90
            ),
            PaymentBasicaModel(
                isPayed = true,
                paymentDescription = "Pagamento de prêmio",
                paidDate = "em 04/11",
                paymentType = "Cartão de crédito final 9292",
                isCreditCard = true,
                totalPaid = 197.90
            )
        )
    }

}