package com.example.narutoquiz.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Height(
    @JsonProperty("Blank Period")
    val blankPeriod: String?,
    @JsonProperty("Boruto Manga")
    val borutoManga: String?,
    @JsonProperty("Boruto Movie")
    val borutoMovie: String?,
    @JsonProperty("Gaiden")
    val gaiden: String?,
    @JsonProperty("Part I")
    val partOne: String?,
    @JsonProperty("Part II")
    val partTwo: String?
)
