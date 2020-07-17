package com.riyaldi.githubuserconsumerapp.db

import androidx.lifecycle.LiveData
import com.riyaldi.githubuserconsumerapp.db.FavUser
import com.riyaldi.githubuserconsumerapp.db.FavUserDAO

class FavUserRepository (private val userDAO: FavUserDAO) {
    val allFavUser: LiveData<List<FavUser>> = userDAO.getAll()

    val allInCursor = userDAO.cursorGetAll()

    fun getByUserName(userName: String): LiveData<List<FavUser>> {
        return userDAO.getByUserName(userName)
    }

    suspend fun delete(user: FavUser) {
        userDAO.delete(user)
    }

    suspend fun insert(user: FavUser) {
        userDAO.add(user)
    }
}