package prudential.pobmobilecustomerappandroid.network.api

import prudential.pobmobilecustomerappandroid.model.Payment
import prudential.pobmobilecustomerappandroid.model.Policy
import retrofit2.Call
import retrofit2.http.GET

/**
 * Interface to perform retrofit calls used on [Policy] sceneries.
 */
interface PolicyService {

    /**
     * Get list of policies on a specific URL with retrofit method [GET].
     * @return List of policies encapsuled on a [Call] response.
     */
    @GET("/api/policies")
    fun getPolicies(): Call<List<Policy>>

}