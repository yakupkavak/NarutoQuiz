package com.example.narutoquiz.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class NinjaRank(
    @JsonProperty("Blank Period")
    val blankPeriod: String?,
    @JsonProperty("Gaiden")
    val gaiden: String?,
    @JsonProperty("Part I")
    val partI: String?,
    @JsonProperty("Part II")
    val partII: String?
)