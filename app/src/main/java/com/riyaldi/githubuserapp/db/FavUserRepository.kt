package com.riyaldi.githubuserapp.db

import androidx.lifecycle.LiveData

class FavUserRepository (private val userDAO: FavUserDAO) {
    val allFavUser: LiveData<List<FavUser>> = userDAO.getAll()

    fun delete(user: FavUser) {
        userDAO.delete(user)
    }

    fun insert(user: FavUser) {
        userDAO.add(user)
    }
}