package com.riyaldi.githubuserapp.db

import androidx.lifecycle.LiveData

class FavUserRepository (private val userDAO: FavUserDAO) {
    val allFavUser: LiveData<List<FavUser>> = userDAO.getAll()

    fun getById(id: Int): LiveData<FavUser> {
        return userDAO.getById(id)
    }

    suspend fun insert(user: FavUser) {
        userDAO.add(user)
    }
}