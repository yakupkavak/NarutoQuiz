package com.example.narutoquiz.data.model


import com.google.gson.annotations.SerializedName

data class Family(
    @SerializedName("adoptive brother")
    val adoptiveBrother: String,
    @SerializedName("adoptive mother")
    val adoptiveMother: String,
    @SerializedName("adoptive son")
    val adoptiveSon: String,
    @SerializedName("adoptive sons")
    val adoptiveSons: String,
    @SerializedName("aunt")
    val aunt: String,
    @SerializedName("brother")
    val brother: String,
    @SerializedName("clone")
    val clone: String,
    @SerializedName("clone/brother")
    val clonebrother: String,
    @SerializedName("clone/son")
    val cloneson: String,
    @SerializedName("cousin")
    val cousin: String,
    @SerializedName("creator")
    val creator: String,
    @SerializedName("daughter")
    val daughter: String,
    @SerializedName("father")
    val father: String,
    @SerializedName("first cousin once removed")
    val firstCousinOnceRemoved: String,
    @SerializedName("genetic template")
    val geneticTemplate: String,
    @SerializedName("genetic template/parent")
    val geneticTemplateparent: String,
    @SerializedName("godfather")
    val godfather: String,
    @SerializedName("godson")
    val godson: String,
    @SerializedName("granddaughter")
    val granddaughter: String,
    @SerializedName("grandfather")
    val grandfather: String,
    @SerializedName("grandmother ")
    val grandmother: String,
    @SerializedName("grandmother")
    val grandmother: String,
    @SerializedName("grandson")
    val grandson: String,
    @SerializedName("granduncle")
    val granduncle: String,
    @SerializedName("great-grandfather")
    val greatGrandfather: String,
    @SerializedName("host")
    val host: String,
    @SerializedName("lover")
    val lover: String,
    @SerializedName("mother")
    val mother: String,
    @SerializedName("nephew")
    val nephew: String,
    @SerializedName("niece")
    val niece: String,
    @SerializedName("pet ")
    val pet: String,
    @SerializedName("sister")
    val sister: String,
    @SerializedName("son")
    val son: String,
    @SerializedName("uncle")
    val uncle: String,
    @SerializedName("wife")
    val wife: String
)