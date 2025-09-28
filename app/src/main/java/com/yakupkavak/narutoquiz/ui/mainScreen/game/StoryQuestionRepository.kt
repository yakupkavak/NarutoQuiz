package com.yakupkavak.narutoquiz.ui.mainScreen.game

import com.yakupkavak.narutoquiz.R
import com.yakupkavak.narutoquiz.data.local.model.StoryQuestionModel
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.GAARA
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.ITACHI
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.KABUTO
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.KAKASHI
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.MADARA
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.MINATO
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.NARUTO
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.OBITO
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.OROCHIMARU
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.ROCK_LEE
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.SAKURA
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.SASUKE
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.gaaraList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.itachiList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.kabutoList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.kakashiList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.madaraList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.minatoList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.narutoList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.obitoList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.orochimaruList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.rockLeeList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.sakuraList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.sasukeList

import kotlin.random.Random

class StoryQuestionRepository {

    fun getQuestion(): StoryQuestionModel {
        val random = Random.nextInt(0, 1)

        return if (random == 0) {
            createTextQuestion()
        } else {
            createStandartQuestion()
        }
    }

    private fun createTextQuestion(): StoryQuestionModel {
        val randomType: TextType = TextType.entries.toTypedArray().random()
        return narutoQuestion(type = randomType)
    }

    private fun createStandartQuestion(): StoryQuestionModel {
        val randomType: TextType = TextType.entries.toTypedArray().random()
        return narutoQuestion(type = randomType)
    }

    private fun narutoQuestion(type: TextType): StoryQuestionModel{

        return when (type) {
            TextType.Naruto     -> StoryQuestionModel.StoryCharacterModel(narutoList.random(),
                NARUTO)
            TextType.Itachi     -> StoryQuestionModel.StoryCharacterModel(itachiList.random(),
                ITACHI)
            TextType.Sasuke     -> StoryQuestionModel.StoryCharacterModel(sasukeList.random(),
                SASUKE)
            TextType.Kakashi    -> StoryQuestionModel.StoryCharacterModel(kakashiList.random(),
                KAKASHI)
            TextType.Madara     -> StoryQuestionModel.StoryCharacterModel(madaraList.random(),
                MADARA)
            TextType.Obito      -> StoryQuestionModel.StoryCharacterModel(obitoList.random(),
                OBITO)
            TextType.Gaara      -> StoryQuestionModel.StoryCharacterModel(gaaraList.random(),
                GAARA)
            TextType.Kabuto     -> StoryQuestionModel.StoryCharacterModel(kabutoList.random(),
                KABUTO)
            TextType.Orochimaru -> StoryQuestionModel.StoryCharacterModel(orochimaruList.random(),
                OROCHIMARU)
            TextType.RockLee    -> StoryQuestionModel.StoryCharacterModel(rockLeeList.random(),
                ROCK_LEE)
            TextType.Sakura     -> StoryQuestionModel.StoryCharacterModel(sakuraList.random(),
                SAKURA)
            TextType.Minato     -> StoryQuestionModel.StoryCharacterModel(minatoList.random(),
                MINATO)
        }
    }
}
enum class TextType{
    Naruto,
    Sasuke,
    Itachi,
    Kakashi,
    Madara,
    Obito,
    Gaara,
    Kabuto,
    Orochimaru,
    RockLee,
    Sakura,
    Minato
}