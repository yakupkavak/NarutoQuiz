package com.example.narutoquiz.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Age(
    @JsonProperty("Academy Graduate")
    val academyGraduate: String?,
    @JsonProperty("Blank Period")
    val blankPeriod: String?,
    @JsonProperty("Boruto Manga")
    val borutoManga: String?,
    @JsonProperty("Boruto Movie")
    val borutoMovie: String?,
    @JsonProperty("Chunin Promotion")
    val chuninPromotion: String?,
    @JsonProperty("Part I")
    val partI: String?,
    @JsonProperty("Part II")
    val partII: String?
)
