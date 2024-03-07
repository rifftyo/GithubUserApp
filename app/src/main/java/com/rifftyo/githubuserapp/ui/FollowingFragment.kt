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
import com.rifftyo.githubuserapp.adapter.FollowingAdapter
import com.rifftyo.githubuserapp.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var adapter: FollowingAdapter

    companion object {
        var EXTRA_FOLLOWING = "extrafollowing"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Koneksi MainViewModel
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        // Koneksi Fungsi Loading
        mainViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        // Koneksi Following User
        mainViewModel.followingUser.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        showRecyclerView()

        mainViewModel.getFollowingUser(EXTRA_FOLLOWING)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarFollowing.visibility = View.VISIBLE
        } else {
            binding.progressBarFollowing.visibility = View.GONE
        }
    }

    private fun showRecyclerView() {
        adapter = FollowingAdapter()
        binding.rvListFollowing.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvListFollowing.addItemDecoration(DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL))
        binding.rvListFollowing.adapter = adapter
    }
}