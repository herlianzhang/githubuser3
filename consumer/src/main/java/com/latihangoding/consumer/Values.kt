package com.latihangoding.consumer

import android.net.Uri

object Values {
    const val MAIN_URL = "https://api.github.com/"
    const val TOKEN = "TOKEN 54ae67f393abc5888d520fb326dd6d392b89bb20"

    // content provider
    const val AUTHORITY = "com.latihangoding.githubuserapp.provider"
    const val TABLE_PATH = "com.latihangoding.githubuserapp.provider/favorite_table"
    val FAVORITE_URI = Uri.parse("content://com.latihangoding.githubuserapp.provider/favorite_table/1")
}