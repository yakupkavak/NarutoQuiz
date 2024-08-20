package com.example.narutoquiz.ui.mainScreen.feedlist

import com.example.narutoquiz.R
import com.example.narutoquiz.data.model.FeedRowModel

fun GenerateRowList() : List<FeedRowModel> {
    return listOf(
        FeedRowModel("Akatsuki",R.drawable.akatsuki,0),
        FeedRowModel("Akatsuki",R.drawable.akatsuki,1),
        FeedRowModel("Akatsuki",R.drawable.akatsuki,2),
        FeedRowModel("Akatsuki",R.drawable.akatsuki,3),
        FeedRowModel("Akatsuki",R.drawable.akatsuki,4),
        )
}