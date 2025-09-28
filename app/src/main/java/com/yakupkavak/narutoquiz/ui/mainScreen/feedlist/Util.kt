package com.yakupkavak.narutoquiz.ui.mainScreen.feedlist

import com.yakupkavak.narutoquiz.R
import com.yakupkavak.narutoquiz.data.network.model.FeedRowModel
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.AKATSUKI_GAME_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.CHALLENGE_GAME_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.CLAN_GAME_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.CLASSIC_GAME_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.STORY_GAME_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.TAILED_GAME_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.TEAM_GAME_ID

fun getRowModelList(): List<FeedRowModel> {
    return listOf(
        FeedRowModel(
            title = R.string.game_story_title,
            description = R.string.game_story_description,
            imageResId = R.drawable.storydownscale,
            gameId = STORY_GAME_ID
        )
        ,
        FeedRowModel(
            title = R.string.game_challenge_title,
            description = R.string.game_challenge_description,
            imageResId = R.drawable.challange,
            gameId = CHALLENGE_GAME_ID
        ),
         FeedRowModel(
            title = R.string.game_classic_title,
             description = R.string.game_classic_description,
             imageResId = R.drawable.classic_game,
            gameId = CLASSIC_GAME_ID
        ),
         FeedRowModel(
            title = R.string.game_akatsuki_title,
             description = R.string.game_akatsuki_description,
             imageResId = R.drawable.akatsuki_photo,
            gameId = AKATSUKI_GAME_ID
        ),
        FeedRowModel(
            title = R.string.game_clan_title,
            description = R.string.game_clan_description,
            imageResId = R.drawable.village,
            gameId = CLAN_GAME_ID
        ),
        FeedRowModel(
            title = R.string.game_tailed_beast_title,
            description = R.string.game_tailed_beast_description,
            imageResId = R.drawable.tailedbeast,
            gameId = TAILED_GAME_ID
        ),
        FeedRowModel(
            title = R.string.game_teams_title,
            description = R.string.game_teams_description,
            imageResId = R.drawable.teams,
            gameId = TEAM_GAME_ID
        ),
    )
}
