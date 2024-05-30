package com.example.githubuser


import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuser.ui.FollowFragment

class SectionsPagerAdapter(activity: AppCompatActivity , private val username : String) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowFragment(0, username)
            1 -> fragment = FollowFragment(1, username)
        }
        return fragment as Fragment
    }
}


