package com.nuryadincjr.githubuserapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nuryadincjr.githubuserapp.databinding.ItemRowUserBinding
import com.nuryadincjr.githubuserapp.pojo.Users

class ListFollowAdapter(private val listFollowItems: List<Users>) :
    RecyclerView.Adapter<ListFollowAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.binding.apply {
            tvUsername.text = listFollowItems[position].login
            tvName.text = listFollowItems[position].name

            Glide.with(holder.itemView.context)
                .load(listFollowItems[position].avatarUrl)
                .circleCrop()
                .into(ivAvatar)
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listFollowItems[holder.adapterPosition])
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun getItemCount(): Int = listFollowItems.size

    class ListViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: Users)
    }
}