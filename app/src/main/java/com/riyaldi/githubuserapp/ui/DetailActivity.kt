package com.riyaldi.githubuserapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.riyaldi.githubuserapp.R
import com.riyaldi.githubuserapp.adapter.SectionsPagerAdapter
import com.riyaldi.githubuserapp.data.User
import kotlinx.android.synthetic.main.activity_detail.*

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    companion object {
        val EXTRA_USER = "extra_user"
    }

    private lateinit var user : User

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

        user = intent.getParcelableExtra(EXTRA_USER) as User

        setDetailInfo()
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
}
