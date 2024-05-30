package com.example.githubuser.data.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.database.Fav
import com.example.githubuser.databinding.ItemUserBinding
import com.example.githubuser.ui.DetailActivity

class FavAdapter : ListAdapter<Fav, FavAdapter.MyViewHolder>(FavAdapter.DIFF_CALLBACK)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavAdapter.MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavAdapter.MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: FavAdapter.MyViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class MyViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: Fav) {
            binding.tvName.text = "${user.username}"
            Glide.with(binding.image.context).load(user.avatarUrl).into(binding.image)

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra("USERNAME", user.username)
                intent.putExtra("user_avatar_url", user.avatarUrl)
                binding.root.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Fav>() {
            override fun areItemsTheSame(oldItem: Fav, newItem: Fav): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: Fav, newItem: Fav): Boolean {
                return oldItem == newItem
            }
        }
    }
}