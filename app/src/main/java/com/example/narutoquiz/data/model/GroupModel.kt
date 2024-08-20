package com.example.narutoquiz.data.model


import com.google.gson.annotations.SerializedName

data class GroupModel(
    @SerializedName("characters")
    val characters: List<Int>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)