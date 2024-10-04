package com.naruto.narutoquiz.data.network.model

import com.google.gson.annotations.SerializedName

data class KekkeiGenkai(
    @SerializedName("kekkei-genkai")
    val clans: List<com.naruto.narutoquiz.data.network.model.GroupModel>?,
    @SerializedName("currentPage")
    val currentPage: Int?,
    @SerializedName("pageSize")
    val pageSize: Int?,
    @SerializedName("total")
    val total: Int?,
)
