package com.nuryadincjr.githubuserapp.adapters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nuryadincjr.githubuserapp.FollowFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var login: String = ""

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()

        if(fragment.arguments== null) {
            fragment.arguments = Bundle().apply {
                putInt(FollowFragment.ARG_SECTION_NUMBER, position)
                putString(FollowFragment.ARG_LOGIN, login)
            }
        }

        return fragment
    }
}