package com.nuryadincjr.githubuserapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nuryadincjr.githubuserapp.R
import com.nuryadincjr.githubuserapp.data.remote.response.Users
import com.nuryadincjr.githubuserapp.databinding.ItemRowUserBinding
import com.nuryadincjr.githubuserapp.ui.DetailUserActivity
import com.nuryadincjr.githubuserapp.util.Constant.DATA_USER

class ListUsersAdapter(private val listUsers: List<Users>) :
    RecyclerView.Adapter<ListUsersAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.setDataToView(listUsers[position])
    }

    override fun getItemCount(): Int = listUsers.size

    class ListViewHolder(
        private var binding: ItemRowUserBinding
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

            itemView.apply {
                setOnClickListener {
                    context.startActivity(
                        Intent(
                            context,
                            DetailUserActivity::class.java
                        ).putExtra(DATA_USER, user)
                    )
                }
            }
        }
    }
}