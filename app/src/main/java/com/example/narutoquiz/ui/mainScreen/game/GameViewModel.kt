package com.example.narutoquiz.ui.mainScreen.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.narutoquiz.data.model.Character
import com.example.narutoquiz.data.model.SelectionModel
import com.example.narutoquiz.data.repository.NarutoRepository
import com.example.narutoquiz.data.util.Resource
import com.example.narutoquiz.domain.extension.getFirstNonNullField
import com.example.narutoquiz.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: NarutoRepository,
) : BaseViewModel() {

    private val _questionText = MutableLiveData<String>()
    val questionText: LiveData<String> get() = _questionText

    private val _firstOption = MutableLiveData<SelectionModel>()
    val firstOption: LiveData<SelectionModel> get() = _firstOption

    private val _secondOption = MutableLiveData<SelectionModel>()
    val secondOption: LiveData<SelectionModel> get() = _secondOption

    private val _thirdOption = MutableLiveData<SelectionModel>()
    val thirdOption: LiveData<SelectionModel> get() = _thirdOption

    private val _lastOption = MutableLiveData<SelectionModel>()
    val lastOption: LiveData<SelectionModel> get() = _lastOption

    fun startGame(gameId: Int) {
        viewModelScope.launch {
            when (gameId) {
                0 -> {
                    askFamily()
                }

                1 -> {

                }

                2 -> {

                }

                3 -> {

                }
            }
        }
    }

    suspend fun classicGame() {
        val firstCharacter = getRandomCharacter()!!
        val secondCharacter = getRandomCharacter()!!
    }

    private suspend fun askFamily() {
        val options = listOf(_firstOption, _secondOption, _thirdOption, _lastOption).shuffled()
        var nonNullPair: Pair<String, String>? = null
        var nonNullFirst: Character? = null
        val secondCharacter = getRandomCharacter()
        val thirdCharacter = getRandomCharacter()
        val lastCharacter = getRandomCharacter()

        while (nonNullPair == null) {
            nonNullFirst = getRandomCharacter()
            nonNullPair = nonNullFirst.family?.getFirstNonNullField()
        }
        _questionText.postValue(
            "Which one's ${nonNullPair.first}" + " is ${nonNullPair.second}"
        )
        nonNullFirst?.let {
            options[0].postValue(SelectionModel(it.images?.get(0), it.name,true))
            options[1].postValue(
                SelectionModel(
                    secondCharacter.images?.get(0), secondCharacter.name,false
                )
            )
            options[2].postValue(
                SelectionModel(
                    thirdCharacter.images?.get(0), thirdCharacter.name,false
                )
            )
            options[3].postValue(
                SelectionModel(
                    lastCharacter.images?.get(0), lastCharacter.name,false
                )
            )
        }
    }

    private suspend fun getRandomCharacter(): Character {
        val charList = repository.getCharacterList(Random.nextInt(0, 10))
        val pageSize = charList.data?.pageSize
        return charList.data?.characters?.get(Random.nextInt(0, pageSize ?: 19)) ?: Character(
            null, null, null, null, null, null, null, null, null, null, null, null
        )
    }

    suspend fun getCharacter(id: Int): Resource<Character> {
        return repository.getCharacter(id)
    }

    suspend fun akatsukiQuestion() {

    }

    suspend fun clanQuestion() {

    }

    suspend fun familyQuestion() {

    }

    suspend fun kekkeiQuestion() {

    }

    suspend fun teamQuestion() {

    }

    suspend fun tailedQuestion() {

    }

    suspend fun villageQuestion() {

    }

    suspend fun voiceActorQuestion() {
        //4 farklı karakter aldım
        //bunların bir tanesinin voice aktörünü aldım
    }
}