package com.naruto.narutoquiz.data.network.repository

import android.content.Context
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.naruto.narutoquiz.R
import com.naruto.narutoquiz.data.network.util.Resource
import com.naruto.narutoquiz.data.network.util.ServiceCountConst.REMOTE_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

class RemoteConfigRepository(val context: Context) {

    suspend fun observeString(keyValue: String): Resource<String> {
        FirebaseApp.initializeApp(context)
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        return withContext(Dispatchers.IO) {
            return@withContext suspendCancellableCoroutine<Resource<String>> { continuation ->
                remoteConfig.fetchAndActivate()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(REMOTE_TAG, "Remote config success")
                            continuation.resume(
                                Resource.success(
                                    remoteConfig.getString(
                                        keyValue
                                    )
                                )
                            )
                        } else {
                            Log.d(REMOTE_TAG, "Remote config error")
                            continuation.resume(Resource.error(null))
                            if (continuation.isActive) {
                                continuation.resume(Resource.error(null))
                            }
                            //TODO GIVE ERROR
                        }
                    }
            }
        }
    }

    suspend fun observeInt(keyValue: String): Resource<Int> {
        FirebaseApp.initializeApp(context)
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        return withContext(Dispatchers.IO) {
            return@withContext suspendCancellableCoroutine<Resource<Int>> { continuation ->
                remoteConfig.fetchAndActivate()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(REMOTE_TAG, "Remote config success")
                            val data = remoteConfig.getLong(keyValue).toInt()
                            continuation.resume(
                                Resource.success(data)
                            )
                        } else {
                            Log.d(REMOTE_TAG, "Remote config error")
                            continuation.resume(Resource.error(null))
                            //TODO GIVE ERROR
                        }
                    }.addOnFailureListener { exception ->
                        if (continuation.isActive) {
                            continuation.resume(Resource.error(error = exception))
                        }
                    }
            }
        }
    }
}