package com.nuryadincjr.githubuserapp.util.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nuryadincjr.githubuserapp.data.repository.FollowRepository
import com.nuryadincjr.githubuserapp.di.Injection
import com.nuryadincjr.githubuserapp.viewModel.FollowViewModel

class FollowViewModelFactory(
    private val followRepository: FollowRepository?,
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FollowViewModel::class.java)) {
            return FollowViewModel(followRepository!!) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass.name")
    }

    companion object {
        @Volatile
        private var instance: FollowViewModelFactory? = null
        fun getInstance(context: Context): FollowViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: FollowViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}