package com.rifftyo.githubuserapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifftyo.githubuserapp.adapter.UsersFavoriteAdapter
import com.rifftyo.githubuserapp.data.FavUserRepository
import com.rifftyo.githubuserapp.data.database.FavoriteUserRoomDatabase
import com.rifftyo.githubuserapp.databinding.ActivityUserFavoriteBinding
import com.rifftyo.githubuserapp.utils.AppExecutors

class UserFavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserFavoriteBinding
    private lateinit var usersFavoriteAdapter: UsersFavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi RecyclerView dan Adapter
        usersFavoriteAdapter = UsersFavoriteAdapter()
        binding.rvListFavUsers.apply {
            layoutManager = LinearLayoutManager(this@UserFavoriteActivity)
            adapter = usersFavoriteAdapter
        }

        // Mendapatkan Data Pengguna Favorit
        val favoriteDao = FavoriteUserRoomDatabase.getDatabase(applicationContext).FavoriteUserDao()
        val appExecutors = AppExecutors()
        val favUserRepository = FavUserRepository.getInstance(favoriteDao, appExecutors)

        favUserRepository.getFavoriteUser().observe(this) {favoriteUsers ->
            usersFavoriteAdapter.submitList(favoriteUsers)
        }
    }
}