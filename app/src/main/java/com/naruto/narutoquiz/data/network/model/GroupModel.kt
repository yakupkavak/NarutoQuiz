package com.naruto.narutoquiz.data.network.model

import com.fasterxml.jackson.annotation.JsonProperty

data class GroupModel(
    @JsonProperty("characters")
    val characters: List<Int>?,
    @JsonProperty("id")
    val id: Int?,
    @JsonProperty("name")
    val name: String?,
)