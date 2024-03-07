package com.rifftyo.githubuserapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifftyo.githubuserapp.ViewModel.MainViewModel
import com.rifftyo.githubuserapp.adapter.FollowersAdapter
import com.rifftyo.githubuserapp.databinding.FragmentFollowersBinding

class FollowersFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: FragmentFollowersBinding
    private lateinit var adapter: FollowersAdapter

    companion object {
        var EXTRA_FOLLOWERS = "extrafollowers"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Koneksi MainViewModel
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        // Koneksi Fungsi Loading
        mainViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        // Koneksi Followers User
        mainViewModel.followersUser.observe(viewLifecycleOwner) {followersList ->
            adapter.submitList(followersList)
        }

        showRecyclerView()

        mainViewModel.getFollowersUser(EXTRA_FOLLOWERS)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarFollowers.visibility = View.VISIBLE
        } else {
            binding.progressBarFollowers.visibility = View.GONE
        }
    }

    private fun showRecyclerView() {
        adapter = FollowersAdapter()
        binding.rvListFollowers.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvListFollowers.addItemDecoration(DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL))
        binding.rvListFollowers.adapter = adapter
    }
}