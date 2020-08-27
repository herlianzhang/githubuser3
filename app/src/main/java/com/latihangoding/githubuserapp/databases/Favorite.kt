package com.latihangoding.githubuserapp.databases

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class Favorite(
    @PrimaryKey val username: String,
    @ColumnInfo(name = "id") val id: Int?,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String?,
    @ColumnInfo(name = "date_added") val date: Long = System.currentTimeMillis()
)