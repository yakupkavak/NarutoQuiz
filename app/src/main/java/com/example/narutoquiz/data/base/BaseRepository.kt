package com.example.narutoquiz.data.base

import com.example.narutoquiz.data.util.Resource
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.coroutines.resume

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

    suspend inline fun <T> firebaseJob(crossinline task: () -> Task<T>): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                suspendCancellableCoroutine<Resource<T>> { continuation ->
                    task().addOnSuccessListener { result ->
                        continuation.resume(Resource.success(result))
                    }.addOnFailureListener {
                        continuation.resume(Resource.error(null))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Resource.error(null)
            }
        }
    }
}