package com.riyaldi.githubuserconsumerapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.user_item.view.*

class FavUserAdapter(private val favUser: List<FavUser>) : RecyclerView.Adapter<FavUserAdapter.ListViewHolder>() {

    private val size = 130

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavUserAdapter.ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return favUser.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(favUser[position])
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(favUser: FavUser) {
            with(itemView){
                tvName.text = favUser.name
                tvUsername.text = favUser.username
                tvFollowers.text = favUser.followers
                tvFollowing.text = favUser.following
                tvRepo.text = favUser.repositories
                Glide.with(itemView.context)
                    .load(favUser.photoUrl)
                    .apply(RequestOptions().override(size, size))
                    .into(imgProfilePhoto)

                itemView.setOnClickListener {
                    Toast.makeText(context, "Sorry, You can only open Detail Page from Github User App", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}