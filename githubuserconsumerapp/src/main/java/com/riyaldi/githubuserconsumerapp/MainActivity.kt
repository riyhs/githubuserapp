package com.riyaldi.githubuserconsumerapp

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.riyaldi.githubuserconsumerapp.adapter.FavUserAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private lateinit var adapter: FavUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showRecyclerList()
        getFavUserData()
    }

    private fun showRecyclerList() {
        adapter = FavUserAdapter()
        adapter.notifyDataSetChanged()
        rvFavUser.adapter = adapter
        rvFavUser.layoutManager = LinearLayoutManager(this)
    }

    @SuppressLint("Recycle")
    private fun getFavUserData() {

        val TABLE = "fav_user_table"
        val AUTHORITY = "com.riyaldi.githubuserapp.provider"

        val URI: Uri = Uri.Builder()
            .scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE)
            .build()

        val contentResolver = this.contentResolver
        val cursor = contentResolver.query(
            URI,
            null,
            null,
            null,
            null
        )

        Log.d("@CURSOR", cursor?.count.toString())

        if (cursor != null && cursor.count > 0) {
            adapter.setFromCursor(cursor)
        }

    }
}