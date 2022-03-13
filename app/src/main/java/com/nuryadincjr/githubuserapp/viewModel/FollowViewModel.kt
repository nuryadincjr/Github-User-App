package com.nuryadincjr.githubuserapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nuryadincjr.githubuserapp.config.ApiConfig
import com.nuryadincjr.githubuserapp.pojo.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {

    private var followersArrayListItem = ArrayList<Users>()
    private var followingArrayListItem = ArrayList<Users>()

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
        val client = ApiConfig.getApiService().getFollowers(login)
        client.enqueue(object : Callback<List<UsersResponse>> {
            override fun onResponse(
                call: Call<List<UsersResponse>>,
                response: Response<List<UsersResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followersResponse.value = response.body()

                    for (i in 0 until followersResponse.value?.size!!) {
                        findUserFollowers(followersResponse.value?.get(i)?.login.toString())
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _statusCode.value = MainViewModel.responseCode(response)
                }
            }

            override fun onFailure(call: Call<List<UsersResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
                _statusCode.value = MainViewModel.throwableCode(t)
            }
        })
    }

    fun findFollowing(login: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(login)
        client.enqueue(object : Callback<List<UsersResponse>> {
            override fun onResponse(
                call: Call<List<UsersResponse>>,
                response: Response<List<UsersResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followingResponse.value = response.body()

                    for (i in 0 until followingResponse.value?.size!!) {
                        findUserFollowing(followingResponse.value?.get(i)?.login.toString())
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _statusCode.value = MainViewModel.responseCode(response)
                }
            }

            override fun onFailure(call: Call<List<UsersResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
                _statusCode.value = MainViewModel.throwableCode(t)
            }
        })
    }

    private fun findUserFollowing(login: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(login)

        client.enqueue(object : Callback<Users> {
            override fun onResponse(
                call: Call<Users>,
                responseItem: Response<Users>
            ) {
                _isLoading.value = false
                if (responseItem.isSuccessful) {
                    responseItem.body()?.let { followingArrayListItem.add(it) }
                    _followingResponseItem.value = followingArrayListItem
                } else {
                    Log.e(TAG, "onFailure: ${responseItem.message()}")
                    _statusCode.value = MainViewModel.responseCode(responseItem)
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
                _statusCode.value = MainViewModel.throwableCode(t)
            }
        })
    }

    private fun findUserFollowers(login: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(login)

        client.enqueue(object : Callback<Users> {
            override fun onResponse(
                call: Call<Users>,
                responseItem: Response<Users>
            ) {
                _isLoading.value = false
                if (responseItem.isSuccessful) {
                    responseItem.body()?.let { followersArrayListItem.add(it) }
                    _followersResponseItem.value = followersArrayListItem
                } else {
                    Log.e(TAG, "onFailure: ${responseItem.message()}")
                    _statusCode.value = MainViewModel.responseCode(responseItem)
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
                _statusCode.value = MainViewModel.throwableCode(t)
            }
        })
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}