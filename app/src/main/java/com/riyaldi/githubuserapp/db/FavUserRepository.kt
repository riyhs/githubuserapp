package com.riyaldi.githubuserapp.db

import androidx.lifecycle.LiveData

class FavUserRepository (private val userDAO: FavUserDAO) {
    val allFavUser: LiveData<List<FavUser>> = userDAO.getAll()

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