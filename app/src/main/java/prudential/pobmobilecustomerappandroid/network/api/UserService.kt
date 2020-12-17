package prudential.pobmobilecustomerappandroid.network.api

import prudential.pobmobilecustomerappandroid.model.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Interface to perform retrofit calls used on [User] sceneries.
 */
interface UserService {
    /**
     * Get list of users on a specific URL with retrofit method [GET].
     * @return List of users encapsuled on a [Call] response.
     */
    @GET("test/obj.json")
    fun getUsers(): Call<List<User>>

    /**
     * Call api to perform login with user info using [POST] method.
     * @param user User CPF or CNPJ used as login username.
     * @param password Password for the current username.
     * @return Current [User] encapsuled on a [Call] response.
     */
    @POST("test/obj.json")
    @FormUrlEncoded
    fun login(
        @Field("user") user: String,
        @Field("password") password: String
    ): Call<User>

    /**
     * Call api to perform action when user forgot password using [POST] method.
     * @param user Current user to be used on password recovery.
     * @return Current [User] encapsuled on a [Call] response.
     */
    @POST("test/obj.json")
    @FormUrlEncoded
    fun forgotPassword(
        @Field("user") user: String
    ): Call<User>

    /**
     * Call api to perform action when user creates a new password.
     * @param user Current user to be used on new password creation.
     * @param password New password.
     * @param date Date of born of current user. Used to validate user.
     * @return Current [User] encapsuled on a [Call] response.
     */
    @POST("test/obj.json")
    @FormUrlEncoded
    fun newPassword(
        @Field("user") user: String,
        @Field("password") password: String,
        @Field("date") date: String
    ): Call<User>
}