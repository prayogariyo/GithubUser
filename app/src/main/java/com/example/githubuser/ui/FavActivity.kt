package com.example.githubuser.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.adapter.FavAdapter
import com.example.githubuser.data.model.FavViewModel
import com.example.githubuser.data.model.ViewModelFactory
import com.example.githubuser.database.Fav
import com.example.githubuser.databinding.ActivityFavoritBinding


class FavActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritBinding
    private lateinit var favViewModel: FavViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvFav.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)

        binding.rvFav.addItemDecoration(itemDecoration)

        val favViewModel = getFavViewModel(this@FavActivity)
        favViewModel.getAllFav().observe(this) {
            setFavData(it)
         }
    }
    private fun setFavData(consumerFav: List<Fav>) {
        val adapter = FavAdapter()
        adapter.submitList(consumerFav)
        binding.rvFav.adapter = adapter
    }
    private fun getFavViewModel(activity: AppCompatActivity): FavViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavViewModel::class.java]
    }
}
