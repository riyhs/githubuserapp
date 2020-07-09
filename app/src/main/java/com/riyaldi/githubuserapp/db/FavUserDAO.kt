package com.riyaldi.githubuserapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface FavUserDAO {
    @Query("SELECT * FROM fav_user_table")
    fun getAll(): LiveData<List<FavUser>>

    @Insert(onConflict = REPLACE)
    fun add(user: FavUser)
}