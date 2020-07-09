package com.riyaldi.githubuserapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavUserDAO {
    @Query("SELECT * FROM fav_user_table")
    fun getAll(): LiveData<List<FavUser>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(user: FavUser)
}