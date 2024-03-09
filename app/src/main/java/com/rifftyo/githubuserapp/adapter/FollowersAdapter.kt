package com.rifftyo.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rifftyo.githubuserapp.R
import com.rifftyo.githubuserapp.data.response.FollowersUserResponseItem
import com.rifftyo.githubuserapp.databinding.ListUsersBinding


class FollowersAdapter: ListAdapter<FollowersUserResponseItem, FollowersAdapter.MyViewHolderFollowers>(
    DIFF_CALLBACK_FOLLOWERS
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderFollowers {
        val binding = ListUsersBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return MyViewHolderFollowers(binding)
    }

   override fun onBindViewHolder(holder: MyViewHolderFollowers, position: Int) {
        val userFollow = getItem(position)
        holder.bind(userFollow)
    }

    class MyViewHolderFollowers(private val binding: ListUsersBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(userFollowers: FollowersUserResponseItem) {
            binding.username.text = userFollowers.login
            val idFormat = itemView.context.getString(R.string.id_profile_users)
            binding.idProfile.text = String.format(idFormat, userFollowers.id)
            Glide.with(itemView.context)
                .load(userFollowers.avatarUrl)
                .skipMemoryCache(true)
                .into(binding.profileImage)
        }
    }

    companion object {
        val DIFF_CALLBACK_FOLLOWERS = object : DiffUtil.ItemCallback<FollowersUserResponseItem>() {
            override fun areItemsTheSame(oldItem: FollowersUserResponseItem, newItem: FollowersUserResponseItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: FollowersUserResponseItem, newItem: FollowersUserResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}