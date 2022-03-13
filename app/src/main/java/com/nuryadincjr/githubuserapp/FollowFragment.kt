package com.nuryadincjr.githubuserapp

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.nuryadincjr.githubuserapp.adapters.ListFollowAdapter
import com.nuryadincjr.githubuserapp.databinding.FragmentFollowBinding
import com.nuryadincjr.githubuserapp.pojo.Users
import com.nuryadincjr.githubuserapp.viewModel.FollowViewModel

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding
    private val followViewModel: FollowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val login = arguments?.getString(ARG_LOGIN)

        followViewModel.apply {
            if (index == 0) {
                findFollowers(login.toString())
                followersResponseItem.observe(viewLifecycleOwner) {
                    showRecyclerList(it)
                }
            } else {
                findFollowing(login.toString())
                followingResponseItem.observe(viewLifecycleOwner) {
                    showRecyclerList(it)
                }
            }

            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

            statusCode.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { respond ->
                    Toast.makeText(context, respond, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun showRecyclerList(list: List<Users>) {
        binding?.rvFollow?.layoutManager =
            if (context?.resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager(context, 2)
            } else LinearLayoutManager(context)

        val listUsersAdapter = ListFollowAdapter(list)
        binding?.rvFollow?.adapter = listUsersAdapter

        listUsersAdapter.setOnItemClickCallback(object : ListFollowAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Users) {
                onStartActivity(data)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun onStartActivity(usersItem: Users) {
        val detailIntent = Intent(context, DetailUserActivity::class.java)
        detailIntent.putExtra(DetailUserActivity.DATA_USER, usersItem)
        startActivity(detailIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_LOGIN = "login"
    }
}