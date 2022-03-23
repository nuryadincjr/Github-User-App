package com.nuryadincjr.githubuserapp.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nuryadincjr.githubuserapp.R
import com.nuryadincjr.githubuserapp.data.local.entity.UsersEntity
import com.nuryadincjr.githubuserapp.data.remote.response.Users
import com.nuryadincjr.githubuserapp.databinding.ItemRowUserBinding
import com.nuryadincjr.githubuserapp.ui.DetailUserActivity
import com.nuryadincjr.githubuserapp.util.Constant

class ListFavoriteAdapter(private val onBookmarkClick: (UsersEntity) -> Unit) :
    ListAdapter<UsersEntity, ListFavoriteAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding, this)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val news = getItem(position)
        holder.setDataToView(news)
    }

    class ListViewHolder(
        private var binding: ItemRowUserBinding,
        private var listUsersAdapter: ListFavoriteAdapter
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setDataToView(user: UsersEntity) {
            binding.apply {
                tvUsername.text = user.login
                tvName.text = user.name

                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .circleCrop()
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .error(R.drawable.ic_baseline_error_24)
                    .into(ivAvatar)
            }

            val users = Users()
            users.login = user.login
            users.name = user.name
            users.avatarUrl = user.avatarUrl
            users.followers = user.followers?.toInt()
            users.following = user.following?.toInt()
            users.company = user.company
            users.location = user.location
            users.publicRepos = user.publicRepos?.toInt()

            itemView.apply {
                setOnClickListener {
                    context.startActivity(
                        Intent(
                            context,
                            DetailUserActivity::class.java
                        ).putExtra(Constant.DATA_USER, users)
                    )
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<UsersEntity> =
            object : DiffUtil.ItemCallback<UsersEntity>() {
                override fun areItemsTheSame(oldUser: UsersEntity, user: UsersEntity): Boolean {
                    return oldUser.login == user.login
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldUser: UsersEntity,
                    user: UsersEntity
                ): Boolean {
                    return oldUser == user
                }
            }
    }
}