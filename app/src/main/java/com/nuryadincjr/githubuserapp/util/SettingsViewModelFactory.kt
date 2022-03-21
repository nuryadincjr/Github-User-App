package com.nuryadincjr.githubuserapp.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nuryadincjr.githubuserapp.viewModel.SettingsViewModel

class SettingsViewModelFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}