package prudential.pobmobilecustomerappandroid.model

/**
 * Class to help with response from RoomDB and Retrofit. All response will be encapsulated
 * in this class to become easy to get right response.
 * @property T Type of class to be encapsuled in response.
 */
sealed class Resource<T> {
    /**
     * Class to define loading state.
     * @property T Type of class to be encapsuled in response. Same type of parent class.
     */
    class Loading<T> : Resource<T>()

    /**
     * Class to define success state.
     * @property data Data of success response with defined type.
     * @property T Type of class to be encapsuled in response. Same type of parent class.
     */
    data class Success<T>(val data: T) : Resource<T>()

    /**
     * Class to define failure state.
     * @property T Type of class to be encapsuled in response. Same type of parent class.
     * @property throwable Throwable message defined in catch block.
     * @property statusCode HTTP status code in case of retrofit error response.
     * @property message Message from retrofit error.
     */
    data class Failure<T>(
        val throwable: Throwable? = null,
        val statusCode: Int? = null,
        val message: String? = ""
    ) : Resource<T>()
}