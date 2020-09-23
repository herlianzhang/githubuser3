package com.latihangoding.githubuserapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.latihangoding.githubuserapp.api.BaseDataSource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

/**
 * The database serves as the single source of truth.
 * Therefore UI can receive data updates from database only.
 * Function notify UI about:
 * [Result.SUCCESS] - with data from database
 * [Result.ERROR] - if error has occurred from any source
 * [Result.LOADING]
 */

@Singleton
class DataSource @Inject constructor(): BaseDataSource() {
    fun <T> resultTestLiveData(networkCall: suspend () -> retrofit2.Response<T>): LiveData<Result<T>> =
        liveData(Dispatchers.IO) {
            emit(Result.LOADING<T>())
            val response = getResult { networkCall.invoke() }
            if (response is Result.SUCCESS) {
                emit(Result.SUCCESS(response.data))
            } else if (response is Result.ERROR) {
                emit(Result.ERROR<T>(response.message))
            }
        }

    fun <T, A> resultLiveData(
        databaseQuery: () -> LiveData<T>,
        networkCall: suspend () -> retrofit2.Response<A>,
        saveCallResult: suspend (A) -> Unit
    ): LiveData<Result<T>> =
        liveData(Dispatchers.IO) {
            emit(Result.LOADING<T>())
            val source: LiveData<Result<T>> = databaseQuery.invoke().map { Result.SUCCESS(it) }
            emitSource(source)

            val responseStatus = getResult{ networkCall.invoke() }
            if (responseStatus is Result.SUCCESS) {
                saveCallResult(responseStatus.data)
            } else if (responseStatus is Result.ERROR) {
                emit(Result.ERROR<T>(responseStatus.message))
                emitSource(source)
            }
        }
}
