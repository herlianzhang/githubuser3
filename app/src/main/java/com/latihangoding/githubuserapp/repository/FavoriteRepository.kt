package com.latihangoding.githubuserapp.repository

import com.latihangoding.githubuserapp.databases.Favorite
import com.latihangoding.githubuserapp.databases.FavoriteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val favoriteDao: FavoriteDao) {
    val favorites = favoriteDao.getFavorites()

    suspend fun setFavorite(favorite: Favorite) {
        withContext(Dispatchers.IO) {
            if (getIsFavorite(favorite.username)) favoriteDao.deleteFavorite(favorite)
            else favoriteDao.insertFavorite(favorite)
        }
    }

    suspend fun getIsFavorite(username: String) : Boolean = withContext(Dispatchers.IO) { favoriteDao.filter(username) == 1 }
}