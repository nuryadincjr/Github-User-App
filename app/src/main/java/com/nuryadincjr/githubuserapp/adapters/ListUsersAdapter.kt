package com.nuryadincjr.githubuserapp.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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

class ListUsersAdapter(private val onBookmarkClick: (Users) -> Unit) :
    ListAdapter<Users, ListUsersAdapter.ListViewHolder>(DIFF_CALLBACK) {

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
        private var listUsersAdapter: ListUsersAdapter
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setDataToView(user: Users) {
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

//            val ivBookmark = binding.ivFavorite
//            if (user.isFavourite) {
//                ivBookmark.setImageDrawable(
//                    ContextCompat.getDrawable(
//                        ivBookmark.context,
//                        R.drawable.ic_baseline_favorited
//                    )
//                )
//            } else {
//                ivBookmark.setImageDrawable(
//                    ContextCompat.getDrawable(
//                        ivBookmark.context,
//                        R.drawable.ic_baseline_favorite
//                    )
//                )
//            }
//
//            ivBookmark.setOnClickListener {
//                listUsersAdapter.onBookmarkClick(user)
//                if (user.isFavourite) {
//                    ivBookmark.setImageDrawable(
//                        ContextCompat.getDrawable(
//                            ivBookmark.context,
//                            R.drawable.ic_baseline_favorited
//                        )
//                    )
//                } else {
//                    ivBookmark.setImageDrawable(
//                        ContextCompat.getDrawable(
//                            ivBookmark.context,
//                            R.drawable.ic_baseline_favorite
//                        )
//                    )
//                }
//            }

            itemView.apply {
                setOnClickListener {
                    context.startActivity(
                        Intent(
                            context,
                            DetailUserActivity::class.java
                        ).putExtra(Constant.DATA_USER, user)
                    )
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Users> =
            object : DiffUtil.ItemCallback<Users>() {
                override fun areItemsTheSame(oldUser: Users, newUser: Users): Boolean {
                    return oldUser.login == newUser.login
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldUser: Users,
                    newUser: Users
                ): Boolean {
                    return oldUser == newUser
                }
            }
    }
}