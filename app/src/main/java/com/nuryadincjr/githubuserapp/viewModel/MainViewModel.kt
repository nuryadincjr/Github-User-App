package com.nuryadincjr.githubuserapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nuryadincjr.githubuserapp.config.ApiConfig
import com.nuryadincjr.githubuserapp.pojo.Event
import com.nuryadincjr.githubuserapp.pojo.Users
import com.nuryadincjr.githubuserapp.pojo.UsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private var usersArrayListItem: ArrayList<Users> = ArrayList()

    private val _usersResponse = MutableLiveData<List<UsersResponse>>()
    val usersResponse: LiveData<List<UsersResponse>> = _usersResponse

    private val _userResponseItem = MutableLiveData<List<Users>>()
    val users: LiveData<List<Users>> = _userResponseItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _statusCode = MutableLiveData<Event<String>>()
    val statusCode: LiveData<Event<String>> = _statusCode

    init {
        findUsers()
    }

    private fun findUsers() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers()
        client.enqueue(object : Callback<List<UsersResponse>> {
            override fun onResponse(
                call: Call<List<UsersResponse>>,
                response: Response<List<UsersResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _usersResponse.value = response.body()

                    for (i in 0 until usersResponse.value?.size!!) {
                        findUser(usersResponse.value?.get(i)?.login.toString())
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _statusCode.value = responseCode(response)
                }
            }

            override fun onFailure(call: Call<List<UsersResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
                _statusCode.value = throwableCode(t)
            }
        })
    }

    private fun findUser(login: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(login)

        client.enqueue(object : Callback<Users> {
            override fun onResponse(
                call: Call<Users>,
                responseItem: Response<Users>
            ) {
                _isLoading.value = false
                if (responseItem.isSuccessful) {
                    responseItem.body()?.let { usersArrayListItem.add(it) }
                   _userResponseItem.value = usersArrayListItem
                } else {
                    Log.e(TAG, "onFailure: ${responseItem.message()}")
                    _statusCode.value = responseCode(responseItem)
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
                _statusCode.value = throwableCode(t)
            }
        })
    }

    companion object {
        private const val TAG = "MainViewModel"

        fun <T> responseCode(response: Response<T>) = when (response.hashCode()) {
            401 -> Event("$response : Bad Request")
            403 -> Event("$response : Forbidden")
            404 -> Event("$response : Not Found")
            else -> Event("$response : ${response.message()}")
        }

        fun throwableCode(t: Throwable) = when (t.hashCode()) {
            401 -> Event("${t.hashCode()} : Bad Request")
            403 -> Event("${t.hashCode()} : Forbidden")
            404 -> Event("${t.hashCode()} : Not Found")
            else -> Event("${t.hashCode()} : ${t.message}")
        }
    }
}