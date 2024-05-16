package com.rifftyo.githubuserapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rifftyo.githubuserapp.data.database.FavoriteUser
import com.rifftyo.githubuserapp.databinding.ListUsersBinding
import com.rifftyo.githubuserapp.ui.UserDetailActivity

class UsersFavoriteAdapter: ListAdapter<FavoriteUser, UsersFavoriteAdapter.UsersFavoriteViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UsersFavoriteAdapter.UsersFavoriteViewHolder {
        val binding = ListUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersFavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: UsersFavoriteAdapter.UsersFavoriteViewHolder,
        position: Int
    ) {
        val favoriteUser = getItem(position)
        holder.bind(favoriteUser)
    }

    class UsersFavoriteViewHolder(private val binding: ListUsersBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteUser: FavoriteUser) {
            binding.apply {
                username.text = favoriteUser.username
                // Tampilkan Gambar
                Glide.with(root.context)
                    .load(favoriteUser.avatarUrl)
                    .into(profileImage)
                itemView.setOnClickListener {
                    val intentDetail = Intent(itemView.context, UserDetailActivity::class.java)
                    intentDetail.putExtra(UserDetailActivity.EXTRA_USERNAME, favoriteUser.username)
                    itemView.context.startActivity(intentDetail)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteUser>() {
            override fun areItemsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem.username == newItem.username
            }

            override fun areContentsTheSame(
                oldItem: FavoriteUser,
                newItem: FavoriteUser
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}