package com.example.narutoquiz.ui.mainScreen.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.narutoquiz.R
import com.example.narutoquiz.data.model.Akatsuki
import com.example.narutoquiz.data.model.Character
import com.example.narutoquiz.data.model.GroupModel
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

    private val _questionNumber = MutableLiveData(0)
    val questionNumber: LiveData<Int> get() = _questionNumber

    private val _trueAnswer = MutableLiveData(0)
    val trueAnswer: LiveData<Int> get() = _trueAnswer

    private val _falseAnswer = MutableLiveData(0)
    val falseAnswer: LiveData<Int> get() = _falseAnswer

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

    private var _currentGameId: Int = 0

    fun startGame(gameId: Int) {
        _currentGameId = gameId
        viewModelScope.launch {
            when (gameId) {
                0 -> {
//                    classicGame()
                }

                1 -> {
//                    characterGame()
                }

                2 -> {
                    akatsukiGame()
                }

                3 -> {
                    clanGame()
                }

                4 -> {
                    kekkeiGame()
                }

                5 -> {
                    tailedGame()
                }

                6 -> {
                    teamGame()
                }
            }
        }
    }

    fun nextQuestion() {
        viewModelScope.launch {
            when (_currentGameId) {
                0 -> {
                }

                1 -> {

                }

                2 -> {

                }

                3 -> {

                }
            }
            _questionNumber.postValue(_questionNumber.value?.plus(1))
        }
    }
    /*
    suspend fun classicGame() {
        characterGame()
    }


    suspend fun characterGame() {
        askFamily(callCharacter = { getRandomCharacter() })
    }

     */

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
            { characterList ->  askFamily(characterList)  },
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
            if (firstCharacter.family?.getFirstNonNullField() != null){
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

    private fun getAkatsuki(charList: Resource<Akatsuki>) : Character{
        while (true){
            val character = charList.data?.akatsuki?.get(Random.nextInt(0, 43))
            if (character?.images?.isEmpty() == false ) {
                return character
            }
        }
    }

    private suspend fun askVoiceActor(callCharacter: suspend () -> Character) {
        val options = listOf(_firstOption, _secondOption, _thirdOption, _lastOption).shuffled()
        var nonNullPair: Pair<String, String>? = null
        var firstCharacter: Character? = null
        var secondCharacter: Character?
        var thirdCharacter: Character?
        var lastCharacter: Character?

        while (nonNullPair == null) {
            firstCharacter = callCharacter.invoke()
            nonNullPair = firstCharacter.family?.getFirstNonNullField()
        }

        while (true) {
            secondCharacter = callCharacter.invoke()
            if (firstCharacter != null && secondCharacter.name != firstCharacter.name) {
                thirdCharacter = callCharacter.invoke()
                if (thirdCharacter.name != secondCharacter.name && thirdCharacter.name != firstCharacter.name) {
                    lastCharacter = callCharacter.invoke()
                    if (lastCharacter.name != thirdCharacter.name && lastCharacter.name != secondCharacter.name && lastCharacter.name != firstCharacter.name) {
                        break
                    }
                }
            }
        }
        _questionText.postValue(
            "Which one's ${nonNullPair.first} voice actor" + " is ${nonNullPair.second}"
        )
        firstCharacter?.let {
            options[0].postValue(SelectionModel(it.images?.get(0), it.name, true))
            if (secondCharacter != null) {
                options[1].postValue(
                    SelectionModel(
                        secondCharacter.images?.get(0), secondCharacter.name, false
                    )
                )
            }
            if (thirdCharacter != null) {
                options[2].postValue(
                    SelectionModel(
                        thirdCharacter.images?.get(0), thirdCharacter.name, false
                    )
                )
            }
            if (lastCharacter != null) {
                options[3].postValue(
                    SelectionModel(
                        lastCharacter.images?.get(0), lastCharacter.name, false
                    )
                )
            }
        }
    }

    private suspend fun getRandomCharacter(): Character {
        val charList = repository.getCharacterList(Random.nextInt(0, 10))
        val pageSize = charList.data?.pageSize
        var nonNullCharacter: Character?
        while (true) {
            nonNullCharacter = charList.data?.characters?.get(Random.nextInt(0, pageSize ?: 19))
            if (nonNullCharacter?.images?.isEmpty() == false && nonNullCharacter.family != null) {
                return nonNullCharacter
            }
        }
    }

    suspend fun clanGame() {
        askClan { getRandomClan() }
    }

    suspend fun askClan(callClan: suspend () -> GroupModel?) {
        val options = listOf(_firstOption, _secondOption, _thirdOption, _lastOption).shuffled()
        val firstClan = callClan.invoke()
        var secondClan: GroupModel?
        var thirdClan: GroupModel?
        var lastClan: GroupModel?

        while (true) {
            secondClan = callClan.invoke()
            if (secondClan != null) {
                if (firstClan != null && secondClan.name != firstClan.name) {
                    thirdClan = callClan.invoke()
                    if (thirdClan != null) {
                        if (thirdClan.name != secondClan.name && thirdClan.name != firstClan.name) {
                            lastClan = callClan.invoke()
                            if (lastClan != null) {
                                if (lastClan.name != thirdClan.name && lastClan.name != secondClan.name && lastClan.name != firstClan.name) {
                                    break
                                }
                            }
                        }
                    }
                }
            }
        }

        val firstCharacter = firstClan?.let { getClanCharacter(it.id) }
        val secondCharacter = secondClan?.let { getClanCharacter(it.id) }
        val thirdCharacter = thirdClan?.let { getClanCharacter(it.id) }
        val lastCharacter = lastClan?.let { getClanCharacter(it.id) }

        if (firstClan != null) {
            _questionText.postValue(
                "Which one's clan" + " is ${firstClan.name}"
            )
        }

        if (firstCharacter != null) {
            options[0].postValue(
                SelectionModel(
                    firstCharacter.images?.get(0) ?: R.string.emptyImageUrl.toString(),
                    firstCharacter.name,
                    true
                )
            )
        }
        if (secondCharacter != null) {
            options[1].postValue(
                SelectionModel(
                    secondCharacter.images?.get(0) ?: R.string.emptyImageUrl.toString(),
                    secondCharacter.name,
                    false
                )
            )
        }
        if (thirdCharacter != null) {
            options[2].postValue(
                SelectionModel(
                    thirdCharacter.images?.get(0) ?: R.string.emptyImageUrl.toString(),
                    thirdCharacter.name,
                    false
                )
            )
        }
        if (lastCharacter != null) {
            options[3].postValue(
                SelectionModel(
                    lastCharacter.images?.get(0) ?: R.string.emptyImageUrl.toString(),
                    lastCharacter.name,
                    false
                )
            )
        }
    }

    suspend fun getRandomClan(): GroupModel? {
        val charList = repository.getClanList(Random.nextInt(0, 3))
        return charList.data?.clans?.get(Random.nextInt(0, charList.data.pageSize ?: 2))
    }

    suspend fun getClanCharacter(clanId: Int): Character? {
        val charList = repository.getClan(clanId)
        val randomCharId =
            charList.data?.characters?.get(Random.nextInt(0, charList.data.characters.size)) ?: 1293
        return repository.getCharacter(randomCharId).data
    }

    suspend fun familyQuestion() {

    }

    suspend fun kekkeiGame() {

    }

    suspend fun teamGame() {

    }

    suspend fun tailedGame() {

    }

    suspend fun villageGame() {

    }

}