package com.rifftyo.githubuserapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rifftyo.githubuserapp.R
import com.rifftyo.githubuserapp.data.response.UserItem
import com.rifftyo.githubuserapp.databinding.ListUsersBinding
import com.rifftyo.githubuserapp.ui.UserDetailActivity

class UsersAdapter: ListAdapter<UserItem, UsersAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val users = getItem(position)
        holder.bind(users)
    }

    class MyViewHolder(private val binding: ListUsersBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserItem) {
            binding.username.text = user.login
            val idFormat = itemView.context.getString(R.string.id_profile_users)
            binding.idProfile.text = String.format(idFormat, user.id)
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .skipMemoryCache(true)
                .into(binding.profileImage)
            itemView.setOnClickListener {
                val intentDetail = Intent(itemView.context, UserDetailActivity::class.java)
                intentDetail.putExtra(UserDetailActivity.EXTRA_USERNAME, user.login)
                itemView.context.startActivity(intentDetail)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserItem>() {
            override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}