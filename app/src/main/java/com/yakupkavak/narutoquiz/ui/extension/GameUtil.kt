package com.yakupkavak.narutoquiz.ui.extension

import android.content.Context
import com.yakupkavak.narutoquiz.R
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst

fun Context.getStringByGameId(gameId: Int) : String{
    return when(gameId) {
        GameConst.CHALLENGE_GAME_ID -> {
            this.getString(R.string.challenge)
        }
        GameConst.CLASSIC_GAME_ID -> {
            this.getString(R.string.classic)
        }
        GameConst.AKATSUKI_GAME_ID -> {
            this.getString(R.string.akatsuki)
        }
        GameConst.CLAN_GAME_ID -> {
            this.getString(R.string.clan)
        }
        GameConst.TAILED_GAME_ID -> {
            this.getString(R.string.tail)
        }
        GameConst.TEAM_GAME_ID -> {
            this.getString(R.string.team)
        }
        else -> {
            return ""
        }
    }
}