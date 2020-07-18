package com.riyaldi.githubuserapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.riyaldi.githubuserapp.R
import com.riyaldi.githubuserapp.adapter.UserRecyclerViewAdapter
import com.riyaldi.githubuserapp.data.User
import com.riyaldi.githubuserapp.model.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MainActivity : AppCompatActivity() {

    private var list = ArrayList<User>()
    private lateinit var mainViewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showLoading(false)
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        searchUser()
        setViewModel()

    }

    private fun searchUser(){
        svUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    if (query.isNotEmpty()) {
                        list.clear()
                        mainViewModel.setUserData(query, applicationContext)
                        setViewModel()
                        setIllustration(false)
                    }
                }
                showLoading(true)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }

    private fun setViewModel() {
        mainViewModel.getUserData().observe(this@MainActivity, Observer { liveUserData ->
            showLoading(true)
            if (liveUserData != null) {
                list = liveUserData

                setIllustration(false)
                setAdapter(list)
                showLoading(false)
            } else {
                list = liveUserData

                setIllustration(true)
                setAdapter(list)
            }
        })
    }

    private fun setIllustration(state: Boolean){
        ivEmpty.isVisible = state
        tvNoData.isVisible = state
        tvNoDataDescription.isVisible = state
    }

    private fun setAdapter(liveUserData: ArrayList<User>) {
        rvMain.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = UserRecyclerViewAdapter(liveUserData)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pbMain.visibility = View.VISIBLE
        } else {
            pbMain.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.favMenu -> {
                val intent = Intent(this@MainActivity, FavouriteUserActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.setting_menu -> {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }
}