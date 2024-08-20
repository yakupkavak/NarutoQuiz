package com.example.narutoquiz.data.model


import com.google.gson.annotations.SerializedName

data class Debut(
    @SerializedName("anime")
    val anime: String,
    @SerializedName("appearsIn")
    val appearsIn: String,
    @SerializedName("game")
    val game: String,
    @SerializedName("manga")
    val manga: String,
    @SerializedName("movie")
    val movie: String,
    @SerializedName("novel")
    val novel: String,
    @SerializedName("ova")
    val ova: String
)