package com.latihangoding.githubuserapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.latihangoding.githubuserapp.databases.Favorite
import com.latihangoding.githubuserapp.databases.FavoriteDatabase
import com.latihangoding.githubuserapp.models.ProfileModel
import com.latihangoding.githubuserapp.network.GithubApi
import com.latihangoding.githubuserapp.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FavoriteRepository

    private val _isFavorite = MutableLiveData(false)
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    private val _profileModel = MutableLiveData<ProfileModel>()
    val profileModel: LiveData<ProfileModel>
        get() = _profileModel

    private val _isBackClicked = MutableLiveData<Boolean>()
    val isBackClicked: LiveData<Boolean>
        get() = _isBackClicked

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean>
        get() = _isError

    init {
        val favoriteDao = FavoriteDatabase.getInstance(application).favoriteDao()
        repository = FavoriteRepository(favoriteDao)
        _isBackClicked.postValue(false)
        _isError.postValue(false)
    }

    fun getProfile(username: String) {
        viewModelScope.launch {
            val profileDeffered = GithubApi.retrofitService.getProfileAsync(username)
            try {
                val result = withContext(Dispatchers.IO) { profileDeffered.await() }
                _profileModel.postValue(result)
            } catch (e: Exception) {
                _isError.postValue(true)
                Log.e("error", "username: $username cause: $e")
            }
        }
    }

    fun checkFavorite() {
        viewModelScope.launch {
            _profileModel.value?.login?.let {
                _isFavorite.postValue(repository.getIsFavorite(it))
            }
        }
    }

    fun setFavorite() {
        viewModelScope.launch {
            _profileModel.value?.let {
                repository.setFavorite(Favorite(it.login, it.id, it.avatarUrl))
                checkFavorite()
            }
        }
    }

    fun backClicked() {
        _isBackClicked.postValue(true)
    }

    fun doneShowErrorMessage() {
        _isError.postValue(false)
    }
}