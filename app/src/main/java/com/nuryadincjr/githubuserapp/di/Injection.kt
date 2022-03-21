package com.nuryadincjr.githubuserapp.di

import android.content.Context
import com.nuryadincjr.githubuserapp.data.local.room.UsersDatabase
import com.nuryadincjr.githubuserapp.data.remote.retrofit.ApiConfig
import com.nuryadincjr.githubuserapp.data.repository.FollowRepository
import com.nuryadincjr.githubuserapp.data.repository.UsersRepository

object Injection {
    inline fun <reified T> provideRepository(context: Context): T? {
        val apiService = ApiConfig.getApiService()
        val database = UsersDatabase.getInstance(context)
        val dao = database.usersDao()

        return when (T::class.java) {
            UsersRepository::class.java -> UsersRepository.getInstance(apiService, dao) as T
            FollowRepository::class.java -> FollowRepository.getInstance(apiService, dao) as T
            else -> null
        }
    }
}