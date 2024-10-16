package com.naruto.narutoquiz.data.network.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Character(
    @JsonProperty("debut")
    val debut: com.naruto.narutoquiz.data.network.model.Debut?,
    @JsonProperty("family")
    val family: com.naruto.narutoquiz.data.network.model.Family?,
    @JsonProperty("id")
    val id: Int?,
    @JsonProperty("images")
    val images: List<String>?,
    @JsonProperty("jutsu")
    val jutsu: List<String>?,
    @JsonProperty("name")
    val name: String?,
    @JsonProperty("natureType")
    val natureType: List<String>?,
    @JsonProperty("personal")
    val personal: com.naruto.narutoquiz.data.network.model.Personal?,
    @JsonProperty("rank")
    val rank: com.naruto.narutoquiz.data.network.model.Rank?,
    @JsonProperty("tools")
    val tools: List<String>?,
    @JsonProperty("uniqueTraits")
    val uniqueTraits: List<String>?,
    @JsonProperty("voiceActors")
    val voiceActors: com.naruto.narutoquiz.data.network.model.VoiceActors?
)