package com.latihangoding.githubuserapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.latihangoding.githubuserapp.databases.Favorite
import com.latihangoding.githubuserapp.databases.FavoriteDatabase
import com.latihangoding.githubuserapp.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FavoriteRepository

    val favorites: LiveData<List<Favorite>>

    init {
        val favoriteDao = FavoriteDatabase.getInstance(application).favoriteDao()
        repository = FavoriteRepository(favoriteDao)
        favorites = repository.favorites
    }

    fun deleteFavorite(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setFavorite(favorite)
        }
    }
}