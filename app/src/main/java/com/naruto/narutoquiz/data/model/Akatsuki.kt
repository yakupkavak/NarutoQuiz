package com.naruto.narutoquiz.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Akatsuki(
    @JsonProperty("akatsuki")
    val akatsuki: List<Character>?,
    @JsonProperty("currentPage")
    val currentPage: Int?,
    @JsonProperty("pageSize")
    val pageSize: Int?,
    @JsonProperty("total")
    val total: Int?,
)
