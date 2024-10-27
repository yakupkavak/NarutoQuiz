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
import com.naruto.narutoquiz.data.network.util.ServiceCountConst.REMOTE_ABOUT_GAME
import com.naruto.narutoquiz.data.network.util.ServiceCountConst.REMOTE_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

class RemoteConfigRepository(val context: Context) {

    suspend fun observeAboutGame(): Resource<String> {
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
                                        REMOTE_ABOUT_GAME
                                    )
                                )
                            )
                        } else {
                            Log.d(REMOTE_TAG, "Remote config error")
                            continuation.resume(Resource.error(null))
                            //TODO GIVE ERROR
                        }
                    }
            }
        }
    }
}