package com.nuryadincjr.githubuserapp.interfaces

import com.nuryadincjr.githubuserapp.pojo.SearchUsersResponse
import com.nuryadincjr.githubuserapp.pojo.Users
import com.nuryadincjr.githubuserapp.pojo.UsersResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @Headers("Authorization: token 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")
    @GET("users")
    fun getUsers(
    ): Call<List<UsersResponse>>

    @Headers("Authorization: token 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")
    @GET("users/{login}/followers")
    fun getFollowers(
        @Path("login") login: String
    ): Call<List<UsersResponse>>

    @Headers("Authorization: token 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")
    @GET("users/{login}/following")
    fun getFollowing(
        @Path("login") login: String
    ): Call<List<UsersResponse>>

    @Headers("Authorization: token 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")
    @GET("users/{login}")
    fun getUser(
        @Path("login") login: String
    ): Call<Users>

    @Headers("Authorization: token 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")
    @GET("search/users")
    fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: String? = null,
        @Query("per_page") perPage: String? = null,
        @Query("sort") sort: String? = null,
        @Query("order") order: String? = null,
    ): Call<SearchUsersResponse>

}