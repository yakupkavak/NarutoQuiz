package com.example.narutoquiz.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Family(
    @JsonProperty("adoptive brother")
    val adoptiveBrother: String?,
    @JsonProperty("adoptive mother")
    val adoptiveMother: String?,
    @JsonProperty("adoptive son")
    val adoptiveSon: String?,
    @JsonProperty("adoptive sons")
    val adoptiveSons: String?,
    @JsonProperty("aunt")
    val aunt: String?,
    @JsonProperty("brother")
    val brother: String?,
    @JsonProperty("clone")
    val clone: String?,
    @JsonProperty("clone/brother")
    val clonebrother: String?,
    @JsonProperty("clone/son")
    val cloneson: String?,
    @JsonProperty("cousin")
    val cousin: String?,
    @JsonProperty("creator")
    val creator: String?,
    @JsonProperty("daughter")
    val daughter: String?,
    @JsonProperty("father")
    val father: String?,
    @JsonProperty("first cousin once removed")
    val firstCousinOnceRemoved: String?,
    @JsonProperty("genetic template")
    val geneticTemplate: String?,
    @JsonProperty("genetic template/parent")
    val geneticTemplateparent: String?,
    @JsonProperty("godfather")
    val godfather: String?,
    @JsonProperty("godson")
    val godson: String?,
    @JsonProperty("granddaughter")
    val granddaughter: String?,
    @JsonProperty("grandfather")
    val grandfather: String?,
    @JsonProperty("grandmother")
    val grandmother: String?,
    @JsonProperty("grandson")
    val grandson: String?,
    @JsonProperty("granduncle")
    val granduncle: String?,
    @JsonProperty("great-grandfather")
    val greatGrandfather: String?,
    @JsonProperty("host")
    val host: String?,
    @JsonProperty("lover")
    val lover: String?,
    @JsonProperty("mother")
    val mother: String?,
    @JsonProperty("nephew")
    val nephew: String?,
    @JsonProperty("niece")
    val niece: String?,
    @JsonProperty("pet ")
    val pet: String?,
    @JsonProperty("sister")
    val sister: String?,
    @JsonProperty("son")
    val son: String?,
    @JsonProperty("uncle")
    val uncle: String?,
    @JsonProperty("wife")
    val wife: String?
)
