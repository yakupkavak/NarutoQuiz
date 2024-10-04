package com.naruto.narutoquiz.data.network.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Personal(
    @JsonProperty("affiliation")
    val affiliation: List<String>?,
    @JsonProperty("age")
    val age: com.naruto.narutoquiz.data.model.Age?,
    @JsonProperty("birthdate")
    val birthdate: String?,
    @JsonProperty("bloodType")
    val bloodType: String?,
    @JsonProperty("clan")
    val clan: List<String>?,
    @JsonProperty("classification")
    val classification: List<String>?,
    @JsonProperty("height")
    val height: com.naruto.narutoquiz.data.network.model.Height?,
    @JsonProperty("kekkeiGenkai")
    val kekkeiGenkai: List<String>?,
    @JsonProperty("kekkeiMōra")
    val kekkeiMora: List<String>?,
    @JsonProperty("occupation")
    val occupation: List<String>?,
    @JsonProperty("partner")
    val partner: List<String>?,
    @JsonProperty("sex")
    val sex: String?,
    @JsonProperty("species")
    val species: String?,
    @JsonProperty("status")
    val status: String?,
    @JsonProperty("tailedBeast")
    val tailedBeast: String?,
    @JsonProperty("team")
    val team: List<String>?,
    @JsonProperty("titles")
    val titles: List<String>?,
    @JsonProperty("weight")
    val weight: com.naruto.narutoquiz.data.network.model.Weight?,
    @JsonProperty("jinchūriki")
    val jinchuriki: List<String>?,
)