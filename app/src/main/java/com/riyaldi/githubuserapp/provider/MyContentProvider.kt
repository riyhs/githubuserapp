package com.riyaldi.githubuserapp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.riyaldi.githubuserapp.db.FavUserDAO
import com.riyaldi.githubuserapp.db.FavUserDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import java.lang.UnsupportedOperationException

@InternalCoroutinesApi
class MyContentProvider : ContentProvider() {

    companion object {
        private const val USER = 1
        private const val AUTHORITY = "com.riyaldi.githubuserapp.provider"
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply{
            addURI(AUTHORITY, "fav_user_table", USER)
        }
    }

    @InternalCoroutinesApi
    private val favUserDao: FavUserDAO by lazy {
        FavUserDatabase.getInstance(requireNotNull(context)).favUserDAO()
    }


    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException()
    }

    override fun getType(uri: Uri): String? {
        throw UnsupportedOperationException()
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException()
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)){
            USER -> favUserDao.cursorGetAll()
            else -> null
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        throw UnsupportedOperationException()
    }
}
