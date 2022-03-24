package com.nuryadincjr.githubuserapp.ui

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.nuryadincjr.githubuserapp.R
import com.nuryadincjr.githubuserapp.util.SettingPreferences
import com.nuryadincjr.githubuserapp.util.factory.SettingsViewModelFactory
import com.nuryadincjr.githubuserapp.viewModel.SettingsViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportActionBar?.apply {
            title = resources.getString(R.string.setting)
            setDisplayHomeAsUpEnabled(true)
            elevation = 0f
        }

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(dataStore)
        val viewModel = ViewModelProvider(
            this,
            SettingsViewModelFactory(pref)
        )[SettingsViewModel::class.java]

        viewModel.getThemeSettings().observe(this) {
            val themeMode =
                if (it) {
                    switchTheme.isChecked = true
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    switchTheme.isChecked = false
                    AppCompatDelegate.MODE_NIGHT_NO
                }
            AppCompatDelegate.setDefaultNightMode(themeMode)
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            viewModel.saveThemeSetting(isChecked)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}