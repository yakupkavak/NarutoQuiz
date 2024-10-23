package com.naruto.narutoquiz.data.network.repository

import android.content.Context
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.naruto.narutoquiz.R
import com.naruto.narutoquiz.data.network.util.ServiceCountConst.REMOTE_TAG

class RemoteConfigRepository(val context:Context) {

    fun observeRemoteConfig(){
        FirebaseApp.initializeApp(context)
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(REMOTE_TAG,"Remote config success")
                    val data = remoteConfig.getString("about_game")
                } else {
                    Log.d(REMOTE_TAG,"Remote config error")
                }
            }

        remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                Log.e(REMOTE_TAG, "Updated keys: " + configUpdate.updatedKeys)

                if (configUpdate.updatedKeys.contains("color")) {
                    remoteConfig.activate().addOnCompleteListener {

                    }
                }
            }

            override fun onError(error: FirebaseRemoteConfigException) {
                Log.w(REMOTE_TAG, "Config update error with code: " + error.code, error)
            }
        })
    }
}