package com.latihangoding.githubuserapp.databases

import android.content.ContentValues
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class Favorite(
    @PrimaryKey val username: String,
    @ColumnInfo(name = "id") val id: Int?,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String?,
    @ColumnInfo(name = "date_added") val date: Long = System.currentTimeMillis()
) {
    companion object {
        const val _ID = "_id"
        const val USERNAME = "username"
        const val AVATAR_URL = "avatar_url"
        const val DATE_ADDED = "date_added"

        fun fromContentValues(contentValues: ContentValues): Favorite =
            Favorite(
                contentValues.getAsString(USERNAME),
                contentValues.getAsInteger(_ID),
                contentValues.getAsString(AVATAR_URL),
                contentValues.getAsLong(DATE_ADDED)
            )
    }
}