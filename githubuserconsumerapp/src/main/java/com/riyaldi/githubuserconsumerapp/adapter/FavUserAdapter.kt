package com.riyaldi.githubuserconsumerapp.adapter

import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.riyaldi.githubuserconsumerapp.R
import kotlinx.android.synthetic.main.user_item.view.*
import kotlinx.coroutines.InternalCoroutinesApi

class FavUserAdapter : RecyclerView.Adapter<FavUserAdapter.ListViewHolder>() {

    private var cursor: Cursor? = null

    private val name = "name"
    private val username = "username"
    private val followers = "followers"
    private val following = "following"
    private val repositories = "repositories"
    private val url = "photoUrl"

    fun setFromCursor(cursor: Cursor) {
        this.cursor = cursor
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavUserAdapter.ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }

    @InternalCoroutinesApi
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        cursor?.moveToPosition(position)?.let { holder.bind(it) }
    }

    @Suppress("DEPRECATION")
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @InternalCoroutinesApi
        fun bind(moveToPosition: Boolean) {
            if (moveToPosition)
                with(itemView){
                    tvName.text = cursor?.getString(cursor?.getColumnIndexOrThrow(name) ?: 0)
                    tvUsername.text = cursor?.getString(cursor?.getColumnIndexOrThrow(username) ?: 0)
                    tvFollowers.text = cursor?.getString(cursor?.getColumnIndexOrThrow(followers) ?: 0)
                    tvFollowing.text = cursor?.getString(cursor?.getColumnIndexOrThrow(following) ?: 0)
                    tvRepo.text = cursor?.getString(cursor?.getColumnIndexOrThrow(repositories) ?: 0)
                    Glide.with(itemView.context)
                        .load(cursor?.getString(cursor?.getColumnIndexOrThrow(url) ?: 0))
                        .apply(RequestOptions().override(130, 130))
                        .into(imgProfilePhoto)
                }
        }
    }
}