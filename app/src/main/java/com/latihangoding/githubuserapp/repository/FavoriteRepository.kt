package com.latihangoding.githubuserapp.repository

import com.latihangoding.githubuserapp.databases.Favorite
import com.latihangoding.githubuserapp.databases.FavoriteDao

class FavoriteRepository(private val favoriteDao: FavoriteDao) {
    val favorites = favoriteDao.getFavorites()

    suspend fun setFavorite(favorite: Favorite) {
        val find = favoriteDao.filter(favorite.username)

        if (find == 0) {
            favoriteDao.insertFavorite(favorite)
        } else {
            favoriteDao.deleteFavorite(favorite)
        }
    }
}