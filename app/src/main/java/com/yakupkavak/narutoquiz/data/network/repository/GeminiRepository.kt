package com.yakupkavak.narutoquiz.data.network.repository

import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.yakupkavak.narutoquiz.data.network.util.Resource

class GeminiRepository {
    suspend fun getHint(character: String): Resource<String> {
        try {
            val generativeModel = Firebase.ai(backend = GenerativeBackend.googleAI())
                .generativeModel("gemini-3.5-flash-lite")
            val prompt =
                "Provide information about $character to help the user in the game of guessing" +
                        " who the character is. Don't use $character's name. Write a single sentence of no more than 40 words."
            val response = generativeModel.generateContent(prompt)
            return Resource.success(response.text)
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.error(null)
        }
    }
}
