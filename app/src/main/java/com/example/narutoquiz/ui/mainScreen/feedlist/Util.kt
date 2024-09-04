package com.example.narutoquiz.ui.mainScreen.feedlist

import com.example.narutoquiz.R
import com.example.narutoquiz.data.model.FeedRowModel
import com.example.narutoquiz.ui.mainScreen.game.GameConst.AkatsukiGameId
import com.example.narutoquiz.ui.mainScreen.game.GameConst.ChallangeGameId
import com.example.narutoquiz.ui.mainScreen.game.GameConst.ClanGameId
import com.example.narutoquiz.ui.mainScreen.game.GameConst.ClassicGameId
import com.example.narutoquiz.ui.mainScreen.game.GameConst.TailedGameId
import com.example.narutoquiz.ui.mainScreen.game.GameConst.TeamGameId

fun getRowModelList(): List<FeedRowModel> {
    return listOf(
        FeedRowModel(description = "Challenge", imageResId = R.drawable.akatsuki, gameId = ChallangeGameId),
        FeedRowModel(description = "Classic", imageResId = R.drawable.akatsuki, gameId = ClassicGameId),
        FeedRowModel(description = "Akatsuki", imageResId = R.drawable.akatsuki, gameId = AkatsukiGameId),
        FeedRowModel(description = "Clan", imageResId = R.drawable.akatsuki, gameId = ClanGameId),
        FeedRowModel(description = "Tailed Beast", imageResId = R.drawable.akatsuki, gameId = TailedGameId),
        FeedRowModel(description = "Teams", imageResId = R.drawable.akatsuki, gameId = TeamGameId),
    )
}
