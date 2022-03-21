package com.nuryadincjr.githubuserapp.data.remote.retrofit

import com.nuryadincjr.githubuserapp.data.remote.response.SearchUsersResponse
import com.nuryadincjr.githubuserapp.data.remote.response.Users
import com.nuryadincjr.githubuserapp.data.remote.response.UsersResponse
import com.nuryadincjr.githubuserapp.util.Constant.TOKEN
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @Headers("Authorization: token $TOKEN")
    @GET("users")
    suspend fun getUsersV1(): List<UsersResponse>

    @Headers("Authorization: token $TOKEN")
    @GET("users/{login}")
    suspend fun getUserV1(@Path("login") login: String): Users

    @Headers("Authorization: token $TOKEN")
    @GET("search/users")
    suspend fun searchUsersV1(
        @Query("q") query: String? = null,
        @Query("page") page: String? = null,
        @Query("per_page") perPage: String? = null,
        @Query("sort") sort: String? = null,
        @Query("order") order: String? = null,
    ): SearchUsersResponse

    @Headers("Authorization: token $TOKEN")
    @GET("users")
    fun getUsers(
    ): Call<List<UsersResponse>>

    @Headers("Authorization: token $TOKEN")
    @GET("users/{login}")
    fun getUser(
        @Path("login") login: String
    ): Call<Users>

    @Headers("Authorization: token $TOKEN")
    @GET("search/users")
    fun searchUsers(
        @Query("q") query: String? = null,
        @Query("page") page: String? = null,
        @Query("per_page") perPage: String? = null,
        @Query("sort") sort: String? = null,
        @Query("order") order: String? = null,
    ): Call<SearchUsersResponse>

    @Headers("Authorization: token $TOKEN")
    @GET("users/{login}/followers")
    fun getFollowers(
        @Path("login") login: String
    ): Call<List<UsersResponse>>

    @Headers("Authorization: token $TOKEN")
    @GET("users/{login}/following")
    fun getFollowing(
        @Path("login") login: String
    ): Call<List<UsersResponse>>
}