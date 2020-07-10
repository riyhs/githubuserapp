package com.riyaldi.githubuserapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.ABORT
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface FavUserDAO {
    @Query("SELECT * FROM fav_user_table")
    fun getAll(): LiveData<List<FavUser>>

    @Query("SELECT * FROM fav_user_table WHERE username = :userName")
    fun getByUserName(userName: String): LiveData<List<FavUser>>

    @Insert(onConflict = REPLACE)
    fun add(user: FavUser)

    @Delete
    fun delete(user: FavUser)
}