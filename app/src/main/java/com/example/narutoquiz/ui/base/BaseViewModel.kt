package com.example.narutoquiz.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.narutoquiz.data.model.Akatsuki
import com.example.narutoquiz.data.model.Character
import com.example.narutoquiz.data.model.SelectionModel
import com.example.narutoquiz.data.util.Resource
import com.example.narutoquiz.data.util.Status
import com.example.narutoquiz.domain.extension.getFirstNonNullField
import kotlinx.coroutines.launch
import kotlin.random.Random

open class BaseViewModel : ViewModel() {

    fun getCharacterCall(
        dataCall: suspend () -> Resource<List<Character>>,
        onSuccess: (suspend (List<Character>) -> Unit)?,
        onError: (suspend () -> Unit)?,
        onLoading: (suspend () -> Unit)?
    ) = viewModelScope.launch {
        when (dataCall().status) {
            Status.SUCCESS -> {
                println("boş")
                dataCall().data?.let {
                    println("boş değil")
                    onSuccess?.invoke(it)
                }
            }

            Status.ERROR -> {
                onError?.invoke()
            }

            Status.LOADING -> {
                onLoading?.invoke()
            }
        }
    }
    private fun askFamily(characterList: List<Character>) {
        val options = listOf(_firstOption, _secondOption, _thirdOption, _lastOption).shuffled()
        val nonNullPair: Pair<String, String>?
        val firstCharacter = characterList[0]
        val secondCharacter = characterList[1]
        val thirdCharacter = characterList[2]
        val lastCharacter = characterList[3]

        nonNullPair = firstCharacter.family?.getFirstNonNullField()

        if (nonNullPair != null) {
            _questionText.postValue(
                "Which one's ${nonNullPair.first}" + " is ${nonNullPair.second}"
            )
        }

        options[0].postValue(
            SelectionModel(
                firstCharacter.images?.get(0), firstCharacter.name, true
            )
        )
        options[1].postValue(
            SelectionModel(
                secondCharacter.images?.get(0), secondCharacter.name, false
            )
        )
        options[2].postValue(
            SelectionModel(
                thirdCharacter.images?.get(0), thirdCharacter.name, false
            )
        )
        options[3].postValue(
            SelectionModel(
                lastCharacter.images?.get(0), lastCharacter.name, false
            )
        )
    }

    private fun akatsukiGame() {
        getCharacterCall(
            { getFourAkatsukiCharacter() },
            { characterList -> askVoiceActor(characterList) },
            null,
            null
        )
    }

    private suspend fun getFourAkatsukiCharacter(): Resource<List<Character>> {
        _loading.postValue(true)
        var firstCharacter: Character
        var secondCharacter: Character
        var thirdCharacter: Character
        var lastCharacter: Character
        while (true) {
            val charList = repository.getAkatsukiList(43)
            firstCharacter = getAkatsuki(charList)
            if (firstCharacter.family?.getFirstNonNullField() != null) {
                secondCharacter = getAkatsuki(charList)
                thirdCharacter = getAkatsuki(charList)
                lastCharacter = getAkatsuki(charList)
                if (setOf(firstCharacter, secondCharacter, thirdCharacter, lastCharacter).size == 4
                ) {
                    _loading.postValue(false)
                    return Resource.success(
                        listOf(
                            firstCharacter, secondCharacter, thirdCharacter, lastCharacter
                        )
                    )
                }
            }
        }
    }

    private fun getAkatsuki(charList: Resource<Akatsuki>): Character {
        while (true) {
            val character = charList.data?.akatsuki?.get(Random.nextInt(0, 43))
            if (character?.images?.isEmpty() == false) {
                return character
            }
        }
    }



}



