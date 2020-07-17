package com.riyaldi.githubuserconsumerapp.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "fav_user_table")
@Parcelize
data class FavUser (
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "username") var username: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "company") var company: String,
    @ColumnInfo(name = "location") var location: String,
    @ColumnInfo(name = "bio") var bio: String,
    @ColumnInfo(name = "repositories") var repositories: String,
    @ColumnInfo(name = "followers") var followers: String,
    @ColumnInfo(name = "following") var following: String,
    @ColumnInfo(name = "followersUrl") var followersUrl: String,
    @ColumnInfo(name = "followingUrl") var followingUrl: String,
    @ColumnInfo(name = "photoUrl") var photoUrl: String
) : Parcelable