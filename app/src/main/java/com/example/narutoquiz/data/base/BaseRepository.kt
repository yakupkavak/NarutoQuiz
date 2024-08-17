package com.example.narutoquiz.data.base

import com.example.narutoquiz.data.util.Resource
import retrofit2.Response

abstract class BaseRepository {

    suspend inline fun <T> fetchData(crossinline call: suspend () -> Response<T>): Resource<T> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error(null)
            } else {
                return Resource.error(null)
            }
        } catch (e: Exception) {
            return Resource.error(null)
        }
    }
}