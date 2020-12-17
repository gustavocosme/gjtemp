package prudential.pobmobilecustomerappandroid.extensions

import prudential.pobmobilecustomerappandroid.model.Resource
import retrofit2.Response

/**
 * Try to execute [Resource.Success] with callback inside a try block.
 * @param T Type should be implicit, according to the return.
 * @return [Resource.Success] if perform ok.
 * @exception Any exception will retorn [Resource.Failure]
 */
inline fun <T> tryResource(callback: () -> T): Resource<T> = try {
    Resource.Success(callback())
} catch (e: Exception) {
    Resource.Failure(e)
}

/**
 * Check response of retrofit inside a try block.
 * @param T Type should be implicit, according to the return.
 * @return [Resource.Success] with body of repsonse if perform ok.
 * @exception Any exception will retorn [Resource.Failure] with status code or exception.
 */
inline fun <T> tryResponse(callback: () -> Response<T>): Resource<T> = try {
    val result = callback()
    when {
        result.isSuccessful -> {
            val body = result.body() ?: "ok" as T
            when {
                result.code() == 200 || body != null -> Resource.Success(body)
                else -> Resource.Failure(statusCode = result.code())
            }
        }
        else -> Resource.Failure(statusCode = result.code())
    }
} catch (e: Exception) {
    Resource.Failure(e)
}