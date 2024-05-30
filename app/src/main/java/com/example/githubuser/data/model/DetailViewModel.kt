package com.example.githubuser.data.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.githubuser.data.Repository.FavRepository
import com.example.githubuser.data.Response.DetailResponseUser
import com.example.githubuser.data.Response.ItemsItem
import com.example.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailViewModel (application: Application): AndroidViewModel(application) {

    private val _detailUser = MutableLiveData<DetailResponseUser>()
    val detailUser: MutableLiveData<DetailResponseUser> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    private val _followersUser = MutableLiveData<List<ItemsItem>>()
    val followersUser: MutableLiveData<List<ItemsItem>> = _followersUser

    private val _followingUser = MutableLiveData<List<ItemsItem>>()
    val followingUser: MutableLiveData<List<ItemsItem>> = _followingUser

    private val mFavRepository: FavRepository = FavRepository(application)


    companion object {
        private const val TAG = "DetailActivity"
    }

    fun detailUserGithub(q : String = "username" ) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUserGithub(q)
        client.enqueue(object : Callback<DetailResponseUser> {
            override fun onResponse(
                call: Call<DetailResponseUser>,
                response: Response<DetailResponseUser>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailUser.value = response.body()
                    }
                } else {
                    Log.e(TAG, "OnResponse ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailResponseUser>, t: Throwable){
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    fun detailFollowers(path : String) {
       _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowersUserGithub(path)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                //_isLoading.value = false
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("response body", responseBody.toString())
                    if (responseBody != null) {
                        _followersUser.value = response.body()
                    }
                } else {
                    Log.e(TAG, "OnFailure ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable){
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    fun detailFollowing(path : String ) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowingUserGithub(path)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _followingUser.value = response.body()
                    }
                } else {
                    Log.e(TAG, "OnFailure ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable){
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }
}

