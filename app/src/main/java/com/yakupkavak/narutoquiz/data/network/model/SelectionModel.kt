package com.yakupkavak.narutoquiz.data.network.model

import androidx.annotation.StringRes

data class SelectionModel(
    val imageUrl: String?,
    val characterName: String?,
    val trueAnswer: Boolean?,
    @StringRes val answerText: String? = null,
    )
