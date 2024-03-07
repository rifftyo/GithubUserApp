package com.rifftyo.githubuserapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rifftyo.githubuserapp.R
import com.rifftyo.githubuserapp.ViewModel.MainViewModel
import com.rifftyo.githubuserapp.adapter.SectionsPagerAdapter
import com.rifftyo.githubuserapp.data.response.UserDetailResponse
import com.rifftyo.githubuserapp.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding

    companion object {
        private const val TAG = "UserDetail"
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Koneksi Tab Layout
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        // Koneksi MainViewModel
        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        // Koneksi Fungsi
        mainViewModel.userDetail.observe(this) { detailUser ->
            setDetailUsersData(detailUser)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        // Ambil Data Username
        val username = intent.getStringExtra(EXTRA_USERNAME)
        if (username != null) {
            mainViewModel.getDetailUsers(username)
            FollowersFragment.EXTRA_FOLLOWERS = username
            FollowingFragment.EXTRA_FOLLOWING = username
        } else {
            Log.e(TAG, "No username provided")
        }
    }

    // Fungsi Mengambil Data API
    private fun setDetailUsersData(detailUser: UserDetailResponse) {
        binding.nameDetailUser.text = detailUser.name
        binding.usernameUserDetail.text = detailUser.login
        binding.jumlahFollowers.text = detailUser.followers.toString()
        binding.jumlahFollowing.text = detailUser.following.toString()
        Glide.with(this@UserDetailActivity)
            .load(detailUser.avatarUrl)
            .into(binding.profileImageDetail)
    }

    // Fungsi Loading
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarDetailUser.visibility = View.VISIBLE
        } else {
            binding.progressBarDetailUser.visibility = View.GONE
        }
    }
}