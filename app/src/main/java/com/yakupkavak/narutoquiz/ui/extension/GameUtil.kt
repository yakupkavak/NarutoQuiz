package com.yakupkavak.narutoquiz.ui.extension

import android.content.Context
import com.yakupkavak.narutoquiz.R
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst

fun Context.getStringByGameId(gameId: Int) : String{
    return when(gameId) {
        GameConst.ChallangeGameId -> {
            this.getString(R.string.challenge)
        }
        GameConst.ClassicGameId -> {
            this.getString(R.string.classic)
        }
        GameConst.AkatsukiGameId -> {
            this.getString(R.string.akatsuki)
        }
        GameConst.ClanGameId -> {
            this.getString(R.string.clan)
        }
        GameConst.TailedGameId -> {
            this.getString(R.string.tail)
        }
        GameConst.TeamGameId -> {
            this.getString(R.string.team)
        }
        else -> {
            return ""
        }
    }
}