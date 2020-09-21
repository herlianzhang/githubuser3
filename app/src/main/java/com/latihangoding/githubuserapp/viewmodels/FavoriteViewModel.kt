package com.latihangoding.githubuserapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.latihangoding.githubuserapp.databases.Favorite
import com.latihangoding.githubuserapp.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(application: Application, private val repository: FavoriteRepository) : AndroidViewModel(application) {
    val favorites: LiveData<List<Favorite>> = repository.favorites

    fun deleteFavorite(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setFavorite(favorite)
        }
    }
}