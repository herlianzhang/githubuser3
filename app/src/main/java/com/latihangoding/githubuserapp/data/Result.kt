package com.latihangoding.githubuserapp.data

/**
 * A generic class that holds a value with its loading status.
 *
 * Result is usually created by the Repository classes where they return
 * `LiveData<Result<T>>` to pass back the latest data to the UI with its fetch status.
 */

sealed class Result<out T> {
    class LOADING<T> : Result<T>()
    data class ERROR<T>(val message: String) : Result<T>()
    data class SUCCESS<T>(val data: T) : Result<T>()
}