package com.riyaldi.githubuserapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var name: String,
    var username: String,
    var followers: String,
    var following: String,
    var photoUrl: String,
    var repositories: String,
    var followersUrl: String,
    var followingUrl: String
) : Parcelable

// detail activity
// main activity
// user recyclerview adapter