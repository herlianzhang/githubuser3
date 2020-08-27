package com.latihangoding.githubuserapp.databases

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_table ORDER BY date_added")
    fun getFavorites(): LiveData<List<Favorite>>

    @Insert
    suspend fun insertFavorite(favorite: Favorite): Long

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("SELECT COUNT(username) FROM favorite_table WHERE username = :username")
    suspend fun filter(username: String): Int
}