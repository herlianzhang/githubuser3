package com.latihangoding.consumer.viewmodels

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.latihangoding.consumer.models.FavoriteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val contentResolver = application.applicationContext.contentResolver
    private val _favorites = MutableLiveData<List<FavoriteModel>>()
    val favorites: LiveData<List<FavoriteModel>>
        get() = _favorites

    init {
        fetchFavorites()
    }

    private fun fetchFavorites() {
        viewModelScope.launch {
            val cursor = withContext(Dispatchers.IO) {
                contentResolver.query(
                    Uri.parse("content://com.latihangoding.githubuserapp.provider/favorite_table/1"),
                    null,
                    null,
                    null,
                    null
                )
            }
            FavoriteModel.mapToFavorites(cursor)?.let { mFavorites ->
                Log.d("provider", "fetchFavorites: $mFavorites")
                _favorites.postValue(mFavorites)

            }
        }
    }

    fun deleteFavorite(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val uri =
                Uri.parse("content://com.latihangoding.githubuserapp.provider/favorite_table/1/$username")
            contentResolver.delete(uri, null, null)
            fetchFavorites()
        }
    }
}