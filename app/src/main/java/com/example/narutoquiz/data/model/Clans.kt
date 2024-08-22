package com.example.narutoquiz.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Clans(
    @JsonProperty("clans")
    val clans: List<GroupModel>,
    @JsonProperty("currentPage")
    val currentPage: Int,
    @JsonProperty("pageSize")
    val pageSize: Int,
    @JsonProperty("total")
    val total: Int
)