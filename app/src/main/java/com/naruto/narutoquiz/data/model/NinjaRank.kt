package com.naruto.narutoquiz.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class NinjaRank(
    @JsonProperty("Blank Period")
    val blankPeriod: String?,
    @JsonProperty("Gaiden")
    val gaiden: String?,
    @JsonProperty("Part I")
    val partOne: String?,
    @JsonProperty("Part II")
    val partTwo: String?
)