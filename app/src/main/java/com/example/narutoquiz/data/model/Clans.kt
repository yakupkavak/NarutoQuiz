package com.example.narutoquiz.data.model


import com.google.gson.annotations.SerializedName

data class Clans(
    @SerializedName("clans")
    val clans: List<GroupModel>,
    @SerializedName("currentPage")
    val currentPage: Int,
    @SerializedName("pageSize")
    val pageSize: Int,
    @SerializedName("total")
    val total: Int
)