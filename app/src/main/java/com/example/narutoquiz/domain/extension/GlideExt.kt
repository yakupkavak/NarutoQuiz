package com.example.narutoquiz.domain.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.narutoquiz.R

fun ImageView.getUrl(url: String) {
    Glide.with(this)
        .load(url)
        .thumbnail(
            Glide.with(this)
                .load(R.drawable.spinnerblack)
        ).error(Glide.with(this).load(R.drawable.holderitachi))
        .into(this)
}