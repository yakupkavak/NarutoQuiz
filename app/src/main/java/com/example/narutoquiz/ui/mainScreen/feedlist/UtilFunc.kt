package com.example.narutoquiz.ui.mainScreen.feedlist

import com.example.narutoquiz.R
import com.example.narutoquiz.data.model.Character
import com.example.narutoquiz.data.model.FeedRowModel

fun getRowModelList(): List<FeedRowModel> {
    return listOf(
        FeedRowModel(description = "Classic", imageResId = R.drawable.akatsuki, gameId = 0),
        FeedRowModel(description = "Characters", imageResId = R.drawable.akatsuki, gameId = 1),
        FeedRowModel(description = "Akatsuki", imageResId = R.drawable.akatsuki, gameId = 2),
        FeedRowModel(description = "Village", imageResId = R.drawable.akatsuki, gameId = 3),
        FeedRowModel(description = "Kekkei Genkai", imageResId = R.drawable.akatsuki, gameId = 4),
        FeedRowModel(description = "Tailed Beast", imageResId = R.drawable.akatsuki, gameId = 5),
        FeedRowModel(description = "Teams", imageResId = R.drawable.akatsuki, gameId = 6),
    )
}

fun getNullCharacter(): Character {
    println("Null character generated")
    return Character(
        null,
        null,
        null,
        null,
        null,
        "",
        null,
        null,
        null,
        null,
        null,
        null
    )
}