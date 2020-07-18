package com.riyaldi.githubuserapp.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.riyaldi.githubuserapp.db.FavUser
import com.riyaldi.githubuserapp.db.FavUserDAO
import com.riyaldi.githubuserapp.db.FavUserDatabase
import com.riyaldi.githubuserapp.db.FavUserRepository
import kotlinx.coroutines.*

@InternalCoroutinesApi
class FavUserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FavUserRepository
    private val favUserDAO: FavUserDAO = FavUserDatabase.getInstance(application).favUserDAO()

    private var _favUser : LiveData<List<FavUser>>

    val favUser: LiveData<List<FavUser>>
        get() = _favUser

    private var vmJob = Job()
    private var uiScope = CoroutineScope(Dispatchers.IO + vmJob)

    init {
        repository = FavUserRepository(favUserDAO)
        _favUser = repository.allFavUser
    }

    fun addFavUser(name: String,
                   username: String,
                   company: String,
                   location: String,
                   bio: String,
                   repositories: String,
                   followers: String,
                   following: String,
                   followersUrl: String,
                   followingUrl: String,
                   photoUrl: String) {
        uiScope.launch {
            repository.insert(FavUser(
                name =  name,
                username = username,
                company = company,
                location = location,
                bio = bio,
                repositories = repositories,
                followers = followers,
                following = following,
                followersUrl = followersUrl,
                followingUrl = followingUrl,
                photoUrl = photoUrl))
        }
    }

    fun delete(
            name: String,
            username: String,
            company: String,
            location: String,
            bio: String,
            repositories: String,
            followers: String,
            following: String,
            followersUrl: String,
            followingUrl: String,
            photoUrl: String) {
        uiScope.launch {
            repository.delete(
                FavUser(
                    name = name,
                    username = username,
                    company = company,
                    location = location,
                    bio = bio,
                    repositories = repositories,
                    followers = followers,
                    following = following,
                    followersUrl = followersUrl,
                    followingUrl = followingUrl,
                    photoUrl = photoUrl
                )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        vmJob.cancel()
    }
}