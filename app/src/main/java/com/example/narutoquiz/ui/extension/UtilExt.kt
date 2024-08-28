package com.example.narutoquiz.ui.extension

fun getRandomNumList(limit: Int, range: Int): List<Int> {
    return if (limit > 0) {
        val numbers = (1..range).toList()
        numbers.shuffled().take(limit)
    } else {
        listOf()
    }
}