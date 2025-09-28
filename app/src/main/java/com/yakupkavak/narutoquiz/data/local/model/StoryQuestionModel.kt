package com.yakupkavak.narutoquiz.data.local.model

import androidx.annotation.StringRes

sealed class StoryQuestionModel {

    data class StoryCharacterModel(
        //Aşağıdakilerden hangisi bu sözü söylemiştir.
        @StringRes val questionTitle: Int,
        val answerCharacterId: Int,
    ) : StoryQuestionModel()

    data class StoryTextModel(
        //Naruto annesini gördüğünde ne demiştir
        @StringRes val questionTitle: Int,
        @StringRes val trueAnswerId: Int,
        @StringRes val answerTwoId: Int,
        @StringRes val answerThreeId: Int,
        @StringRes val answerFourId: Int,
        ) : StoryQuestionModel()
}



