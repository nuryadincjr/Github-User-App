package com.nuryadincjr.githubuserapp.adapters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nuryadincjr.githubuserapp.view.FollowFragment
import com.nuryadincjr.githubuserapp.util.Constant.ARG_LOGIN
import com.nuryadincjr.githubuserapp.util.Constant.ARG_SECTION_NUMBER
import com.nuryadincjr.githubuserapp.util.Constant.TAB_TITLES

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var login: String = ""

    override fun getItemCount(): Int {
        return TAB_TITLES.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()

        fragment.arguments = Bundle().apply {
            putInt(ARG_SECTION_NUMBER, position)
            putString(ARG_LOGIN, login)
        }

        return fragment
    }
}