package com.riyaldi.githubuserconsumerapp

import android.annotation.SuppressLint
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: FavUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getFavUserData()
        showRecyclerList()
    }

    private fun showRecyclerList() {
        rvFavUser.adapter = adapter
        rvFavUser.layoutManager = LinearLayoutManager(this)
    }

    private fun setAdapter(cursor: Cursor?) {
        adapter = FavUserAdapter(convertCursor(cursor))
        adapter.notifyDataSetChanged()
    }

    private fun setIllustration(state: Boolean){
        ivSearching.isVisible = state
        tvNoData.isVisible = state
        tvNoDataDescription.isVisible = state
    }

    @SuppressLint("Recycle")
    private fun getFavUserData() {

        val table = "fav_user_table"
        val authority = "com.riyaldi.githubuserapp.provider"

        val uri: Uri = Uri.Builder()
            .scheme("content")
            .authority(authority)
            .appendPath(table)
            .build()

        val contentResolver = this.contentResolver
        val cursor = contentResolver.query(
            uri,
            null,
            null,
            null,
            null
        )


        if (cursor != null && cursor.count > 0) {
            setIllustration(false)
            setAdapter(cursor)
        } else {
            setIllustration(true)
            setAdapter(cursor)
        }

    }

    private fun convertCursor(cursor: Cursor?): ArrayList<FavUser> {
        val favUsers = ArrayList<FavUser>()

        cursor?.apply {
            while (moveToNext()) {
                val name = getString(getColumnIndexOrThrow("name"))
                val username = getString(getColumnIndexOrThrow("username"))
                val followers = getString(getColumnIndexOrThrow("followers"))
                val following = getString(getColumnIndexOrThrow("following"))
                val repositories = getString(getColumnIndexOrThrow("repositories"))
                val url = getString(getColumnIndexOrThrow("photoUrl"))
                favUsers.add(FavUser(name, username, followers, following, repositories, url))
            }
        }
        return favUsers
    }
}