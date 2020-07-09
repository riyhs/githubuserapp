package com.riyaldi.githubuserapp.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.riyaldi.githubuserapp.db.FavUser
import com.riyaldi.githubuserapp.db.FavUserDAO
import com.riyaldi.githubuserapp.db.FavUserDatabase
import com.riyaldi.githubuserapp.db.FavUserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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

    fun addFavUser(text: String) {
        uiScope.launch {
            repository.insert(FavUser(0, text))
        }
    }

    override fun onCleared() {
        super.onCleared()
        vmJob.cancel()
    }
}