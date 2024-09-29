package com.naruto.narutoquiz.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Characters(
    @JsonProperty("characters")
    val characters: List<Character>?,
    @JsonProperty("currentPage")
    val currentPage: Int?,
    @JsonProperty("pageSize")
    val pageSize: Int?,
    @JsonProperty("total")
    val total: Int?,
)