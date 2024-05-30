package com.example.githubuser.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.data.Response.ItemsItem
import com.example.githubuser.data.model.DetailViewModel
import com.example.githubuser.databinding.FragmentFollowBinding

class FollowFragment(private val position: Int, private val username : String ) : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var detailViewModel: DetailViewModel
    private var _binding: FollowFragment? = null
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        detailViewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        if (position == 0){
            detailViewModel.detailFollowers(username)
        }else{
            detailViewModel.detailFollowing(username)
        }
        if (position == 0) {
            detailViewModel.followersUser.observe(viewLifecycleOwner) {
                setUsersData(it)
            }
        }else {
            detailViewModel.followingUser.observe(viewLifecycleOwner) {
                setUsersData(it)
            }
        }
        detailViewModel.isLoading.observe(this){
            showLoading(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun setUsersData(consumerUser: List<ItemsItem>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = UserAdapter()
        adapter.submitList(consumerUser)
        binding.recyclerView.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarFollow.visibility = if (state) View.VISIBLE else View.GONE
    }
}