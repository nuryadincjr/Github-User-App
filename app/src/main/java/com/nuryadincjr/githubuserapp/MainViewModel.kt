package com.nuryadincjr.githubuserapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nuryadincjr.githubuserapp.config.ApiConfig
import com.nuryadincjr.githubuserapp.pojo.UserResponse
import com.nuryadincjr.githubuserapp.pojo.UsersResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private var userArrayList: ArrayList<UserResponse> = ArrayList()

    private val _usersResponseItemList = MutableLiveData<List<UsersResponseItem>>()
    val usersResponseItemList: LiveData<List<UsersResponseItem>> = _usersResponseItemList

    private val _userResponse = MutableLiveData<List<UserResponse>>()
    val userResponse: LiveData<List<UserResponse>> = _userResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        findUsers()
    }

    private fun findUsers() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers()

        client.enqueue(object : Callback<List<UsersResponseItem>> {
            override fun onResponse(
                call: Call<List<UsersResponseItem>>,
                response: Response<List<UsersResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _usersResponseItemList.value = response.body()

                    for (i in 0 until usersResponseItemList.value?.size!!) {
                        findUser(usersResponseItemList.value?.get(i)?.login.toString())
                    }

                } else Log.e(TAG, "onFailure: ${response.message()}")
            }

            override fun onFailure(call: Call<List<UsersResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun findUser(login: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserLogin(login)

        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let { userArrayList.add(it) }
                    _userResponse.value = userArrayList
                    Log.e(TAG, "onFailure: ${response.body()?.name}")
                } else Log.e(TAG, "onFailure: ${response.message()}")
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}