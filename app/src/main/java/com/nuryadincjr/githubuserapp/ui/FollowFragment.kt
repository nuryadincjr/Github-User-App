package com.nuryadincjr.githubuserapp.ui

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
import com.nuryadincjr.githubuserapp.adapters.ListUsersAdapter
import com.nuryadincjr.githubuserapp.data.remote.response.Users
import com.nuryadincjr.githubuserapp.databinding.FragmentFollowBinding
import com.nuryadincjr.githubuserapp.util.Constant.ARG_LOGIN
import com.nuryadincjr.githubuserapp.util.Constant.ARG_SECTION_NUMBER
import com.nuryadincjr.githubuserapp.util.Constant.SPAN_COUNT
import com.nuryadincjr.githubuserapp.util.factory.FollowViewModelFactory
import com.nuryadincjr.githubuserapp.viewModel.FollowViewModel

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding

    private val followViewModel: FollowViewModel by viewModels {
        FollowViewModelFactory.getInstance(requireContext())
    }

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
        val login = arguments?.getString(ARG_LOGIN).toString()

        onSubscribeViewModel(index, login)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun onSubscribeViewModel(index: Int?, login: String) {
        followViewModel.apply {
            if (index == 0) {
                getFollowers(login).observe(viewLifecycleOwner) { showRecyclerList(it) }
            } else {
                getFollowing(login).observe(viewLifecycleOwner) { showRecyclerList(it) }
            }

            isLoading().observe(viewLifecycleOwner) { showLoading(it) }

            statusCode().observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { respond ->
                    Toast.makeText(context, respond, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun showRecyclerList(listUsers: List<Users>) {
        val listUsersAdapter = ListUsersAdapter(listUsers)

        binding?.rvFollow?.apply {
            layoutManager =
                if (context?.resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    GridLayoutManager(context, SPAN_COUNT)
                } else LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = listUsersAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}