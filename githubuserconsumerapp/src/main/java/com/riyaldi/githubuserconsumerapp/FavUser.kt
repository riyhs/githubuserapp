package com.riyaldi.githubuserconsumerapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavUser (
    val name: String,
    val username: String,
    val followers: String,
    val following: String,
    val repositories: String,
    val url: String
) : Parcelable