package com.example.narutoquiz.ui.extension

import android.content.Context
import com.example.narutoquiz.R
import com.example.narutoquiz.ui.mainScreen.game.GameConst.AkatsukiGameId
import com.example.narutoquiz.ui.mainScreen.game.GameConst.ChallangeGameId
import com.example.narutoquiz.ui.mainScreen.game.GameConst.ClanGameId
import com.example.narutoquiz.ui.mainScreen.game.GameConst.ClassicGameId
import com.example.narutoquiz.ui.mainScreen.game.GameConst.TailedGameId
import com.example.narutoquiz.ui.mainScreen.game.GameConst.TeamGameId
import kotlin.random.Random

fun getRandomNumList(limit: Int, range: Int): List<Int> {
    return if (limit > 0) {
        val numbers = (1..range).toList()
        numbers.shuffled().take(limit)
    } else {
        listOf()
    }
}
fun getRandom(from: Int = 0, includeUntil: Int): Int{
    return Random.nextInt(from,includeUntil+1)
}
fun Context.setString(gameId: Int) : String{
    return when(gameId) {
        ChallangeGameId -> {
            this.getString(R.string.challenge)
        }
        ClassicGameId -> {
            this.getString(R.string.classic)
        }
        AkatsukiGameId -> {
            this.getString(R.string.akatsuki)
        }
        ClanGameId -> {
            this.getString(R.string.clan)
        }
        TailedGameId -> {
            this.getString(R.string.tail)
        }
        TeamGameId -> {
            this.getString(R.string.team)
        }
        else -> {
            return "Null"
        }
    }
}