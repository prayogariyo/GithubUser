package com.example.githubuser.data.model

import android.util.Log
import android.view.WindowInsetsAnimation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.example.githubuser.SettingPreferences
import com.example.githubuser.data.Response.DetailResponseUser
import com.example.githubuser.data.Response.ItemsItem
import com.example.githubuser.data.Response.ResponseUserGithub
import com.example.githubuser.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: SettingPreferences): ViewModel() {

    private val _data = MutableLiveData<String>()
    val data: LiveData<String> get() = _data

    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        fetchDataFromAPI()
    }
    private fun fetchDataFromAPI() {
    }

    companion object {
        private const val TAG = "Activity"
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

}





