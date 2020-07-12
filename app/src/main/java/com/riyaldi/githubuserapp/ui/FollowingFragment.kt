package com.riyaldi.githubuserapp.ui

import android.os.Bundle
import android.util.Log
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
import com.riyaldi.githubuserapp.model.InfoViewModel
import kotlinx.android.synthetic.main.fragment_following.*
import kotlinx.coroutines.InternalCoroutinesApi

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class FollowingFragment : Fragment() {

    private var listFollowing = arrayListOf<User>()

    private lateinit var viewModel: InfoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("Following", "onViewCreated start")
        showLoading(true)

        val activity: DetailActivity = activity as DetailActivity
        val username: String = activity.getMyData()

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(InfoViewModel::class.java)

        showData(username)
        showRecyclerView()
    }

    private fun showData(username: String) {
        showLoading(true)
        listFollowing.clear()
        viewModel.setUserData(username, requireContext(), "following")
        setViewModel()
    }

    private fun setViewModel() {
        viewModel.getUserData().observe(viewLifecycleOwner, Observer { liveUserData ->
            if (liveUserData != null) {
                listFollowing = liveUserData
            } else listFollowing
            rvFragmentFollowing.adapter = UserRecyclerViewAdapter(listFollowing)
            showLoading(false)
        })
    }

    private fun showRecyclerView() {
        rvFragmentFollowing.apply {
            Log.i("Following", "recyclerView inner start")
            showLoading(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = UserRecyclerViewAdapter(listFollowing)
        }
    }

    @Suppress("SameParameterValue")
    private fun showLoading(state: Boolean) {
        if (state) {
            pbFollowing.visibility = View.VISIBLE
        } else {
            pbFollowing.visibility = View.GONE
        }
    }
}