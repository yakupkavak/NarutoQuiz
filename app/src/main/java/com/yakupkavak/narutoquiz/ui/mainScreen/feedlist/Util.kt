package com.yakupkavak.narutoquiz.ui.mainScreen.feedlist

import com.yakupkavak.narutoquiz.R
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.AKATSUKI_GAME_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.CHALLENGE_GAME_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.CLAN_GAME_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.CLASSIC_GAME_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.TAILED_GAME_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.TEAM_GAME_ID

fun getRowModelList(): List<com.yakupkavak.narutoquiz.data.network.model.FeedRowModel> {
    return listOf(
        com.yakupkavak.narutoquiz.data.network.model.FeedRowModel(
            description = "Challenge",
            imageResId = R.drawable.challange,
            gameId = CHALLENGE_GAME_ID
        ),
        com.yakupkavak.narutoquiz.data.network.model.FeedRowModel(
            description = "Classic",
            imageResId = R.drawable.classic_game,
            gameId = CLASSIC_GAME_ID
        ),
        com.yakupkavak.narutoquiz.data.network.model.FeedRowModel(
            description = "Akatsuki",
            imageResId = R.drawable.akatsuki_photo,
            gameId = AKATSUKI_GAME_ID
        ),
        com.yakupkavak.narutoquiz.data.network.model.FeedRowModel(
            description = "Clan",
            imageResId = R.drawable.village,
            gameId = CLAN_GAME_ID
        ),
        com.yakupkavak.narutoquiz.data.network.model.FeedRowModel(
            description = "Tailed Beast",
            imageResId = R.drawable.tailedbeast,
            gameId = TAILED_GAME_ID
        ),
        com.yakupkavak.narutoquiz.data.network.model.FeedRowModel(
            description = "Teams",
            imageResId = R.drawable.teams,
            gameId = TEAM_GAME_ID
        ),
    )
}
