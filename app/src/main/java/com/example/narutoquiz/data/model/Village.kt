package com.example.narutoquiz.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Village(
@JsonProperty("villages")
val village: List<Character>,
@JsonProperty("currentPage")
val currentPage: Int,
@JsonProperty("pageSize")
val pageSize: Int,
@JsonProperty("total")
val total: Int
)
