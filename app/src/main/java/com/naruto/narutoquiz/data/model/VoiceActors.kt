package com.naruto.narutoquiz.data.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class VoiceActors(
    @JsonProperty("english")
    val english: List<String>?,
    @JsonProperty("japanese")
    val japanese: List<String>?
)