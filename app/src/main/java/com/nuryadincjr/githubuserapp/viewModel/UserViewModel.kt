package com.nuryadincjr.githubuserapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nuryadincjr.githubuserapp.pojo.Users

class UserViewModel : ViewModel() {

    private val _user = MutableLiveData<Users>()

    fun setUser(user: Users) {
        _user.value = user
    }

    fun getUser(): LiveData<Users?> {
        return _user
    }
}