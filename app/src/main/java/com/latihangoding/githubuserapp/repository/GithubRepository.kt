package com.latihangoding.githubuserapp.repository

import androidx.lifecycle.liveData
import com.latihangoding.githubuserapp.data.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GithubRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {
    fun getUserData(username: String) = liveData(Dispatchers.IO) {
        emit(remoteDataSource.fetchSearchData(username))
    }
}