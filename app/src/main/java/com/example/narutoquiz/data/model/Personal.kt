package com.example.narutoquiz.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Personal(
    @JsonProperty("affiliation")
    val affiliation: List<String>?,
    @JsonProperty("age")
    val age: Age?,
    @JsonProperty("birthdate")
    val birthdate: String?,
    @JsonProperty("bloodType")
    val bloodType: String?,
    @JsonProperty("clan")
    val clan: List<String>?,
    @JsonProperty("classification")
    val classification: List<String>?,
    @JsonProperty("height")
    val height: Height?,
    @JsonProperty("kekkeiGenkai")
    val kekkeiGenkai: List<String>?,
    @JsonProperty("kekkeiMōra")
    val kekkeiMōra: List<String>?,
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
    val weight: Weight?,
    @JsonProperty("jinchūriki")
    val jinchuriki: List<String>?,
)