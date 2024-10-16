package com.naruto.narutoquiz.data.network.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Rank(
    @JsonProperty("ninjaRank")
    val ninjaRank: com.naruto.narutoquiz.data.network.model.NinjaRank?,
    @JsonProperty("ninjaRegistration")
    val ninjaRegistration: String?
)