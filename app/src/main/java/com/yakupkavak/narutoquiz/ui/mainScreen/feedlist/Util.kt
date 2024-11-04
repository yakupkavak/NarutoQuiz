package com.yakupkavak.narutoquiz.ui.mainScreen.feedlist

import com.yakupkavak.narutoquiz.R
import com.yakupkavak.narutoquiz.data.network.model.FeedRowModel
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.AkatsukiGameId
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.ChallangeGameId
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.ClanGameId
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.ClassicGameId
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.TailedGameId
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.TeamGameId

fun getRowModelList(): List<com.yakupkavak.narutoquiz.data.network.model.FeedRowModel> {
    return listOf(
        com.yakupkavak.narutoquiz.data.network.model.FeedRowModel(
            description = "Challenge",
            imageResId = R.drawable.challange,
            gameId = ChallangeGameId
        ),
        com.yakupkavak.narutoquiz.data.network.model.FeedRowModel(
            description = "Classic",
            imageResId = R.drawable.classic_game,
            gameId = ClassicGameId
        ),
        com.yakupkavak.narutoquiz.data.network.model.FeedRowModel(
            description = "Akatsuki",
            imageResId = R.drawable.akatsuki_photo,
            gameId = AkatsukiGameId
        ),
        com.yakupkavak.narutoquiz.data.network.model.FeedRowModel(
            description = "Clan",
            imageResId = R.drawable.village,
            gameId = ClanGameId
        ),
        com.yakupkavak.narutoquiz.data.network.model.FeedRowModel(
            description = "Tailed Beast",
            imageResId = R.drawable.tailedbeast,
            gameId = TailedGameId
        ),
        com.yakupkavak.narutoquiz.data.network.model.FeedRowModel(
            description = "Teams",
            imageResId = R.drawable.teams,
            gameId = TeamGameId
        ),
    )
}
