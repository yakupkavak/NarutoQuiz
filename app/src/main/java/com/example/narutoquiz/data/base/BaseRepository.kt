package com.example.narutoquiz.data.base

import com.example.narutoquiz.data.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class BaseRepository {

    suspend inline fun <T> fetchData(crossinline call: suspend () -> Response<T>): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = call()
                if (response.isSuccessful) {
                    response.body()?.let {
                        return@withContext Resource.success(it)
                    } ?: Resource.error(null)
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "unknown error"
                    val errorCode = response.code()
                    println("error ->$errorMessage$errorCode")
                    return@withContext Resource.error(null)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext Resource.error(null)
            }
        }
    }
}