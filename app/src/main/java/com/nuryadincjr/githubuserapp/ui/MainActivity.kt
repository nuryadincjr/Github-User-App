package com.nuryadincjr.githubuserapp.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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
import com.nuryadincjr.githubuserapp.databinding.ActivityMainBinding
import com.nuryadincjr.githubuserapp.data.remote.response.Users
import com.nuryadincjr.githubuserapp.util.Constant.DATA_USER
import com.nuryadincjr.githubuserapp.util.SettingPreferences
import com.nuryadincjr.githubuserapp.util.SettingsViewModelFactory
import com.nuryadincjr.githubuserapp.util.ViewModelFactory
import com.nuryadincjr.githubuserapp.viewModel.MainViewModel
import com.nuryadincjr.githubuserapp.viewModel.SettingsViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this@MainActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        supportActionBar?.title = resources.getString(R.string.github_user)

        val pref = SettingPreferences.getInstance(dataStore)
        val viewModel = ViewModelProvider(
            this@MainActivity,
            SettingsViewModelFactory(pref)
        )[SettingsViewModel::class.java]

        viewModel.getThemeSettings().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.apply {
            getUsers().observe(this@MainActivity) {
                showRecyclerList(it)
            }

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

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }
                })
            }

            isLoading().observe(this@MainActivity) {
                showLoading(it)
            }

            statusCode().observe(this@MainActivity) {
                it.getContentIfNotHandled()?.let { respond ->
                    Toast.makeText(this@MainActivity, respond, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.setting_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.setting_menu) {
            startActivity(Intent(this, SettingsActivity::class.java))
        } else if (item.itemId == R.id.favorite_menu) {
            startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showRecyclerList(listUsers: List<Users>) {
        val listUsersAdapter = ListUsersAdapter(listUsers)

        binding.rvUsers.apply {
            setHasFixedSize(true)
            layoutManager =
                if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    GridLayoutManager(this@MainActivity, 2)
                } else LinearLayoutManager(this@MainActivity)
            adapter = listUsersAdapter
        }

        listUsersAdapter.setOnItemClickCallback(object : ListUsersAdapter.OnItemClickCallback {
            override fun onItemClicked(view: View, position: Int) {
                if (view.id == R.id.iv_favorite) {
                    startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
                } else {
                    onStartActivity(listUsers[position])
                }
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun onStartActivity(usersItem: Users) {
        val detailIntent = Intent(this, DetailUserActivity::class.java)
        detailIntent.putExtra(DATA_USER, usersItem)
        startActivity(detailIntent)
    }
}