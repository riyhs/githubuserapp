package com.riyaldi.githubuserapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi

@Database(entities = [FavUser::class], version = 1)
abstract class FavUserDatabase : RoomDatabase() {

    abstract fun favUserDAO(): FavUserDAO

    companion object{
        @Volatile
        private var INSTANCE: FavUserDatabase? = null

        @InternalCoroutinesApi
        fun getInstance(context: Context) : FavUserDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FavUserDatabase::class.java,
                        "fav_user_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}