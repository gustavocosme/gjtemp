package prudential.pobmobilecustomerappandroid.model

data class PolicyBasicData(
    val billToDate: String,
    val endDate: String,
    val insuranceGrossPremium: Double,
    val insuranceNetPremium: Int,
    val iofValue: Double,
    val issueDate: String,
    val paidToDate: String,
    val paymentPeriodicity: String,
    val policyId: String,
    val policyStatus: String,
    val totalPaidValue: Double,

    val policyLocalStates: PolicyBasicModel
)