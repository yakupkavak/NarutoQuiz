package com.example.narutoquiz.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Character(
    @JsonProperty("debut")
    val debut: Debut?,
    @JsonProperty("family")
    val family: Family?,
    @JsonProperty("id")
    val id: Int?,
    @JsonProperty("images")
    val images: List<String>?,
    @JsonProperty("jutsu")
    val jutsu: List<String>?,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("natureType")
    val natureType: List<String>?,
    @JsonProperty("personal")
    val personal: Personal?,
    @JsonProperty("rank")
    val rank: Rank?,
    @JsonProperty("tools")
    val tools: List<String>?,
    @JsonProperty("uniqueTraits")
    val uniqueTraits: List<String>?,
    @JsonProperty("voiceActors")
    val voiceActors: VoiceActors?
)