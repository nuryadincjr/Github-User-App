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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        supportActionBar?.title = resources.getString(R.string.detail_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<Users>(DATA_USER)

        Glide.with(this)
            .load(user?.avatar)
            .circleCrop()
            .into(binding.ivAvatar)

        val followInfo = String.format(
            getString(R.string.follow_info),
            "${user?.followers}",
            "${user?.following}"
        )
        val repositories = String.format(getString(R.string.repositories), "${user?.repository}")

        binding.apply {
            tvUsername.text = user?.username
            tvName.text = user?.name
            tvFollowers.text = followInfo
            tvCompany.text = user?.company
            tvLocation.text = user?.location
            tvRepository.text = repositories

            btnShare.setOnClickListener {
                val gitHubUserUrl = String.format(getString(R.string.github), "${user?.username}")
                val shareUserIntent = Intent(Intent.ACTION_SEND)
                    .putExtra(Intent.EXTRA_TEXT, gitHubUserUrl)
                    .setType("text/plain")
                startActivity(shareUserIntent)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val DATA_USER = "data_user"
    }
}