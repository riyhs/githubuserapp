package com.riyaldi.githubuserapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.riyaldi.githubuserapp.R
import com.riyaldi.githubuserapp.adapter.SectionsPagerAdapter
import com.riyaldi.githubuserapp.data.User
import com.riyaldi.githubuserapp.db.FavUserDatabase
import com.riyaldi.githubuserapp.model.DetailViewModel
import com.riyaldi.githubuserapp.model.FavUserViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    companion object {
        val EXTRA_USER = "extra_user"
    }

    private lateinit var username : String
    private lateinit var favUserViewModel: FavUserViewModel
    private lateinit var detailViewModel: DetailViewModel
    private var isFav: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setViewPager()

        username = intent.getStringExtra(EXTRA_USER) as String

        favUserViewModel = ViewModelProvider(this).get(FavUserViewModel::class.java)
        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        setActionBar(username)
        isLiked()
        setDetail()
    }

    private fun setActionBar(username: String){
        supportActionBar?.title = "$username's Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setDetail() {
        detailViewModel.getDetailUserData(username, applicationContext)
        detailViewModel.getUserData().observe(this, Observer {
            addFavUser(it)
            setDetailInfo(it)
        })
    }

    fun getMyData(): String {
        return username
    }

    @SuppressLint("SetTextI18n")
    private fun setDetailInfo(user: User) {
        tvProfileName.text = user.name
        tvProfileBio.text = "\"${user.bio}\""

        Glide.with(this@DetailActivity)
            .load(user.photoUrl)
            .into(imgProfilePhoto)
    }

    private fun setViewPager(){
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = sectionsPagerAdapter
        materialTab.setupWithViewPager(viewPager)
        supportActionBar?.elevation = 0f
    }

    private fun changeFabIcon(state: Boolean) {
        if (state) {
            fabLoveInDetail.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_love_filled))
        } else {
            fabLoveInDetail.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_love_border))
        }
    }

    private fun isLiked(){
        val db = FavUserDatabase.getInstance(applicationContext)
        val dao = db.favUserDAO()
        dao.getByUserName(username).observe(this, Observer { liveUserData ->
            if (liveUserData.isNotEmpty() && liveUserData[0].username.isNotEmpty()) {
                isFav = true
                changeFabIcon(isFav)
            } else {
                isFav = false
                changeFabIcon(isFav)
            }
        })
    }

    private fun addFavUser(user: User) {
        fabLoveInDetail.setOnClickListener {
            isLiked()
            if (!isFav) {
                favUserViewModel.addFavUser(
                    name = user.name,
                    username = user.username,
                    company = user.company,
                    bio = user.bio,
                    repositories = user.repositories,
                    location = user.location,
                    following = user.following,
                    followers = user.followers,
                    followingUrl = user.followingUrl,
                    followersUrl = user.followersUrl,
                    photoUrl = user.photoUrl)
                Toast.makeText(this@DetailActivity, "Added to Favourite", Toast.LENGTH_SHORT).show()
            } else {
                favUserViewModel.delete(
                    name = user.name,
                    username = user.username,
                    company = user.company,
                    bio = user.bio,
                    repositories = user.repositories,
                    location = user.location,
                    following = user.following,
                    followers = user.followers,
                    followingUrl = user.followingUrl,
                    followersUrl = user.followersUrl,
                    photoUrl = user.photoUrl)
                Toast.makeText(this@DetailActivity, "Removed from Favourite", Toast.LENGTH_SHORT).show()
            }
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
                val intent = Intent(this@DetailActivity, FavouriteUserActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.setting_menu -> {
                val intent = Intent(this@DetailActivity, SettingsActivity::class.java)
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
