package com.example.narutoquiz.ui.extension

import kotlin.random.Random

fun getRandomNumList(limit: Int, range: Int): List<Int> {
    return if (limit > 0) {
        val numbers = (1..range).toList()
        numbers.shuffled().take(limit)
    } else {
        listOf()
    }
}
fun getRandom(from: Int,until: Int): Int{
    return Random.nextInt(from,until+1)
}