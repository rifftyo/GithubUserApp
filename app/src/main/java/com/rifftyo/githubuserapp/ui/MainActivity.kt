package com.rifftyo.githubuserapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifftyo.githubuserapp.ViewModel.MainViewModel
import com.rifftyo.githubuserapp.ViewModel.ViewModelFactory
import com.rifftyo.githubuserapp.ViewModel.ViewModelTheme
import com.rifftyo.githubuserapp.adapter.UsersAdapter
import com.rifftyo.githubuserapp.data.response.UserItem
import com.rifftyo.githubuserapp.databinding.ActivityMainBinding
import com.rifftyo.githubuserapp.utils.SettingPreferences
import com.rifftyo.githubuserapp.utils.dataStore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UsersAdapter
    private lateinit var viewModelTheme: ViewModelTheme


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Koneksi ViewModelTheme
        val pref = SettingPreferences.getInstance(application.dataStore)
        viewModelTheme = ViewModelProvider(this, ViewModelFactory(pref)).get(ViewModelTheme::class.java)

        viewModelTheme.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // Koneksi MainViewModel
        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)


        // Koneksi Fungsi MainViewModel
        mainViewModel.userItem.observe(this){
            setUsersListData(it)
        }

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }

        // Fitur Pencarian
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener{ textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    mainViewModel.getListUsers(searchView.text.toString())
                    false
                }
        }

        // Koneksi List Adapter
        showRecyclerView()

        binding.iconImage.setOnClickListener {
            val intent = Intent(this, UserFavoriteActivity::class.java)
            startActivity(intent)
        }

        binding.fabSetting.setOnClickListener {
            val intent = Intent(this, ThemeActivity::class.java)
            startActivity(intent)
        }
    }

    // Fungsi Memasukkan Data
    private fun setUsersListData(usersList: List<UserItem>) {
        adapter.submitList(usersList)
    }

    // Fungsi Loading
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showRecyclerView() {
        adapter = UsersAdapter()
        binding.rvListUsers.layoutManager = LinearLayoutManager(this)
        binding.rvListUsers.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        binding.rvListUsers.adapter = adapter
    }
}
