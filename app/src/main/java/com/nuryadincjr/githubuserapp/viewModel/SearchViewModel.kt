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

class SearchViewModel : ViewModel() {

    private var usersArrayListItem = ArrayList<Users>()

    private val _itemsItem = MutableLiveData<List<ItemsItem?>?>()
    val itemsItem: LiveData<List<ItemsItem?>?> = _itemsItem

    private val _userResponseItem = MutableLiveData<List<Users>>()
    val userResponseItem: LiveData<List<Users>> = _userResponseItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _statusCode = MutableLiveData<Event<String>>()
    val statusCode: LiveData<Event<String>> = _statusCode

    fun searchUsers(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUsers(query, perPage = "1000")
        client.enqueue(object : Callback<SearchUsersResponse> {
            override fun onResponse(
                call: Call<SearchUsersResponse>,
                response: Response<SearchUsersResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _itemsItem.value = response.body()?.items

                    for (i in 0 until itemsItem.value?.size!!) {
                        findUser(itemsItem.value?.get(i)?.login.toString())
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _statusCode.value = MainViewModel.responseCode(response)
                }
            }

            override fun onFailure(call: Call<SearchUsersResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
                _statusCode.value = MainViewModel.throwableCode(t)
            }
        })
    }

    fun findUser(login: String) {
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
        private const val TAG = "SearchViewModel"
    }
}