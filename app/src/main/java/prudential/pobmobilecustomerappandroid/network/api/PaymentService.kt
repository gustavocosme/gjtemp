package prudential.pobmobilecustomerappandroid.network.api

import prudential.pobmobilecustomerappandroid.model.Payment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Interface to perform retrofit calls used on [Payment] sceneries.
 */
interface PaymentService {

    /**
     * Get list of payments on a specific URL with retrofit method [GET].
     * @return List of payments encapsuled on a [Call] response.
     */
    @GET("/api/payments/{id}")
    suspend fun getPayments(@Path("id") id: String): List<Payment>

}