package com.latihangoding.githubuserapp.api

import com.latihangoding.githubuserapp.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * Abstract Base Data source class with error handling
 */
abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend() -> retrofit2.Response<T>): Result<T> {
        try {

            val response = withContext(Dispatchers.IO) { call() }
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Result.SUCCESS(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Result<T> {
        Timber.e(message)
        return Result.ERROR("Network call has failed for a following reason: $message")
    }
}