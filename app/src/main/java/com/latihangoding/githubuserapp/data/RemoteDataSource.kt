package com.latihangoding.githubuserapp.data

import com.latihangoding.githubuserapp.api.ApiService
import com.latihangoding.githubuserapp.api.BaseDataSource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val service: ApiService) : BaseDataSource() {
    suspend fun fetchSearchData(username: String) = service.getSearchDataAsync(username)
}