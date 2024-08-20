package com.example.narutoquiz.data.model


import com.google.gson.annotations.SerializedName

data class Personal(
    @SerializedName("affiliation")
    val affiliation: List<String>,
    @SerializedName("age")
    val age: Age,
    @SerializedName("birthdate")
    val birthdate: String,
    @SerializedName("bloodType")
    val bloodType: String,
    @SerializedName("clan")
    val clan: String,
    @SerializedName("classification")
    val classification: List<String>,
    @SerializedName("height")
    val height: Height,
    @SerializedName("kekkeiGenkai")
    val kekkeiGenkai: List<String>,
    @SerializedName("kekkeiMōra")
    val kekkeiMōra: String,
    @SerializedName("occupation")
    val occupation: List<String>,
    @SerializedName("partner")
    val partner: List<String>,
    @SerializedName("sex")
    val sex: String,
    @SerializedName("species")
    val species: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("tailedBeast")
    val tailedBeast: String,
    @SerializedName("team")
    val team: List<String>,
    @SerializedName("titles")
    val titles: List<String>,
    @SerializedName("weight")
    val weight: Weight
)