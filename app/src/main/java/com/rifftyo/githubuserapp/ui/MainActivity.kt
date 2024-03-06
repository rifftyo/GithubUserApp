package com.rifftyo.githubuserapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifftyo.githubuserapp.data.response.UserItem
import com.rifftyo.githubuserapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UsersAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Koneksi MainViewModel
        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)


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
