package com.example.narutoquiz.data.model


import com.google.gson.annotations.SerializedName

data class Characters(
    @SerializedName("characters")
    val characters: List<Character>,
    @SerializedName("currentPage")
    val currentPage: Int,
    @SerializedName("pageSize")
    val pageSize: Int,
    @SerializedName("total")
    val total: Int
)