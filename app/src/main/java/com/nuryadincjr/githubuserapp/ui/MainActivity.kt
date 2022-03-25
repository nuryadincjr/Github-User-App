package com.nuryadincjr.githubuserapp.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.appcompat.widget.SearchView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.nuryadincjr.githubuserapp.R
import com.nuryadincjr.githubuserapp.adapters.ListUsersAdapter
import com.nuryadincjr.githubuserapp.data.remote.response.Users
import com.nuryadincjr.githubuserapp.databinding.ActivityMainBinding
import com.nuryadincjr.githubuserapp.util.Constant.SPAN_COUNT
import com.nuryadincjr.githubuserapp.util.SettingPreferences
import com.nuryadincjr.githubuserapp.util.factory.SettingsViewModelFactory
import com.nuryadincjr.githubuserapp.util.factory.ViewModelFactory
import com.nuryadincjr.githubuserapp.viewModel.MainViewModel
import com.nuryadincjr.githubuserapp.viewModel.SettingsViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        supportActionBar?.title = resources.getString(R.string.github_user)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onPrepareTheme()
        onSubscribeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.setting_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var intent: Intent? = null

        if (item.itemId == R.id.setting_menu) {
            intent = Intent(this, SettingsActivity::class.java)
        } else if (item.itemId == R.id.favorite_menu) {
            intent = Intent(this, FavoriteActivity::class.java)
        }

        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }

    private fun onPrepareTheme() {
        val settingPreferences = SettingPreferences.getInstance(dataStore)
        val viewModel = ViewModelProvider(
            this,
            SettingsViewModelFactory(settingPreferences)
        )[SettingsViewModel::class.java]

        viewModel.getThemeSettings().observe(this) {
            val themeMode = if (it) MODE_NIGHT_YES else MODE_NIGHT_NO
            setDefaultNightMode(themeMode)
        }
    }

    private fun onSubscribeViewModel() {
        mainViewModel.apply {
            getUsers().observe(this@MainActivity) { showRecyclerList(it) }

            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            binding.svUser.apply {
                setSearchableInfo(searchManager.getSearchableInfo(componentName))
                queryHint = resources.getString(R.string.search_hint)
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (query != null) {
                            searchUsers(query)
                            clearFocus()
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean = false
                })
            }

            isLoading().observe(this@MainActivity) { showLoading(it) }

            statusCode().observe(this@MainActivity) {
                it.getContentIfNotHandled()?.let { respond ->
                    Toast.makeText(this@MainActivity, respond, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun showRecyclerList(listUsers: List<Users>) {
        val listUsersAdapter = ListUsersAdapter(listUsers)

        binding.rvUsers.apply {
            layoutManager =
                if (resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    GridLayoutManager(context, SPAN_COUNT)
                } else LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = listUsersAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}