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
import com.riyaldi.githubuserapp.db.FavUser
import com.riyaldi.githubuserapp.db.FavUserDatabase
import com.riyaldi.githubuserapp.model.FavUserViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    companion object {
        val EXTRA_USER = "extra_user"
        val EXTRA_FAV_USER = "extra_fav_user"
    }

    private lateinit var user : User
    private lateinit var viewModel: FavUserViewModel
    private var isFav: Boolean = false

    private val userDetail by lazy {
        User(
            name = user.name,
            username = user.username,
            company = user.company,
            location = user.location,
            bio = user.bio,
            repositories = user.repositories,
            followers = user.followers,
            following = user.following,
            photoUrl = user.photoUrl,
            followingUrl = user.followingUrl,
            followersUrl = user.followersUrl
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setViewPager()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        user = intent.getParcelableExtra(EXTRA_USER) as User

        viewModel = ViewModelProvider(this).get(FavUserViewModel::class.java)

        isLiked()
        setDetailInfo()
        addFavUser()
    }

    fun getMyData(): User {
        return userDetail
    }

    @SuppressLint("SetTextI18n")
    private fun setDetailInfo() {
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
        dao.getByUserName(user.username).observe(this, Observer { liveUserData ->
            if (liveUserData.isNotEmpty() && liveUserData[0].username.isNotEmpty()) {
                isFav = true
                changeFabIcon(isFav)
            } else {
                isFav = false
                changeFabIcon(isFav)
            }
        })
    }

    private fun addFavUser() {
        fabLoveInDetail.setOnClickListener {
            isLiked()
            if (!isFav) {
                viewModel.addFavUser(
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
                viewModel.delete(
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
        when(item.itemId) {
            R.id.favMenu -> {
                val intent = Intent(this@DetailActivity, FavouriteUserActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.setting_menu -> {
                val intent = Intent(this@DetailActivity, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return true
        }
    }
}
