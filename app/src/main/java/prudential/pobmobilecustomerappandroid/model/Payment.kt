package prudential.pobmobilecustomerappandroid.model

data class Payment(
    val cardIssuer: String,
    val cardNumber: String,
    val cdBank: String,
    val cliName: String,
    val codDebitBank: String,
    val docEmCobReg: String,
    val dtEnvioCobReg: String,
    val dtExpiracy: String,
    val dtPayment: String,
    val dtStartTem: Int,
    val dtVencReg: String,
    val dueDate: Int,
    val hrEnvioCobReg: String,
    val isPayable: Boolean,
    val numDebitAccount: String,
    val numDebitAgency: String,
    val numDvDebitAccount: String,
    val numDvDebitAgency: String,
    val paymentStatus: String,
    val vlDebito: Double
)