package com.rifftyo.githubuserapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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
import com.rifftyo.githubuserapp.data.FavUserRepository
import com.rifftyo.githubuserapp.data.database.FavoriteUser
import com.rifftyo.githubuserapp.data.database.FavoriteUserRoomDatabase
import com.rifftyo.githubuserapp.data.response.UserDetailResponse
import com.rifftyo.githubuserapp.databinding.ActivityUserDetailBinding
import com.rifftyo.githubuserapp.utils.AppExecutors

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private var isFavorite = false
    private lateinit var favUserRepository: FavUserRepository


    companion object {
        private const val TAG = "UserDetail"
        var EXTRA_USERNAME = "extra_username"

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

        // Inisialisasi favUserRepository
        val favoriteDao = FavoriteUserRoomDatabase.getDatabase(applicationContext).FavoriteUserDao()
        val appExecutors = AppExecutors()
        favUserRepository = FavUserRepository.getInstance(favoriteDao, appExecutors)

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
            EXTRA_USERNAME = username

            // Ambil status favorit dari database
            favUserRepository.getFavoriteUser().observe(this) { favoriteUsers ->
                val favoriteUser = favoriteUsers.find { it.username == username }
                isFavorite = favoriteUser != null
                updateFavoriteButtonIcon()
            }
        } else {
            Log.e(TAG, "No username provided")
        }

        // Aksi Klik Float Button
        binding.fabAdd.setOnClickListener {
            isFavorite = !isFavorite    // Toggle Status Favorite
            updateFavoriteButtonIcon()

            val userDetail = mainViewModel.userDetail.value
            userDetail?.let { detail ->
                val favoriteUser = FavoriteUser(detail.login, detail.avatarUrl ?: "", isFavorite)
                if (isFavorite) {
                    favUserRepository.addFavoriteUser(favoriteUser)
                    Toast.makeText(this, "User Ditambahkan Ke Favorit", Toast.LENGTH_SHORT).show()
                } else {
                    favUserRepository.removeFavoriteUser(favoriteUser)
                    Toast.makeText(this, "User Dihapus Dari Favorit", Toast.LENGTH_SHORT).show()
                }
            }
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

    private fun updateFavoriteButtonIcon() {
        if (isFavorite) {
            binding.fabAdd.setImageResource(R.drawable.favorite_icon)
        } else {
            binding.fabAdd.setImageResource(R.drawable.favorite_border_icon)
        }
    }
}