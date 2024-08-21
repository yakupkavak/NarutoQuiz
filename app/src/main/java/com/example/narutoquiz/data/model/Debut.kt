package com.example.narutoquiz.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Debut(
    @JsonProperty("anime")
    val anime: String?,
    @JsonProperty("appearsIn")
    val appearsIn: String?,
    @JsonProperty("game")
    val game: String?,
    @JsonProperty("manga")
    val manga: String?,
    @JsonProperty("movie")
    val movie: String?,
    @JsonProperty("novel")
    val novel: String?,
    @JsonProperty("ova")
    val ova: String?
)
