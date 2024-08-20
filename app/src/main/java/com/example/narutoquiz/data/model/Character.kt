package com.example.narutoquiz.data.model


import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("debut")
    val debut: Debut,
    @SerializedName("family")
    val family: Family,
    @SerializedName("id")
    val id: Int,
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("jutsu")
    val jutsu: List<String>,
    @SerializedName("name")
    val name: String,
    @SerializedName("natureType")
    val natureType: List<String>,
    @SerializedName("personal")
    val personal: Personal,
    @SerializedName("rank")
    val rank: Rank,
    @SerializedName("tools")
    val tools: List<String>,
    @SerializedName("uniqueTraits")
    val uniqueTraits: List<String>,
    @SerializedName("voiceActors")
    val voiceActors: VoiceActors
)