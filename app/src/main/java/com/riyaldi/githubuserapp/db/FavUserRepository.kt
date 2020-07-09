package com.riyaldi.githubuserapp.db

import androidx.lifecycle.LiveData

class FavUserRepository (private val userDAO: FavUserDAO) {
    val allFavUser: LiveData<List<FavUser>> = userDAO.getAll()

    suspend fun insert(user: FavUser) {
        userDAO.add(user)
    }
}