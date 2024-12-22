package ru.webarmour.crypto_task.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import ru.webarmour.crypto_task.BuildConfig


const val BASE_URL = "https://api.apilayer.com/exchangerates_data/"
interface CryptoApi {


    @GET("latest")
    suspend fun getCurrency(
        @Query("base") base: String,
        @Query("symbols") symbols: List<String> = listOf("AED, USD, BYN, RUB, EUR")
    ): CurrencyDto

}

object RetrofitInstance {

    private const val KEY_PARAM = "apikey"


    private val okHttpClient = OkHttpClient.Builder()

        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val newUrl = originalRequest
                .url
                .newBuilder()
                .addQueryParameter(KEY_PARAM, BuildConfig.API_KEY)
                .build()
            val newRequest = originalRequest.newBuilder()
                .url(newUrl)
                .build()
            chain.proceed(newRequest)

        }
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val retrofit: CryptoApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create()
}