package com.nuryadincjr.githubuserapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.nuryadincjr.githubuserapp.adapters.ListUsersAdapter
import com.nuryadincjr.githubuserapp.databinding.ActivityMainBinding
import com.nuryadincjr.githubuserapp.pojo.Users
import com.nuryadincjr.githubuserapp.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listUsersAdapter: ListUsersAdapter
    private val mainViewModel: MainViewModel by viewModels()
    private var listUsers: ArrayList<Users> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = resources.getString(R.string.github_user)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.apply {
            userResponseItem.observe(this@MainActivity) {
                listUsers = it as ArrayList<Users>
                showRecyclerList()
            }

            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            binding.apply {
                svUser.setSearchableInfo(searchManager.getSearchableInfo(componentName))
                svUser.queryHint = resources.getString(R.string.search_hint)
                svUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (query != null) {
                            listUsers.clear()
                            searchUsers(query)
                            svUser.clearFocus()
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }
                })
            }

            isLoading.observe(this@MainActivity) {
                showLoading(it)
            }

            statusCode.observe(this@MainActivity) {
                it.getContentIfNotHandled()?.let { respond ->
                    Toast.makeText(this@MainActivity, respond, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun showRecyclerList() {
        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.layoutManager =
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager(this, 2)
            } else LinearLayoutManager(this)

        listUsersAdapter = ListUsersAdapter(listUsers)
        binding.rvUsers.adapter = listUsersAdapter
        listUsersAdapter.setOnItemClickCallback(object : ListUsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Users) {
                onStartActivity(data)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun onStartActivity(usersItem: Users) {
        val detailIntent = Intent(this, DetailUserActivity::class.java)
        detailIntent.putExtra(DetailUserActivity.DATA_USER, usersItem)
        startActivity(detailIntent)
    }
}