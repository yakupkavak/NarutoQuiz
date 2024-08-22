package com.example.narutoquiz.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class GroupModel(
    @JsonProperty("characters")
    val characters: List<Int>,
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("name")
    val name: String
)