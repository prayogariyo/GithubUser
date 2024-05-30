package com.example.githubuser.data.retrofit

import com.example.githubuser.data.Response.DetailResponseUser
import com.example.githubuser.data.Response.ItemsItem
import com.example.githubuser.data.Response.ResponseUserGithub
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
        fun getUserGithub(@Query("q") page: String): Call<ResponseUserGithub>

    @GET("users/{username}")
       fun getDetailUserGithub(@Path("username") username: String): Call<DetailResponseUser>

    @GET("users/{username}/followers")
       fun getFollowersUserGithub(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
       fun getFollowingUserGithub(@Path("username") username: String): Call<List<ItemsItem>>

}







