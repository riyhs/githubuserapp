package com.riyaldi.githubuserapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_user_table")
data class FavUser (
    @PrimaryKey(autoGenerate = true) var id : Int,
    @ColumnInfo(name = "name") var name: String
)