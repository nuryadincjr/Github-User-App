package com.nuryadincjr.githubuserapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nuryadincjr.githubuserapp.data.repository.FollowRepository
import com.nuryadincjr.githubuserapp.data.remote.response.Users
import com.nuryadincjr.githubuserapp.util.Event

class FollowViewModel(private val followRepository: FollowRepository) : ViewModel() {

    fun getFollowers(login: String) {
        return followRepository.findFollowers(login)
    }

    fun getFollowing(login: String) {
        return followRepository.findFollowing(login)
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

}