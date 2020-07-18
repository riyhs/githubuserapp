package com.riyaldi.githubuserapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.riyaldi.githubuserapp.R
import com.riyaldi.githubuserapp.data.User
import com.riyaldi.githubuserapp.ui.DetailActivity
import kotlinx.android.synthetic.main.user_item.view.*
import kotlinx.coroutines.InternalCoroutinesApi

class UserRecyclerViewAdapter(private val listUser: ArrayList<User>) : RecyclerView.Adapter<UserRecyclerViewAdapter.ListViewHolder>() {

    private val size = 120

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    @InternalCoroutinesApi
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    @Suppress("DEPRECATION")
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @InternalCoroutinesApi
        fun bind(user: User) {
            with(itemView){
                tvName.text = user.name
                tvUsername.text = user.username
                tvFollowers.text = user.followers
                tvFollowing.text = user.following
                tvRepo.text = user.repositories
                Glide.with(itemView.context)
                    .load(user.photoUrl)
                    .apply(RequestOptions().override(size, size))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(imgProfilePhoto)

                itemView.setOnClickListener {
                    val username = user.username

                    val moveWithObjectIntent = Intent(it.context, DetailActivity::class.java)
                    moveWithObjectIntent.putExtra(DetailActivity.EXTRA_USER, username)
                    it.context.startActivity(moveWithObjectIntent)
                }
            }
        }
    }
}