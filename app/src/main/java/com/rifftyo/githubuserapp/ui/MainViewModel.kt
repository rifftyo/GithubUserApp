package com.rifftyo.githubuserapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rifftyo.githubuserapp.data.response.FollowersUserResponseItem
import com.rifftyo.githubuserapp.data.response.SearchUserResponse
import com.rifftyo.githubuserapp.data.response.UserDetailResponse
import com.rifftyo.githubuserapp.data.response.UserItem
import com.rifftyo.githubuserapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val _userItem = MutableLiveData<List<UserItem>>()
    val userItem: LiveData<List<UserItem>> = _userItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userDetail = MutableLiveData<UserDetailResponse>()
    val userDetail: LiveData<UserDetailResponse> = _userDetail

    private val _followersUser = MutableLiveData<List<FollowersUserResponseItem>>()
    val followersUser: LiveData<List<FollowersUserResponseItem>> = _followersUser

    companion object{
        private const val TAG = "MainViewModel"
    }

    init {
        getListUsers("Asep")
    }


    // Fungsi Mendapatkan List User
    fun getListUsers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getSearchUsersApi(username)
        client.enqueue(object : Callback<SearchUserResponse> {
            override fun onResponse(
                call: Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userItem.value = response.body()?.items
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    // Fungsi Mendapatkan Detail User
    fun getDetailUsers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object: Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (responseBody != null) {
                    _userDetail.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    // Fungsi Mendapatkan List Followers
    fun getFollowersUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<FollowersUserResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowersUserResponseItem>>,
                response: Response<List<FollowersUserResponseItem>>
            ) {
                _isLoading.value = false
                if (response.body() != null) {
                    _followersUser.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowersUserResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }
}