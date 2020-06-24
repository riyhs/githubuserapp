package com.riyaldi.githubuserapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.riyaldi.githubuserapp.R
import com.riyaldi.githubuserapp.adapter.UserRecyclerViewAdapter
import com.riyaldi.githubuserapp.data.User
import com.riyaldi.githubuserapp.model.DetailViewModel
import kotlinx.android.synthetic.main.fragment_followers.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class FollowersFragment : Fragment() {

    private var listFollowers = arrayListOf<User>()

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(true)

        val activity: DetailActivity = activity as DetailActivity
        val user: User = activity.getMyData()

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        showData(user.username)
        showRecyclerView()
    }

    private fun showData(username: String) {
        showLoading(true)
        listFollowers.clear()
        viewModel.setUserData(username, requireContext(), "followers")
        setViewModel()
    }

    private fun setViewModel() {
        viewModel.getUserData().observe(this, Observer { liveUserData ->
            if (liveUserData != null) {
                listFollowers = liveUserData
            }
            rvFragmentFollowers.adapter = UserRecyclerViewAdapter(listFollowers)
            showLoading(false)
        })
    }

    private fun showRecyclerView() {
        rvFragmentFollowers.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = UserRecyclerViewAdapter(listFollowers)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pbFollowers.visibility = View.VISIBLE
        } else {
            pbFollowers.visibility = View.GONE
        }
    }
}