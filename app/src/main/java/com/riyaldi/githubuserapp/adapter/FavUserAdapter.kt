package com.riyaldi.githubuserapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.riyaldi.githubuserapp.R
import com.riyaldi.githubuserapp.db.FavUser
import com.riyaldi.githubuserapp.ui.DetailActivity
import kotlinx.android.synthetic.main.user_item.view.*
import kotlinx.coroutines.InternalCoroutinesApi

class FavUserAdapter(private val favUser: List<FavUser>) : RecyclerView.Adapter<FavUserAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavUserAdapter.ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return favUser.size
    }

    @InternalCoroutinesApi
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(favUser[position])
    }

    @Suppress("DEPRECATION")
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @InternalCoroutinesApi
        fun bind(favUser: FavUser) {
            with(itemView){
                tvName.text = favUser.name
                tvUsername.text = favUser.username
                tvFollowers.text = favUser.followers
                tvFollowing.text = favUser.following
                tvRepo.text = favUser.repositories
                Glide.with(itemView.context)
                    .load(favUser.photoUrl)
                    .apply(RequestOptions().override(130, 130))
                    .into(imgProfilePhoto)

                itemView.setOnClickListener {
                    val favUserUsername = favUser.username
                    val moveWithObjectIntent = Intent(it.context, DetailActivity::class.java)
                    moveWithObjectIntent.putExtra(DetailActivity.EXTRA_USER, favUserUsername)
                    it.context.startActivity(moveWithObjectIntent)
                }
            }
        }
    }
}