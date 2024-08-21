package com.example.narutoquiz.domain.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.narutoquiz.R
import com.example.narutoquiz.data.model.Family
import kotlin.reflect.KProperty1

fun ImageView.getUrl(url: String) {
    Glide.with(this)
        .load(url)
        .thumbnail(
            Glide.with(this)
                .load(R.drawable.spinnerblack)
        )
        .into(this)
}
fun Family.getFirstNonNullField(): Pair<String, String>? {
    this::class.members
        .filterIsInstance<KProperty1<Family, String?>>()
        .forEach { property ->
            val value = property.get(this)
            if (value != null) {
                return property.name to value
            }
        }
    return null
}


fun getPokemonId(url: String): Int{
    val matchResult = """.*/(\d+)/?$""".toRegex().find(url)
    return matchResult?.groupValues?.get(1)?.toIntOrNull() ?: 0
}



