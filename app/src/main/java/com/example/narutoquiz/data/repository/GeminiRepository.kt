package com.example.narutoquiz.data.repository

import com.example.narutoquiz.BuildConfig
import com.example.narutoquiz.data.util.Resource
import com.google.ai.client.generativeai.GenerativeModel

class GeminiRepository {
    suspend fun getHint(character: String): Resource<String> {
        try {
            val generativeModel = GenerativeModel(
                modelName = "gemini-1.5-flash", apiKey = BuildConfig.GEMINI_API
            )
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