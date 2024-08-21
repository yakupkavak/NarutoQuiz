package com.example.narutoquiz.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Rank(
    @JsonProperty("ninjaRank")
    val ninjaRank: NinjaRank?,
    @JsonProperty("ninjaRegistration")
    val ninjaRegistration: String?
)