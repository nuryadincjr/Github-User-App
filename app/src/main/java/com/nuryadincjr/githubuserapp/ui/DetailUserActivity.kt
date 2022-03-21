package com.nuryadincjr.githubuserapp.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.nuryadincjr.githubuserapp.R
import com.nuryadincjr.githubuserapp.adapters.SectionsPagerAdapter
import com.nuryadincjr.githubuserapp.databinding.ActivityDetailUserBinding
import com.nuryadincjr.githubuserapp.data.remote.response.Users
import com.nuryadincjr.githubuserapp.util.Constant.DATA_USER
import com.nuryadincjr.githubuserapp.util.Constant.TAB_TITLES
import com.nuryadincjr.githubuserapp.viewModel.UserViewModel

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailUserBinding
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        supportActionBar?.apply {
            title = resources.getString(R.string.detail_user)
            this.setDisplayHomeAsUpEnabled(true)
            elevation = 0f
        }

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<Users>(DATA_USER)

        binding.floatingActionButton.setOnClickListener(this)
        if (user != null) {
            subscribe(user)
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
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun subscribe(user: Users) {
        userViewModel.apply {
            user?.let { setUser(it) }
            getUser().observe(this@DetailUserActivity) {
                if (it != null) {
                    sectionsPager(it)
                }
                setUserData(it)
            }
        }
    }

    private fun sectionsPager(user: Users) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.login = user.login.toString()

        binding.apply {
            viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
                if (position == 0) {
                    tab.orCreateBadge.number = user.followers?.toInt() ?: 0
                } else tab.orCreateBadge.number = user.following?.toInt() ?: 0
            }.attach()
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

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.floatingActionButton) {
            onStartActivity()
        }
    }

    private fun onStartActivity() {
        val detailIntent = Intent(this, FavoriteActivity::class.java)
        startActivity(detailIntent)
    }
}