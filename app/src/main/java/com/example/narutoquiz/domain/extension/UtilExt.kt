package com.example.narutoquiz.domain.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.narutoquiz.R
import com.example.narutoquiz.data.model.Clans
import com.example.narutoquiz.data.model.Family
import com.example.narutoquiz.data.model.Personal
import com.example.narutoquiz.data.model.VoiceActors
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
    this::class.members //koleksiyonu döndürdüm
        .filterIsInstance<KProperty1<Family, String?>>() //sadece içerisinde string? olanları aldım
        .forEach { property ->    //her birinin içerisinde gezinme işlemi yapıyorum ve bu father gibi
            val value = property.get(this) //father'in değerini aldım
            if (value != null) {
                return property.name to value //pair olarak döndürdüm
            }
        }
    return null
}
fun VoiceActors.getFirstNonNullField(): Pair<String, String>? {
    this::class.members
        .filterIsInstance<KProperty1<VoiceActors, Any?>>()
        .forEach { property ->
            val value = property.get(this)
            when (value) {
                is String -> {
                    if (value.isNotBlank()) {
                        return property.name to value
                    }
                }
                is List<*> -> {
                    val nonNullValue = value.filterIsInstance<String>().firstOrNull { it.isNotBlank() }
                    if (nonNullValue != null) {
                        return property.name to nonNullValue
                    }
                }
            }
        }
    return null
}


