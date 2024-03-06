package com.rifftyo.githubuserapp.data.retrofit

import com.rifftyo.githubuserapp.data.response.FollowersUserResponseItem
import com.rifftyo.githubuserapp.data.response.FollowingUserResponse
import com.rifftyo.githubuserapp.data.response.SearchUserResponse
import com.rifftyo.githubuserapp.data.response.UserDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getSearchUsersApi(@Query("q") username: String): Call<SearchUserResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<FollowersUserResponseItem>>

    @GET("users/{username/following")
    fun getFollowing(@Path("username") username: String): Call<List<FollowingUserResponse>>
}