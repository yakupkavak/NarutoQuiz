package com.naruto.narutoquiz.data.network.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Clans(
    @JsonProperty("clans")
    val clans: List<com.naruto.narutoquiz.data.network.model.GroupModel>?,
    @JsonProperty("currentPage")
    val currentPage: Int?,
    @JsonProperty("pageSize")
    val pageSize: Int?,
    @JsonProperty("total")
    val total: Int?,
)