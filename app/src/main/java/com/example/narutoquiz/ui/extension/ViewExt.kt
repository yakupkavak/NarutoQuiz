package com.example.narutoquiz.ui.extension

import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat

fun CardView.setBackground(resource: Int){
    this.setCardBackgroundColor(ContextCompat.getColor(this.context,resource))
}