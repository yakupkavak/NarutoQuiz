package com.naruto.narutoquiz.data.network.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Weight(
    @JsonProperty("Part I")
    val partOne: String?,
    @JsonProperty("Part II")
    val partTwo: String?
)