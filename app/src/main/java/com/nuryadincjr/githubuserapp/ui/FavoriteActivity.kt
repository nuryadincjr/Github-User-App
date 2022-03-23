package com.nuryadincjr.githubuserapp.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.nuryadincjr.githubuserapp.R
import com.nuryadincjr.githubuserapp.adapters.ListFavoriteAdapter
import com.nuryadincjr.githubuserapp.databinding.ActivityFavoriteBinding
import com.nuryadincjr.githubuserapp.util.ViewModelFactory
import com.nuryadincjr.githubuserapp.viewModel.MainViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this@FavoriteActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        supportActionBar?.apply {
            title = resources.getString(R.string.favorite_user)
            this.setDisplayHomeAsUpEnabled(true)
            elevation = 0f
        }

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val favoriteAdapter = ListFavoriteAdapter {
//            if (news.isFavourite) {
//                mainViewModel.deleteNews(news)
//            } else {
//                mainViewModel.saveNews(news)
//            }
        }

        mainViewModel.getUsersFavorite().observe(this) {
            binding.progressBar.visibility = View.GONE
            favoriteAdapter.submitList(it)
        }

        binding.rvFavorite.apply {
            layoutManager =
                if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    GridLayoutManager(this@FavoriteActivity, 2)
                } else LinearLayoutManager(this@FavoriteActivity)
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.setting_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.favorite_menu)?.isVisible = false
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        } else if (item.itemId == R.id.setting_menu) {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}