package com.latihangoding.githubuserapp.repository

import com.latihangoding.githubuserapp.api.ApiService
import com.latihangoding.githubuserapp.data.DataSource
import javax.inject.Inject

class GithubRepository @Inject constructor(private val wkwkwk: DataSource, private val apiService: ApiService) {
    fun getUserData(username: String) =
        wkwkwk.resultTestLiveData { apiService.getSearchDataAsync(username) }

}