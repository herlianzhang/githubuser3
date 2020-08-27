package com.latihangoding.githubuserapp.repository

import com.latihangoding.githubuserapp.databases.Favorite
import com.latihangoding.githubuserapp.databases.FavoriteDao

class FavoriteRepository(private val favoriteDao: FavoriteDao) {
    val favorites = favoriteDao.getFavorites()

    suspend fun setFavorite(favorite: Favorite) {
        if (getIsFavorite(favorite.username)) favoriteDao.deleteFavorite(favorite)
        else favoriteDao.insertFavorite(favorite)
    }

    suspend fun getIsFavorite(username: String) : Boolean = favoriteDao.filter(username) == 1
}