package com.latihangoding.githubuserapp.databases

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_table ORDER BY date_added DESC")
    fun getFavorites(): LiveData<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite): Long

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("SELECT COUNT(username) FROM favorite_table WHERE username = :username")
    suspend fun filter(username: String): Int
}