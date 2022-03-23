package com.nuryadincjr.githubuserapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nuryadincjr.githubuserapp.R
import com.nuryadincjr.githubuserapp.databinding.ItemRowUserBinding
import com.nuryadincjr.githubuserapp.data.remote.response.Users

class ListFollowAdapter(private val listFollowItems: List<Users>) :
    RecyclerView.Adapter<ListFollowAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding, this)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.setDataToView(listFollowItems[position])
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun getItemCount(): Int = listFollowItems.size

    class ListViewHolder(
        private var binding: ItemRowUserBinding,
        private var listUsersAdapter: ListFollowAdapter
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
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

            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View) {
            listUsersAdapter.onItemClickCallback.onItemClicked(p0, adapterPosition)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(view: View, position: Int)
    }
}