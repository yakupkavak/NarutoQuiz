package com.yakupkavak.narutoquiz.data.local.repository

import android.content.Context
import androidx.annotation.RawRes
import com.google.gson.GsonBuilder
import com.yakupkavak.narutoquiz.data.network.model.Akatsuki
import com.yakupkavak.narutoquiz.data.network.model.Character

class MockRepository(val context: Context) {

    fun getRandomCharacterFromRaw(@RawRes resId: Int): Character? {
        return try {
            val json = context.resources.openRawResource(resId)
                .bufferedReader().use { it.readText() }

            val gson = GsonBuilder().setLenient().create()
            val response = gson.fromJson(json, Akatsuki::class.java)

            response.akatsuki?.random()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getStringValue(id: Int): String{
        return context.getString(id)
    }
}

data class AkatsukiResponse(
    val akatsuki: List<Character>
)