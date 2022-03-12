package com.nuryadincjr.githubuserapp

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.nuryadincjr.githubuserapp.adapters.ListUsersAdapter
import com.nuryadincjr.githubuserapp.databinding.ActivityMainBinding
import com.nuryadincjr.githubuserapp.pojo.UserResponse

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = resources.getString(R.string.github_user)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUsers.setHasFixedSize(true)

        mainViewModel.apply {
            userResponse.observe(this@MainActivity) {
                showRecyclerList(it)
            }

            isLoading.observe(this@MainActivity) {
                showLoading(it)
            }
        }
    }

    private fun showRecyclerList(list: List<UserResponse>) {
        binding.rvUsers.layoutManager =
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager(this, 2)
            } else LinearLayoutManager(this)

        val listUsersAdapter = ListUsersAdapter(list)
        binding.rvUsers.adapter = listUsersAdapter

        listUsersAdapter.setOnItemClickCallback(object : ListUsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserResponse) {
                onStartActivity(data)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun onStartActivity(user: UserResponse) {
        val detailIntent = Intent(this, DetailUserActivity::class.java)
        detailIntent.putExtra(DetailUserActivity.DATA_USER, user)
        startActivity(detailIntent)
    }
}