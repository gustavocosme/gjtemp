package prudential.pobmobilecustomerappandroid.model

/**
 * Base class to handle data tutorial.
 * @property title Title of tutorial.
 * @property description Description of tutorial.
 * @property icon Custom icon of tutorial.
 */
data class DataTutorial(
    var title: String = "",
    var description: String = "",
    var icon: Int = -1
)