package com.riyaldi.githubuserapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavUser::class], version = 1, exportSchema = false)
abstract class FavUserDatabase : RoomDatabase() {

    abstract fun favUserDAO(): FavUserDAO

    companion object {
        @Volatile
        private var INSTANCE: FavUserDatabase? = null

        fun getInstance(context: Context): FavUserDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavUserDatabase::class.java,
                    "fav_user_database").fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}