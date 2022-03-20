package com.nuryadincjr.githubuserapp.config

import androidx.viewbinding.BuildConfig
import com.nuryadincjr.githubuserapp.BuildConfig.*
import com.nuryadincjr.githubuserapp.interfaces.ApiService
import com.nuryadincjr.githubuserapp.util.Constant.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {

            val logger = HttpLoggingInterceptor()
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                logger.setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                logger.setLevel(HttpLoggingInterceptor.Level.NONE)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor {
                    val request = it.request()
                    val requestBuilder = request.newBuilder()
                        .addHeader("Authorization", KEY_TOKEN)
                    val newRequest = requestBuilder.build()
                    it.proceed(newRequest)
                }
                .addNetworkInterceptor(loggingInterceptor)
                .build()


            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}