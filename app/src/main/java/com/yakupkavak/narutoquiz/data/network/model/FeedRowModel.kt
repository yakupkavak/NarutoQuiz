package com.yakupkavak.narutoquiz.data.network.model

import androidx.annotation.StringRes

data class FeedRowModel(
    @StringRes val title: Int,
    @StringRes val description: Int,
    val imageResId: Int,
    val gameId: Int
)