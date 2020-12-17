package prudential.pobmobilecustomerappandroid.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import prudential.pobmobilecustomerappandroid.network.RetrofitService.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Object to create single instance of retrofit service.
 */
object RetrofitService {

//    const val BASE_URL = "http://mundosafari.com.br/mobile/"
    const val BASE_URL = "http://locahost"

    /**
     * Create retrofit service based on given interface.
     * @param T Type to represent a given interface to create retrofit service.
     * @return T Given interface with retrofit service created.
     */
    inline fun <reified T> createService(): T {
        val interceptor = httpLoggingInterceptor()

        val client = getOkHttpClient(interceptor)
        val gson = getGsonBuild()
        val retrofit = getRetrofitBuild(client, gson)

        val serviceClass = T::class.java
        return retrofit.create(serviceClass)
    }

    /**
     * Add log intercept to show every state on each request.
     * @return Instance of Log Intercept.
     */
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor
    }

    /**
     * Create a retrofit instance with given http client and json converter.
     * @param client Http custom client.
     * @param gson Gson to convert JSON to Object.
     * @return Retrofit instance.
     */
    fun getRetrofitBuild(
        client: OkHttpClient,
        gson: Gson?
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    /**
     * Build GSON to be used as converter in retrofit.
     * @return Instance of Gson.
     */
    fun getGsonBuild(): Gson? = GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    /**
     * Create instance of Http Client with custom interceptor.
     * @param interceptor Custom interceptor.
     * @return Instance of OkHttpClient.
     */
    fun getOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(interceptor).build()

}