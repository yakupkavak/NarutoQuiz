package com.naruto.narutoquiz.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TailedBeast(
    @JsonProperty("tailed-beasts")
    val tailedBeasts: List<Character>?,
    @JsonProperty("currentPage")
    val currentPage: Int?,
    @JsonProperty("pageSize")
    val pageSize: Int?,
    @JsonProperty("total")
    val total: Int?,
)
