package com.example.narutoquiz.ui.extension

import android.content.Context
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat

fun getRandomNumList(limit: Int, range: Int): List<Int> {
    return if (limit > 0) {
        val numbers = (1..range).toList()
        numbers.shuffled().take(limit)
    } else {
        listOf()
    }
}
fun CardView.setBackground(context: Context,resource: Int){
    this.setCardBackgroundColor(ContextCompat.getColor(context,resource))
}