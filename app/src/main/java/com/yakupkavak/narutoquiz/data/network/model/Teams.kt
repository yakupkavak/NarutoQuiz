package com.yakupkavak.narutoquiz.data.network.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Teams(
    @JsonProperty("teams")
    val teams: List<com.yakupkavak.narutoquiz.data.network.model.GroupModel>?,
    @JsonProperty("currentPage")
    val currentPage: Int?,
    @JsonProperty("pageSize")
    val pageSize: Int?,
    @JsonProperty("total")
    val total: Int?,
)
