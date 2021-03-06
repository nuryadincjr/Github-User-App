package com.nuryadincjr.githubuserapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nuryadincjr.githubuserapp.util.SettingPreferences
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingPreferences: SettingPreferences) : ViewModel() {

    fun getThemeSettings(): LiveData<Boolean> = settingPreferences.getThemeSetting().asLiveData()

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            settingPreferences.saveThemeSetting(isDarkModeActive)
        }
    }
}