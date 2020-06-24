package com.riyaldi.githubuserapp.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.riyaldi.githubuserapp.R
import com.riyaldi.githubuserapp.ui.DetailActivity
import com.riyaldi.githubuserapp.data.User
import kotlinx.android.synthetic.main.fragment_followers.view.*
import kotlinx.android.synthetic.main.user_item.view.*

class UserRecyclerViewAdapter(private val listUser: ArrayList<User>) : RecyclerView.Adapter<UserRecyclerViewAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    @Suppress("DEPRECATION")
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            with(itemView){
                tvName.text = user.name
                tvUsername.text = user.username
                tvFollowers.text = user.followers
                tvFollowing.text = user.following
                tvRepo.text = user.repositories
                Glide.with(itemView.context)
                    .load(user.photoUrl)
                    .apply(RequestOptions().override(130, 130))
                    .into(imgProfilePhoto)

                itemView.setOnClickListener {
                    val userDetail = User(
                        name = user.name,
                        username = user.username,
                        company = user.company,
                        location = user.location,
                        bio = user.bio,
                        repositories = user.repositories,
                        followers = user.followers,
                        following = user.following,
                        photoUrl = user.photoUrl,
                        followersUrl = user.followersUrl,
                        followingUrl = user.followingUrl
                    )

                    val moveWithObjectIntent = Intent(it.context, DetailActivity::class.java)
                    moveWithObjectIntent.putExtra(DetailActivity.EXTRA_USER, userDetail)
                    it.context.startActivity(moveWithObjectIntent)
                }
            }
        }
    }
}