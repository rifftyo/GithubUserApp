package com.rifftyo.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rifftyo.githubuserapp.R
import com.rifftyo.githubuserapp.data.response.FollowingUserResponseItem
import com.rifftyo.githubuserapp.databinding.ListUsersBinding

class FollowingAdapter: ListAdapter<FollowingUserResponseItem, FollowingAdapter.MyViewHolderFollowing>(
    DIFF_CALLBACK_FOLLOWING
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolderFollowing {
        val binding = ListUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolderFollowing(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolderFollowing, position: Int) {
        val userFollow = getItem(position)
        holder.bind(userFollow)
    }

    class MyViewHolderFollowing(val binding: ListUsersBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(userFollowing: FollowingUserResponseItem) {
            binding.username.text = userFollowing.login
            val idFormat = itemView.context.getString(R.string.id_profile_users)
            binding.idProfile.text = String.format(idFormat, userFollowing.id)
            Glide.with(itemView.context)
                .load(userFollowing.avatarUrl)
                .skipMemoryCache(true)
                .into(binding.profileImage)
        }
    }

    companion object {
        val DIFF_CALLBACK_FOLLOWING = object : DiffUtil.ItemCallback<FollowingUserResponseItem>() {
            override fun areItemsTheSame(oldItem: FollowingUserResponseItem, newItem: FollowingUserResponseItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: FollowingUserResponseItem, newItem: FollowingUserResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}