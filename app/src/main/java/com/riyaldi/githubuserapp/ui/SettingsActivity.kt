package com.riyaldi.githubuserapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.riyaldi.githubuserapp.R
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()

        setMyActionBar()
    }

    private fun setMyActionBar() {
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.favMenu -> {
                val intent = Intent(this@SettingsActivity, FavouriteUserActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.setting_menu -> {
                val intent = Intent(this@SettingsActivity, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            android.R.id.home -> {
                finish()
                true
            }
            else -> true
        }
    }
}