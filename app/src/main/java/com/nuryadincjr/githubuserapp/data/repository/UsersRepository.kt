package com.nuryadincjr.githubuserapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nuryadincjr.githubuserapp.data.local.entity.UsersEntity
import com.nuryadincjr.githubuserapp.data.local.room.UsersDao
import com.nuryadincjr.githubuserapp.data.remote.retrofit.ApiService
import com.nuryadincjr.githubuserapp.data.remote.response.ItemsSearchItem
import com.nuryadincjr.githubuserapp.data.remote.response.SearchUsersResponse
import com.nuryadincjr.githubuserapp.data.remote.response.Users
import com.nuryadincjr.githubuserapp.data.remote.response.UsersResponse
import com.nuryadincjr.githubuserapp.util.Constant
import com.nuryadincjr.githubuserapp.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersRepository private constructor(
    private val service: ApiService,
    private val usersDao: UsersDao
) {
    private var usersArrayListItem: ArrayList<Users>? = null

    private val _usersResponse = MutableLiveData<List<UsersResponse>>()
    val usersResponse: LiveData<List<UsersResponse>> = _usersResponse

    private val _itemsSearchItem = MutableLiveData<List<ItemsSearchItem?>?>()
    val itemsSearchItem: LiveData<List<ItemsSearchItem?>?> = _itemsSearchItem

    private val _userResponseItem = MutableLiveData<List<Users>>()
    val userResponseItem: LiveData<List<Users>> = _userResponseItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _statusCode = MutableLiveData<Event<String>>()
    val statusCode: LiveData<Event<String>> = _statusCode

    fun findUsers() {
        _isLoading.value = true
        service.getUsers().enqueue(object : Callback<List<UsersResponse>> {
            override fun onResponse(
                call: Call<List<UsersResponse>>,
                response: Response<List<UsersResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _usersResponse.value = response.body()
                    usersArrayListItem = ArrayList()

                    for (i in 0 until usersResponse.value?.size!!) {
                        findUser(usersResponse.value?.get(i)?.login.toString())
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

    fun searchUsers(query: String) {
        _isLoading.value = true
        service.searchUsers(query, sort = "created", order = "desc")
            .enqueue(object : Callback<SearchUsersResponse> {
                override fun onResponse(
                    call: Call<SearchUsersResponse>,
                    response: Response<SearchUsersResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful && response.body()?.totalCount!! > 0) {
                        _itemsSearchItem.value = response.body()?.searchItems
                        usersArrayListItem = ArrayList()

                        for (i in 0 until itemsSearchItem.value?.size!!) {
                            findUser(itemsSearchItem.value?.get(i)?.login.toString())
                        }
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                        _statusCode.value = Constant.responseStatus(response)
                    }
                }

                override fun onFailure(call: Call<SearchUsersResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message}")
                    _statusCode.value = Constant.throwableStatus(t)
                }
            })
    }

    private fun findUser(login: String) {
        _isLoading.value = true
        service.getUser(login).enqueue(object : Callback<Users> {
            override fun onResponse(
                call: Call<Users>,
                responseItem: Response<Users>
            ) {
                _isLoading.value = false
                if (responseItem.isSuccessful) {

                    responseItem.body()?.let { usersArrayListItem?.add(it) }
                    _userResponseItem.value = usersArrayListItem!!
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

    suspend fun setUserFavorite(usersEntity: UsersEntity) {
        usersDao.insertToFavorite(usersEntity)
    }

    fun deleteUserFavorite(login: String) {
        usersDao.deleteFromFavorite(login)
    }

    companion object {
        private const val TAG = "UsersRepository"

        @Volatile
        private var instance: UsersRepository? = null

        fun getInstance(
            apiService: ApiService,
            usersDao: UsersDao
        ): UsersRepository = instance ?: synchronized(this) {
            instance ?: UsersRepository(apiService, usersDao)
        }.also { instance = it }
    }
}