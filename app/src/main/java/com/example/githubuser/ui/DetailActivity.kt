package com.example.githubuser.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.SectionsPagerAdapter
import com.example.githubuser.data.model.DetailViewModel
import com.example.githubuser.data.model.FavViewModel
import com.example.githubuser.data.model.ViewModelFactory
import com.example.githubuser.database.Fav
import com.example.githubuser.database.FavDao
import com.example.githubuser.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this.application)
        val favViewModel: FavViewModel by viewModels<FavViewModel> { factory }

        supportActionBar?.title = "Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra("USERNAME") ?: ""
        val userAvatarUrl = intent.getStringExtra("user_avatar_url")
        val sectionsPagerAdapter = SectionsPagerAdapter(this, username)

        if (username != null) {
            detailViewModel.detailUserGithub(username)
            detailViewModel.detailFollowers(username)
        }

        Log.e("username", username)

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailViewModel.detailUser.observe(this@DetailActivity) { data ->
            binding.apply {
                tvUserLogin.text = data.login
                tvName.text = data.name
                tvFollowers.text = data.followers.toString()
                tvFollowing.text = data.following.toString()
                    val user = Fav(username = data.login, avatarUrl = data.avatarUrl)
                    favViewModel.getFavByUser(data.login).observe(this@DetailActivity) { data ->
                        val isFavorite = data != null
                        favoriteBtn.setImageResource(if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24)
                        favoriteBtn.setOnClickListener {

                            if (isFavorite) favViewModel.deleteFav(user) else favViewModel.addFav(
                                user
                            )
                            Toast.makeText(
                                this@DetailActivity,
                                "${if (isFavorite) "Delete" else "Add"} Favorite User",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            detailViewModel.isLoading.observe(this) {
                showLoading(it)
            }
            Glide.with(this).load(userAvatarUrl).into(binding.userAvatar)
        }
    }
