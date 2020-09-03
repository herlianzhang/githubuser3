package com.latihangoding.consumer.models

import android.database.Cursor

data class FavoriteModel(
    val username: String,
    val id: Int?,
    val avatarUrl: String?,
    val date: Long
) {
    companion object {
        private const val USERNAME = "username"
        private const val ID = "id"
        private const val AVATAR_URL = "avatar_url"
        private const val DATE_ADDED = "date_added"
        fun mapToFavorites(cursor: Cursor?): List<FavoriteModel>? {
            val favorites = mutableListOf<FavoriteModel>()
            cursor?.apply {
                while (moveToNext()) {
                    val mUsername = getString(getColumnIndexOrThrow(USERNAME))
                    val mId = getInt(getColumnIndexOrThrow(ID))
                    val mAvatarUrl = getString(getColumnIndexOrThrow(AVATAR_URL))
                    val mDate = getLong(getColumnIndexOrThrow(DATE_ADDED))
                    favorites.add(FavoriteModel(mUsername, mId, mAvatarUrl, mDate))
                }
            }
            return favorites
        }
    }
}