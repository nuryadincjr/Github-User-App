package com.nuryadincjr.githubuserapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nuryadincjr.githubuserapp.data.local.entity.UsersEntity
import com.nuryadincjr.githubuserapp.data.repository.FollowRepository
import com.nuryadincjr.githubuserapp.data.remote.response.Users
import com.nuryadincjr.githubuserapp.util.Event
import kotlinx.coroutines.launch

class FollowViewModel(private val followRepository: FollowRepository, login: String) : ViewModel() {

    init {
        followRepository.apply {
            findFollowers(login)
            findFollowing(login)
        }
    }

    fun getFollowers(): LiveData<List<Users>> {
        return followRepository.followersResponseItem
    }

    fun getFollowing(): LiveData<List<Users>> {
        return followRepository.followingResponseItem
    }

    fun isLoading(): LiveData<Boolean> {
        return followRepository.isLoading
    }

    fun statusCode(): LiveData<Event<String>> {
        return followRepository.statusCode
    }

    fun getUsersFavorite() = followRepository.getUsersFavorite()

    fun saveFavorite(usersEntity: UsersEntity) {
        viewModelScope.launch {
            followRepository.setUserFavorite(usersEntity, true)
        }
    }

    fun deleteFavorite(usersEntity: UsersEntity) {
        viewModelScope.launch {
            followRepository.setUserFavorite(usersEntity, false)
        }
    }
}