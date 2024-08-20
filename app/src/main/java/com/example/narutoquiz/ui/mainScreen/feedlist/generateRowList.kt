package com.example.narutoquiz.ui.mainScreen.feedlist

import com.example.narutoquiz.R
import com.example.narutoquiz.data.model.FeedRowModel

fun generateRowList() : List<FeedRowModel> {
    return listOf(
        FeedRowModel("Classic",R.drawable.akatsuki,0),
        FeedRowModel("Characters",R.drawable.akatsuki,1),
        FeedRowModel("Akatsuki",R.drawable.akatsuki,2),
        FeedRowModel("Village",R.drawable.akatsuki,3),
        FeedRowModel("Kekkei Genkai",R.drawable.akatsuki,4),
        FeedRowModel("Tailed Beast",R.drawable.akatsuki,5),
        FeedRowModel("Teams",R.drawable.akatsuki,6),
        )
}