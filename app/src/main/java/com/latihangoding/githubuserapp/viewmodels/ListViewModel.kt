package com.latihangoding.githubuserapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.latihangoding.githubuserapp.databases.Favorite
import com.latihangoding.githubuserapp.databases.FavoriteDatabase
import com.latihangoding.githubuserapp.models.ItemModel
import com.latihangoding.githubuserapp.network.GithubApi
import com.latihangoding.githubuserapp.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FavoriteRepository

    val favorites: LiveData<List<Favorite>>

    private val _usersModel = MutableLiveData<List<ItemModel>>()
    val usersModel: LiveData<List<ItemModel>>
        get() = _usersModel

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isShowNoData = MutableLiveData<Boolean>()
    val isShowNoData: LiveData<Boolean>
        get() = _isShowNoData

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean>
        get() = _isError

    init {
        val favoriteDao = FavoriteDatabase.getInstance(application).favoriteDao()
        repository = FavoriteRepository(favoriteDao)
        favorites = repository.favorites
        _isShowNoData.postValue(true)
        _isError.postValue(false)
    }

    fun getSearch(username: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.postValue(true)
            if (_isShowNoData.value == true) {
                _isShowNoData.postValue(false)
            }
            val getSearchDeferred = GithubApi.retrofitService.getSearchDataAsync(username)

            try {
                val result = getSearchDeferred.await()
                if (result.items.isEmpty()) {
                    _isShowNoData.postValue(true)
                }
                _usersModel.postValue(result.items)
            } catch (e: Exception) {
                _isError.postValue(true)
                Log.e("error", "username: $username cause: $e")
            }
            _isLoading.postValue(false)
            checkFavorite()
        }
    }

    fun getFollowers(username: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.postValue(true)
            if (_isShowNoData.value == true) {
                _isShowNoData.postValue(false)
            }
            val getSearchDeferred = GithubApi.retrofitService.getFollowersAsync(username)

            try {
                val result = getSearchDeferred.await().map { it.copy(isClickable = false) }
                if (result.isEmpty()) {
                    _isShowNoData.postValue(true)
                }
                _usersModel.postValue(result)
            } catch (e: Exception) {
                _isError.postValue(true)
                Log.e("error", "username: $username cause: $e")
            }
            _isLoading.postValue(false)
            checkFavorite()
        }
    }

    fun getFollowing(username: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.postValue(true)
            if (_isShowNoData.value == true) {
                _isShowNoData.postValue(false)
            }
            val getSearchDeferred = GithubApi.retrofitService.getFollowingAsync(username)
            try {
                val result = getSearchDeferred.await().map { it.copy(isClickable = false) }
                if (result.isEmpty()) {
                    _isShowNoData.postValue(true)
                }
                _usersModel.postValue(result)
            } catch (e: Exception) {
                _isError.postValue(true)
                Log.e("error", "username: $username cause: $e")
            }
            _isLoading.postValue(false)
            checkFavorite()
        }
    }

    fun setFavorite(item: ItemModel) {
        val favorite = Favorite(item.login, item.id, item.avatarUrl)
        viewModelScope.launch(Dispatchers.IO) {
            repository.setFavorite(favorite)
            checkFavorite()
        }
    }

    fun doneShowErrorMessage() {
        _isError.postValue(false)
    }

    fun checkFavorite() {
        viewModelScope.launch(Dispatchers.Main) {
            usersModel.value?.let { users ->
                val mUsers = mutableListOf<ItemModel>()
                for (user in users) {
                    val mUser = user.copy(isFavorite = repository.getIsFavorite(user.login))
                    mUsers.add(mUser)
                }
                _usersModel.postValue(mUsers)
            }
        }
    }
}