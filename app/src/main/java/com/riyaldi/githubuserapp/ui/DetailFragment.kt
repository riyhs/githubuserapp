package com.riyaldi.githubuserapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.riyaldi.githubuserapp.R
import com.riyaldi.githubuserapp.data.User
import com.riyaldi.githubuserapp.model.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.coroutines.InternalCoroutinesApi

class DetailFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)

        val activity: DetailActivity = activity as DetailActivity
        val username: String = activity.getMyData()

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        setViewModel(username)
    }

    private fun setViewModel(username: String) {
        showLoading(true)
        context?.let { detailViewModel.getDetailUserData(username, it) }
        detailViewModel.getUserData().observe(viewLifecycleOwner, Observer {
            showDetail(it)
        })
    }

    @SuppressLint("SetTextI18n")
    private fun showDetail(user: User){
        tvDetailName.text = user.name
        tvDetailUsername.text = user.username
        tvDetailCompany.text = user.company
        tvDetailLocation.text = user.location
        tvDetailRepo.text = "${user.repositories} Repositories"
        showLoading(false)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pbDetail.isVisible = true
        } else {
            pbDetail.isGone = true
        }
    }
}