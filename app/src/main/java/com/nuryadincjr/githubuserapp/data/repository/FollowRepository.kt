package com.nuryadincjr.githubuserapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nuryadincjr.githubuserapp.data.local.entity.UsersEntity
import com.nuryadincjr.githubuserapp.data.local.room.UsersDao
import com.nuryadincjr.githubuserapp.data.remote.retrofit.ApiService
import com.nuryadincjr.githubuserapp.data.remote.response.Users
import com.nuryadincjr.githubuserapp.data.remote.response.UsersResponse
import com.nuryadincjr.githubuserapp.util.Constant
import com.nuryadincjr.githubuserapp.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowRepository private constructor(
    private val service: ApiService,
    private val usersDao: UsersDao
) {
    private var followersArrayListItem: ArrayList<Users>? = null
    private var followingArrayListItem: ArrayList<Users>? = null

    private val _followingResponse = MutableLiveData<List<UsersResponse>>()
    val followingResponse: LiveData<List<UsersResponse>> = _followingResponse

    private val _followersResponse = MutableLiveData<List<UsersResponse>>()
    val followersResponse: LiveData<List<UsersResponse>> = _followersResponse

    private val _followersResponseItem = MutableLiveData<List<Users>>()
    val followersResponseItem: LiveData<List<Users>> = _followersResponseItem

    private val _followingResponseItem = MutableLiveData<List<Users>>()
    val followingResponseItem: LiveData<List<Users>> = _followingResponseItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _statusCode = MutableLiveData<Event<String>>()
    val statusCode: LiveData<Event<String>> = _statusCode

    fun findFollowers(login: String) {
        _isLoading.value = true
        service.getFollowers(login).enqueue(object : Callback<List<UsersResponse>> {
            override fun onResponse(
                call: Call<List<UsersResponse>>,
                response: Response<List<UsersResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followersResponse.value = response.body()
                    followersArrayListItem = ArrayList()

                    for (i in 0 until followersResponse.value?.size!!) {
                        findUser(followersResponse.value?.get(i)?.login.toString(), STAT_FOLLOWERS)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _statusCode.value = Constant.responseStatus(response)
                }
            }

            override fun onFailure(call: Call<List<UsersResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
                _statusCode.value = Constant.throwableStatus(t)
            }
        })
    }

    fun findFollowing(login: String) {
        _isLoading.value = true
        service.getFollowing(login).enqueue(object : Callback<List<UsersResponse>> {
            override fun onResponse(
                call: Call<List<UsersResponse>>,
                response: Response<List<UsersResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followingResponse.value = response.body()
                    followingArrayListItem = ArrayList()

                    for (i in 0 until followingResponse.value?.size!!) {
                        findUser(followingResponse.value?.get(i)?.login.toString(), STAT_FOLLOWING)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _statusCode.value = Constant.responseStatus(response)
                }
            }

            override fun onFailure(call: Call<List<UsersResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
                _statusCode.value = Constant.throwableStatus(t)
            }
        })
    }

    private fun findUser(login: String, statement: String) {
        _isLoading.value = true
        service.getUser(login).enqueue(object : Callback<Users> {
            override fun onResponse(
                call: Call<Users>,
                responseItem: Response<Users>
            ) {
                _isLoading.value = false
                if (responseItem.isSuccessful) {
                    if (statement == STAT_FOLLOWERS) {
                        responseItem.body()?.let { followersArrayListItem?.add(it) }
                        _followersResponseItem.value = followersArrayListItem!!
                    } else {
                        responseItem.body()?.let { followingArrayListItem?.add(it) }
                        _followingResponseItem.value = followingArrayListItem!!
                    }
                } else {
                    Log.e(TAG, "onFailure: ${responseItem.message()}")
                    _statusCode.value = Constant.responseStatus(responseItem)
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
                _statusCode.value = Constant.throwableStatus(t)
            }
        })
    }

//    fun getUsersFavorite(): LiveData<List<UsersEntity>> {
//        return usersDao.getUsersFavorite()
//    }
//
//    suspend fun setUserFavorite(usersEntity: UsersEntity) {
//        usersDao.insertToFavorite(usersEntity)
//    }
//
//    fun deleteUserFavorite(login: String) {
//        usersDao.deleteFromFavorite(login)
//    }

    companion object {
        private const val TAG = "FollowRepository"
        private const val STAT_FOLLOWING = "Statement_Following"
        private const val STAT_FOLLOWERS = "Statement_Followers"

        @Volatile
        private var instance: FollowRepository? = null

        fun getInstance(
            apiService: ApiService,
            usersDao: UsersDao
        ): FollowRepository = instance ?: synchronized(this) {
            instance ?: FollowRepository(apiService, usersDao)
        }.also { instance = it }
    }
}