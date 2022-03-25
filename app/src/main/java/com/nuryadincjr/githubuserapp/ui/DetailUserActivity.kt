package com.nuryadincjr.githubuserapp.ui

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.nuryadincjr.githubuserapp.R
import com.nuryadincjr.githubuserapp.adapters.SectionsPagerAdapter
import com.nuryadincjr.githubuserapp.data.remote.response.Users
import com.nuryadincjr.githubuserapp.databinding.ActivityDetailUserBinding
import com.nuryadincjr.githubuserapp.util.Constant.Color.BLUE_GRAY
import com.nuryadincjr.githubuserapp.util.Constant.Color.RED
import com.nuryadincjr.githubuserapp.util.Constant.DATA_USER
import com.nuryadincjr.githubuserapp.util.Constant.TAB_TITLES
import com.nuryadincjr.githubuserapp.util.factory.ViewModelFactory
import com.nuryadincjr.githubuserapp.viewModel.MainViewModel

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        supportActionBar?.apply {
            title = resources.getString(R.string.detail_user)
            setDisplayHomeAsUpEnabled(true)
            elevation = 0f
        }

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<Users>(DATA_USER) as Users
        val login = user.login.toString()

        val colorRed: ColorStateList
        val colorBlueGray: ColorStateList

        if (VERSION.SDK_INT >= VERSION_CODES.M) {
            colorRed = ColorStateList.valueOf(resources.getColor(BLUE_GRAY.value, theme))
            colorBlueGray = ColorStateList.valueOf(resources.getColor(RED.value, theme))
        } else {
            colorRed = ColorStateList.valueOf(RED.value)
            colorBlueGray = ColorStateList.valueOf(BLUE_GRAY.value)
        }

        onSubscribeViewModel(user, login, colorRed, colorBlueGray)
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

    private fun sectionsPager(user: Users) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.login = user.login.toString()

        binding.apply {
            viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
                if (position == 0) {
                    tab.orCreateBadge.number = user.followers ?: 0
                } else tab.orCreateBadge.number = user.following ?: 0
            }.attach()
        }
    }

    private fun onSubscribeViewModel(
        user: Users,
        login: String,
        colorRed: ColorStateList,
        colorBlueGray: ColorStateList
    ) {
        mainViewModel.apply {
            setUser(user)
            getUser().observe(this@DetailUserActivity) {
                if (it != null) sectionsPager(it)
                setUserData(it)
            }

            isUserFavorite(login).observe(this@DetailUserActivity) {
                binding.floatingActionButton.backgroundTintList =
                    if (it) colorRed else colorBlueGray
            }

            binding.floatingActionButton.apply {
                setOnClickListener {
                    isUserFavorite(login).observe(this@DetailUserActivity) {
                        backgroundTintList = if (it) {
                            deleteFavorite(login)
                            colorBlueGray
                        } else {
                            insertFavorite(user)
                            colorRed
                        }
                    }
                }
            }
        }
    }

    private fun setUserData(user: Users?) {
        Glide.with(this)
            .load(user?.avatarUrl)
            .circleCrop()
            .placeholder(R.drawable.ic_baseline_person_24)
            .error(R.drawable.ic_baseline_error_24)
            .into(binding.ivAvatar)

        val followInfo = String.format(
            getString(R.string.follow_info),
            "${user?.followers}",
            "${user?.following}"
        )

        val repositories = String.format(
            getString(R.string.repositories),
            "${user?.publicRepos}"
        )

        binding.apply {
            tvUsername.text = user?.login
            tvName.text = user?.name
            tvFollowers.text = followInfo
            tvCompany.text = user?.company
            tvLocation.text = user?.location
            tvRepository.text = repositories

            btnShare.setOnClickListener {
                val gitHubUserUrl = String.format(getString(R.string.github), "${user?.login}")
                val shareUserIntent = Intent(Intent.ACTION_SEND)
                    .putExtra(Intent.EXTRA_TEXT, gitHubUserUrl)
                    .setType("text/plain")
                startActivity(shareUserIntent)
            }
        }
    }
}