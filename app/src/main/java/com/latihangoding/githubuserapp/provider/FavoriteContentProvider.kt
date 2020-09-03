package com.latihangoding.githubuserapp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.latihangoding.githubuserapp.databases.FavoriteDao
import com.latihangoding.githubuserapp.databases.FavoriteDatabase
import com.latihangoding.githubuserapp.helpers.Values

class FavoriteContentProvider : ContentProvider() {

    private lateinit var favoriteDao: FavoriteDao
    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(Values.AUTHORITY, "${Values.TABLE_NAME}/#", 1)
        addURI(Values.AUTHORITY, "${Values.TABLE_NAME}/#/*", 2)
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return when (sUriMatcher.match(uri)) {
            2 -> {
                val deleted = favoriteDao.deleteByUsername(uri.lastPathSegment.toString())
                context?.contentResolver?.notifyChange(uri, null)

                Log.d("provider", "delete: $deleted username: ${uri.lastPathSegment.toString()}")
                deleted
            }
            else -> {
                Log.e("provider", "delete: failed uri:${uri}, username: ${uri.lastPathSegment.toString()}")
                0
            }
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun onCreate(): Boolean {
        context?.let { ctx ->
            favoriteDao = FavoriteDatabase.getInstance(ctx).favoriteDao()
        }
        return false
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            1 -> {
                Log.d("provider", "query: success")
                favoriteDao.getAllGetCursor()
            }
            else -> {
                Log.e("provider", "query: failed")
                null
            }
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}
