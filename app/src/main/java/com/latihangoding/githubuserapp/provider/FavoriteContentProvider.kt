package com.latihangoding.githubuserapp.provider

import android.content.ContentProvider
import android.content.ContentUris
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
        addURI(Values.AUTHORITY, Values.TABLE_NAME, 1)
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted = favoriteDao.deleteById(ContentUris.parseId(uri))

        context?.contentResolver?.notifyChange(uri, null)

        return deleted
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun onCreate(): Boolean {
        Log.d("WOII", "onCreate: masuk")
        context?.let { ctx ->
            favoriteDao = FavoriteDatabase.getInstance(ctx).favoriteDao()
        }
        return false
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        Log.d("WOII", "query: masuk\n$uri == ${sUriMatcher.match(uri)}")
        return favoriteDao.getAllGetCursor()
//        return when (sUriMatcher.match(uri)) {
//            1 -> {
//                Log.d("WOII", "query: masuk ke no 1")
//                favoriteDao.getAllGetCursor()
//            }
//            else -> {
//                Log.e("content provider", "query 1: Error Guys", )
//                null
//            }
//        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}
