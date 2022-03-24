package com.nuryadincjr.githubuserapp.data.remote.retrofit

import androidx.viewbinding.BuildConfig
import com.nuryadincjr.githubuserapp.BuildConfig.*
import com.nuryadincjr.githubuserapp.util.Constant.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.String.format

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val logger = HttpLoggingInterceptor()
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                logger.setLevel(Level.BODY)
            } else logger.setLevel(Level.NONE)

            val client = OkHttpClient.Builder()
                .addInterceptor {
                    val request = it.request()
                    val requestBuilder = request.newBuilder()
                        .addHeader("Authorization", format("token %s", API_KEY))
                        .build()
                    it.proceed(requestBuilder)
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