package com.nuryadincjr.githubuserapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nuryadincjr.githubuserapp.data.local.entity.UsersEntity
import com.nuryadincjr.githubuserapp.data.repository.UsersRepository
import com.nuryadincjr.githubuserapp.data.remote.response.Users
import com.nuryadincjr.githubuserapp.util.Event
import kotlinx.coroutines.launch

class MainViewModel(private val usersRepository: UsersRepository) : ViewModel() {

    init {
        usersRepository.findUsers()
    }

    fun getUsers(): LiveData<List<Users>> {
        return usersRepository.userResponseItem
    }

    fun searchUsers(string: String) = usersRepository.searchUsers(string)

    fun isLoading(): LiveData<Boolean> {
        return usersRepository.isLoading
    }

    fun statusCode(): LiveData<Event<String>> {
        return usersRepository.statusCode
    }

    fun saveFavorite(usersEntity: UsersEntity) {
        viewModelScope.launch {
            usersRepository.setUserFavorite(usersEntity, true)
        }
    }

    fun deleteFavorite(usersEntity: UsersEntity) {
        viewModelScope.launch {
            usersRepository.setUserFavorite(usersEntity, false)
        }
    }

    private val _user = MutableLiveData<Users>()

    fun setUser(user: Users) {
        _user.value = user
    }

    fun getUser(): LiveData<Users?> {
        return _user
    }

    fun insertFavoriteUser(user: UsersEntity) = viewModelScope.launch {
        usersRepository.insertFavoriteUser(user)
    }

    fun deleteFavoriteUser(user: UsersEntity) = viewModelScope.launch {
        usersRepository.deleteFavoriteUser(user)
    }
}