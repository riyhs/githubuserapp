package com.riyaldi.githubuserapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.riyaldi.githubuserapp.R
import com.riyaldi.githubuserapp.adapter.FavUserAdapter
import com.riyaldi.githubuserapp.db.FavUserDatabase
import kotlinx.android.synthetic.main.activity_favourite_user.*
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class FavouriteUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getFavUserData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favMenu -> {
                val intent = Intent(this@FavouriteUserActivity, FavouriteUserActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.setting_menu -> {
                val intent = Intent(this@FavouriteUserActivity, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }

    private fun getFavUserData() {
        val db = FavUserDatabase.getInstance(applicationContext)
        val dao = db.favUserDAO()

        dao.getAll().observe(this, Observer { liveUserData ->
            rvFavUser.apply {
                layoutManager = LinearLayoutManager(this@FavouriteUserActivity)
                adapter = FavUserAdapter(liveUserData)
            }
        })
    }
}