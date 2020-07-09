package com.riyaldi.githubuserapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.riyaldi.githubuserapp.R
import com.riyaldi.githubuserapp.adapter.UserRecyclerViewAdapter
import com.riyaldi.githubuserapp.data.User
import com.riyaldi.githubuserapp.model.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MainActivity : AppCompatActivity() {

    private var list = ArrayList<User>()
    private lateinit var mainViewModel : MainViewModel
    private lateinit var adapter: UserRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pbMain.visibility = View.GONE

        adapter = UserRecyclerViewAdapter(list)
        adapter.notifyDataSetChanged()
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        searchUser()
        setViewModel()
        showRecyclerList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.favMenu -> {
                val intent = Intent(this@MainActivity, FavouriteUserActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.setting_menu -> {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return true
        }
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
                rvMain.adapter = UserRecyclerViewAdapter(list)
                showLoading(false)
            }
        })
    }

    private fun showRecyclerList() {
        rvMain.layoutManager = LinearLayoutManager(this)
        rvMain.adapter = UserRecyclerViewAdapter(list)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pbMain.visibility = View.VISIBLE
        } else {
            pbMain.visibility = View.GONE
        }
    }
}