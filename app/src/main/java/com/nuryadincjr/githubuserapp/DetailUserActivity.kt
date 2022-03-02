package com.nuryadincjr.githubuserapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.nuryadincjr.githubuserapp.databinding.ActivityDetailUserBinding
import com.nuryadincjr.githubuserapp.pojo.Users

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    companion object {
        const val DATA_USER = "data user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        supportActionBar?.title = "Detail User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<Users>(DATA_USER)

        Glide.with(this)
            .load(user?.avatar)
            .circleCrop()
            .into(binding.ivAvatar)

        val followInfo = "${user?.followers} followers · ${user?.following} following"
        val repositories = "${user?.repository} Repositories"

        binding.tvUsername.text = user?.username
        binding.tvName.text = user?.name
        binding.tvFollowers.text = followInfo
        binding.tvCompany.text = user?.company
        binding.tvLocation.text = user?.location
        binding.tvRepository.text = repositories

        binding.btnShare.setOnClickListener {
            val gitHubUserUrl = "https://github.com/${user?.username}"
            val shareUserIntent = Intent(Intent.ACTION_SEND)
                .putExtra(Intent.EXTRA_TEXT, gitHubUserUrl)
                .setType("text/plain")
            startActivity(shareUserIntent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}