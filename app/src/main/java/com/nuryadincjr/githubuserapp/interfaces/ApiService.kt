package com.nuryadincjr.githubuserapp.interfaces

import com.nuryadincjr.githubuserapp.pojo.UserResponse
import com.nuryadincjr.githubuserapp.pojo.UsersResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users")
    fun getUsers(): Call<List<UsersResponseItem>>

    @GET("users/{login}")
    fun getUserLogin(
        @Path("login") login: String
    ): Call<UserResponse>
}