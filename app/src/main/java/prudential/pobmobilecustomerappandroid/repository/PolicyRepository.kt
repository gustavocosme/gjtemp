package prudential.pobmobilecustomerappandroid.repository

import android.app.Application
import prudential.pobmobilecustomerappandroid.model.Policy
import prudential.pobmobilecustomerappandroid.model.PolicyBasicModel
import prudential.pobmobilecustomerappandroid.network.RetrofitService
import prudential.pobmobilecustomerappandroid.network.api.PolicyService
import retrofit2.Response
import retrofit2.awaitResponse

/**
 * Repository to manage database and netowork access and returns data relative to [PolicyBasicModel] object.
 * @constructor Receive context to be used on databse instance creation.
 */
class PolicyRepository(application: Application) {

    private val policyService by lazy { RetrofitService.createService<PolicyService>() }
    private val paymentRepository by lazy { PaymentRepository(application) }


    /**
     * List policies from API
     * @
     */
    private suspend fun getPoliciesFromApi(): Response<List<Policy>> = policyService
        .getPolicies()
        .awaitResponse()

    suspend fun getPolociesCompleteData(policies: List<Policy>): List<PolicyBasicModel> {
        val policiesBasicData = mutableListOf<PolicyBasicModel>()
        policies.forEach {
            val policyBasicModel = PolicyBasicModel()
            val payments = paymentRepository.getPaymentService(it.policyBasicData.policyId)
            policyBasicModel.isPaid = payments.none { it.dtPayment == "" }
        }
        return policiesBasicData
    }


    /**
     * Fake list os policies.
     * @return List of policies.
     */
    fun getPolicies() = listOf(
        PolicyBasicModel(
            isSelected = true,
            policyId = "000000001",
            policyDescription = "Vida Inteira Mais",
            policyInsuredName = "Sandra Regina Pereira",
            isPaid = true
        )
    )

    fun getSelectedPoliciesByIndex(indexs: MutableList<Int>): List<PolicyBasicModel> = indexs.map {
        getPolicies4()[it]
    }

    /**
     * Fake list os policies.
     * @return List of policies.
     */
    fun getPolicies2() = mutableListOf(
        PolicyBasicModel(
            isSelected = true,
            policyId = "000000001",
            policyDescription = "Vida Inteira Mais",
            policyInsuredName = "Sandra Regina Pereira",
            isPaid = true
        ),
        PolicyBasicModel(
            policyId = "000000002",
            policyDescription = "Temporário",
            policyInsuredName = "Sandra Regina Pereira",
            isPaid = true
        )
    )

    /**
     * Fake list os policies.
     * @return List of policies.
     */
    fun getPolicies3() = mutableListOf(
        PolicyBasicModel(
            isSelected = true,
            policyId = "000000001",
            policyDescription = "Vida Inteira Mais",
            policyInsuredName = "Sandra Regina Pereira",
            isPaid = false
        ),
        PolicyBasicModel(
            policyId = "000000002",
            policyDescription = "Temporário",
            policyInsuredName = "Sandra Regina Pereira",
            isPaid = true
        )
    )

    /**
     * Fake list os policies.
     * @return List of policies.
     */
    fun getPolicies4() = mutableListOf(
        PolicyBasicModel(
            isSelected = true,
            policyId = "000000001",
            policyDescription = "Vida Inteira Mais",
            policyInsuredName = "Sandra Regina Pereira",
            isPaid = false,
            paymentType = PolicyBasicModel.PaymentType.CREDIT_CARD,
            paymentTypeCreditCardNumber = "**** **** **** 8293",
            paymentTypeCreditDate = "02/27",
            paymentFrequency = "Mensal - Dia 05",
            policyAdress = "Rua Visconde de Morais, 238 Niterói - RJ"
        ),
        PolicyBasicModel(
            policyId = "000000002",
            policyDescription = "Temporário",
            policyInsuredName = "Sandra Regina Pereira",
            isPaid = true,
            paymentType = PolicyBasicModel.PaymentType.CREDIT_CARD,
            paymentTypeCreditCardNumber = "**** **** **** 8293",
            paymentTypeCreditDate = "02/27",
            paymentFrequency = "Mensal - Dia 04",
            policyAdress = "Rua Visconde de Morais, 238 Niterói - RJ"
        ),
        PolicyBasicModel(
            policyId = "000000002",
            policyDescription = "Temporário",
            policyInsuredName = "Sandra Regina Pereira",
            isPaid = true,
            paymentType = PolicyBasicModel.PaymentType.DEBIT,
            paymentTypeBankAgency = "2283",
            paymentTypeBankAccount = "28398475-8",
            paymentNameOwner = "Maura da CostaRodrigues",
            paymentFrequency = "Mensal - Dia 03",
            policyAdress = "Rua Visconde de Morais, 238 Niterói - RJ"
        )
    )

}
