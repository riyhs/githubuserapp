package com.riyaldi.githubuserapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.riyaldi.githubuserapp.R
import com.riyaldi.githubuserapp.data.User
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.coroutines.InternalCoroutinesApi

class DetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity: DetailActivity = activity as DetailActivity
        val user: User = activity.getMyData()

        showDetail(user)
    }

    @SuppressLint("SetTextI18n")
    private fun showDetail(user: User){
        tvDetailName.text = user.name
        tvDetailUsername.text = user.username
        tvDetailCompany.text = user.company
        tvDetailLocation.text = user.location
        tvDetailRepo.text = "${user.repositories} Repositories"
    }
}