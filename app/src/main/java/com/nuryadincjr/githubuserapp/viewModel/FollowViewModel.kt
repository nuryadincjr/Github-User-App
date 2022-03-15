package com.nuryadincjr.githubuserapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nuryadincjr.githubuserapp.config.ApiConfig
import com.nuryadincjr.githubuserapp.util.Event
import com.nuryadincjr.githubuserapp.pojo.Users
import com.nuryadincjr.githubuserapp.pojo.UsersResponse
import com.nuryadincjr.githubuserapp.util.Constant.responseStatus
import com.nuryadincjr.githubuserapp.util.Constant.throwableStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {

    private val service = ApiConfig.getApiService()
    private var usersArrayListItem: ArrayList<Users>? = null

    private val _followingResponse = MutableLiveData<List<UsersResponse>>()
    val followingResponse: LiveData<List<UsersResponse>> = _followingResponse

    private val _followersResponse = MutableLiveData<List<UsersResponse>>()
    val followersResponse: LiveData<List<UsersResponse>> = _followersResponse

    private val _userResponseItem = MutableLiveData<List<Users>>()
    val userResponseItem: LiveData<List<Users>> = _userResponseItem

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
                    usersArrayListItem = ArrayList()

                    for (i in 0 until followersResponse.value?.size!!) {
                        findUser(followersResponse.value?.get(i)?.login.toString())
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _statusCode.value = responseStatus(response)
                }
            }

            override fun onFailure(call: Call<List<UsersResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
                _statusCode.value = throwableStatus(t)
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
                    usersArrayListItem = ArrayList()

                    for (i in 0 until followingResponse.value?.size!!) {
                        findUser(followingResponse.value?.get(i)?.login.toString())
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _statusCode.value = responseStatus(response)
                }
            }

            override fun onFailure(call: Call<List<UsersResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
                _statusCode.value = throwableStatus(t)
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
                    _statusCode.value = responseStatus(responseItem)
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
                _statusCode.value = throwableStatus(t)
            }
        })
    }

    companion object {
        private const val TAG = "FollowViewModel"
    }
}