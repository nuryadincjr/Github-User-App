package com.nuryadincjr.githubuserapp.data.remote.retrofit

import com.nuryadincjr.githubuserapp.data.remote.response.SearchUsersResponse
import com.nuryadincjr.githubuserapp.data.remote.response.Users
import com.nuryadincjr.githubuserapp.data.remote.response.UsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    fun getUsers(
    ): Call<List<UsersResponse>>

    @GET("users/{login}")
    fun getUser(
        @Path("login") login: String
    ): Call<Users>

    @GET("search/users")
    fun searchUsers(
        @Query("q") query: String? = null,
        @Query("page") page: String? = null,
        @Query("per_page") perPage: String? = null,
        @Query("sort") sort: String? = null,
        @Query("order") order: String? = null,
    ): Call<SearchUsersResponse>

    @GET("users/{login}/followers")
    fun getFollowers(
        @Path("login") login: String
    ): Call<List<UsersResponse>>

    @GET("users/{login}/following")
    fun getFollowing(
        @Path("login") login: String
    ): Call<List<UsersResponse>>
}