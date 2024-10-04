package com.naruto.narutoquiz.data.network.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TailedBeast(
    @JsonProperty("tailed-beasts")
    val tailedBeasts: List<com.naruto.narutoquiz.data.network.model.Character>?,
    @JsonProperty("currentPage")
    val currentPage: Int?,
    @JsonProperty("pageSize")
    val pageSize: Int?,
    @JsonProperty("total")
    val total: Int?,
)
