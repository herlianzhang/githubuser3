package com.latihangoding.githubuserapp.api

import com.latihangoding.githubuserapp.models.ItemModel
import com.latihangoding.githubuserapp.models.ProfileModel
import com.latihangoding.githubuserapp.models.SearchModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Github REST API access points
 */
interface ApiService {
    @GET("search/users")
    suspend fun getSearchDataAsync(
        @Query("q") username: String,
    ): Response<SearchModel>

    @GET("users/{username}")
    suspend fun getProfileAsync(
        @Path("username") username: String,
    ): Response<ProfileModel>

    @GET("users/{username}/followers")
    suspend fun getFollowersAsync(
        @Path("username") username: String,
    ): Response<List<ItemModel>>

    @GET("users/{username}/following")
    suspend fun getFollowingAsync(
        @Path("username") username: String,
    ): Response<List<ItemModel>>
}