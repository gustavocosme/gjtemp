package prudential.pobmobilecustomerappandroid.model

data class InsuredItem(
    val incomes: List<Income>,
    val occupations: List<Occupation>,
    val taxResidences: TaxResidences
)