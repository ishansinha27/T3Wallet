package com.example.t3_wallet.retrofit
import okhttp3.OkHttpClient
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL = "https://t3backend.onrender.com/"
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)  // Set connection timeout
        .readTimeout(30, TimeUnit.SECONDS)     // Set read timeout
        .writeTimeout(30, TimeUnit.SECONDS)    // Set write timeout
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiInterface: ApiInterface = retrofit.create(ApiInterface::class.java)

}