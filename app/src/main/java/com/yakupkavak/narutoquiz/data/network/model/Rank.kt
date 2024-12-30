package com.yakupkavak.narutoquiz.data.network.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Rank(
    @JsonProperty("ninjaRank")
    val ninjaRank: com.yakupkavak.narutoquiz.data.network.model.NinjaRank?,
    @JsonProperty("ninjaRegistration")
    val ninjaRegistration: String?
)