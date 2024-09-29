package com.naruto.narutoquiz.ui.mainScreen.feedlist

import com.naruto.narutoquiz.R
import com.naruto.narutoquiz.data.model.FeedRowModel
import com.naruto.narutoquiz.ui.mainScreen.game.GameConst.AkatsukiGameId
import com.naruto.narutoquiz.ui.mainScreen.game.GameConst.ChallangeGameId
import com.naruto.narutoquiz.ui.mainScreen.game.GameConst.ClanGameId
import com.naruto.narutoquiz.ui.mainScreen.game.GameConst.ClassicGameId
import com.naruto.narutoquiz.ui.mainScreen.game.GameConst.TailedGameId
import com.naruto.narutoquiz.ui.mainScreen.game.GameConst.TeamGameId

fun getRowModelList(): List<FeedRowModel> {
    return listOf(
        FeedRowModel(description = "Challenge", imageResId = R.drawable.challange, gameId = ChallangeGameId),
        FeedRowModel(description = "Classic", imageResId = R.drawable.classic_game, gameId = ClassicGameId),
        FeedRowModel(description = "Akatsuki", imageResId = R.drawable.akatsuki_photo, gameId = AkatsukiGameId),
        FeedRowModel(description = "Clan", imageResId = R.drawable.village, gameId = ClanGameId),
        FeedRowModel(description = "Tailed Beast", imageResId = R.drawable.tailedbeast, gameId = TailedGameId),
        FeedRowModel(description = "Teams", imageResId = R.drawable.teams, gameId = TeamGameId),
    )
}
