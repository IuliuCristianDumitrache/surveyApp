package com.dumitrachecristian.surveyapp.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiServiceProvider {

    private const val BASE_URL = "https://xm-assignment.web.app"

    fun getClient(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient())
            .build()
            .create(ApiService::class.java)
    }

    private fun getOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val basicInterceptor = Interceptor { chain ->
            val original = chain.request()

            val newRequest = original
                .newBuilder()
                .build()
            chain.proceed(newRequest)
        }

        return OkHttpClient.Builder()
            .addInterceptor(basicInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }
}