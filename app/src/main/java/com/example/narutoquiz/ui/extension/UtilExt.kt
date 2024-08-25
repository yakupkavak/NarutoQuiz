package com.example.narutoquiz.ui.extension

fun getFourRandomNumber(range: Int): List<Int>{
    val numbers = (1..range).toList()
    return numbers.shuffled().take(4)
}
