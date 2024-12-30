package com.yakupkavak.narutoquiz.data.network.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Characters(
    @JsonProperty("characters")
    val characters: List<com.yakupkavak.narutoquiz.data.network.model.Character>?,
    @JsonProperty("currentPage")
    val currentPage: Int?,
    @JsonProperty("pageSize")
    val pageSize: Int?,
    @JsonProperty("total")
    val total: Int?,
)