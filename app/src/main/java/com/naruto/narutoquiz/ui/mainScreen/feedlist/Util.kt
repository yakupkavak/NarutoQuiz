package com.naruto.narutoquiz.ui.mainScreen.feedlist

import com.naruto.narutoquiz.R
import com.naruto.narutoquiz.data.network.model.FeedRowModel
import com.naruto.narutoquiz.ui.mainScreen.game.GameConst.AkatsukiGameId
import com.naruto.narutoquiz.ui.mainScreen.game.GameConst.ChallangeGameId
import com.naruto.narutoquiz.ui.mainScreen.game.GameConst.ClanGameId
import com.naruto.narutoquiz.ui.mainScreen.game.GameConst.ClassicGameId
import com.naruto.narutoquiz.ui.mainScreen.game.GameConst.TailedGameId
import com.naruto.narutoquiz.ui.mainScreen.game.GameConst.TeamGameId

fun getRowModelList(): List<com.naruto.narutoquiz.data.network.model.FeedRowModel> {
    return listOf(
        com.naruto.narutoquiz.data.network.model.FeedRowModel(
            description = "Challenge",
            imageResId = R.drawable.challange,
            gameId = ChallangeGameId
        ),
        com.naruto.narutoquiz.data.network.model.FeedRowModel(
            description = "Classic",
            imageResId = R.drawable.classic_game,
            gameId = ClassicGameId
        ),
        com.naruto.narutoquiz.data.network.model.FeedRowModel(
            description = "Akatsuki",
            imageResId = R.drawable.akatsuki_photo,
            gameId = AkatsukiGameId
        ),
        com.naruto.narutoquiz.data.network.model.FeedRowModel(
            description = "Clan",
            imageResId = R.drawable.village,
            gameId = ClanGameId
        ),
        com.naruto.narutoquiz.data.network.model.FeedRowModel(
            description = "Tailed Beast",
            imageResId = R.drawable.tailedbeast,
            gameId = TailedGameId
        ),
        com.naruto.narutoquiz.data.network.model.FeedRowModel(
            description = "Teams",
            imageResId = R.drawable.teams,
            gameId = TeamGameId
        ),
    )
}
