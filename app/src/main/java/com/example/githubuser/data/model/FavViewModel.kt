package com.example.githubuser.data.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.Repository.FavRepository
import com.example.githubuser.database.Fav

class FavViewModel(application: Application = Application()): ViewModel() {

    private val mFavRepository: FavRepository = FavRepository(application)
    fun getAllFav(): LiveData<List<Fav>> = mFavRepository.getAllFav()

    fun addFav(fav: Fav) {
        mFavRepository.insert(fav)
    }
    fun deleteFav(fav: Fav) {
        mFavRepository.delete(fav)
    }
    fun getFavByUser(fav: String): LiveData<Fav> = mFavRepository.getFavByUser(fav)
}