package com.nuryadincjr.githubuserapp.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nuryadincjr.githubuserapp.data.repository.FollowRepository
import com.nuryadincjr.githubuserapp.di.Injection
import com.nuryadincjr.githubuserapp.viewModel.FollowViewModel

class FollowViewModelFactory(
    private val followRepository: FollowRepository?,
) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FollowViewModel(followRepository!!) as T
    }

    companion object {
        @Volatile
        private var instance: FollowViewModelFactory? = null
        fun getInstance(context: Context, login: String?): FollowViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: FollowViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}