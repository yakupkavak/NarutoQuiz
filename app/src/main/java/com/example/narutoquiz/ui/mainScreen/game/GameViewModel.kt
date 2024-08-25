package com.example.narutoquiz.ui.mainScreen.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.narutoquiz.R
import com.example.narutoquiz.data.model.Akatsuki
import com.example.narutoquiz.data.model.Character
import com.example.narutoquiz.data.model.Characters
import com.example.narutoquiz.data.model.Clans
import com.example.narutoquiz.data.model.GroupModel
import com.example.narutoquiz.data.model.SelectionModel
import com.example.narutoquiz.data.repository.NarutoRepository
import com.example.narutoquiz.data.util.Resource
import com.example.narutoquiz.domain.extension.getFirstNonNullField
import com.example.narutoquiz.ui.base.BaseViewModel
import com.example.narutoquiz.ui.extension.getFourRandomNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs
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
                    characterGame()
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

    fun characterGame() {
        getCharacterCall(
            { getFourRandomCharacter() },
            { characterList ->
                if (characterList != null) {
                    askFamily(characterList)
                }
            },
            { println("error geldi") },
            null
        )
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
            { characterList ->
                if (characterList != null) {
                    askVoiceActor(characterList)
                }
            },
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

    private fun askVoiceActor(characterList: List<Character>) {
        val options = listOf(_firstOption, _secondOption, _thirdOption, _lastOption).shuffled()
        val nonNullPair: Pair<String, String>?
        var firstCharacter = characterList[0]
        var secondCharacter = characterList[1]
        var thirdCharacter = characterList[2]
        var lastCharacter = characterList[3]

        for (i in 0..3) {
            if (i == 0 || i == 3) {
                if (characterList[i].voiceActors != null) {
                    firstCharacter = characterList[i]
                    secondCharacter = characterList[abs(i - 1)]
                    thirdCharacter = characterList[abs(i - 2)]
                    lastCharacter = characterList[abs(i - 3)]
                }
            }
            if (i == 1) {
                if (characterList[i].voiceActors != null) {
                    firstCharacter = characterList[i]
                    secondCharacter = characterList[0]
                    thirdCharacter = characterList[2]
                    lastCharacter = characterList[3]
                }
            }
            if (i == 2) {
                if (characterList[i].voiceActors != null) {
                    firstCharacter = characterList[i]
                    secondCharacter = characterList[0]
                    thirdCharacter = characterList[1]
                    lastCharacter = characterList[3]
                }
            }
        }

        nonNullPair = firstCharacter.voiceActors?.getFirstNonNullField()

        if (nonNullPair != null) {
            _questionText.postValue(
                "Which one's ${nonNullPair.first} voice actor" + " is ${nonNullPair.second}"
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

    private suspend fun getFourRandomCharacter(): Resource<List<Character>> {
        _loading.postValue(true)
        var firstCharacter: Character
        val selectedCharacters = mutableSetOf<Character>()
        while (selectedCharacters.size < 5) {
            firstCharacter = getRandomCharacter()
            if (firstCharacter.family?.getFirstNonNullField() != null) {
                selectedCharacters.add(firstCharacter)
                selectedCharacters.add(getRandomCharacter())
                selectedCharacters.add(getRandomCharacter())
                selectedCharacters.add(getRandomCharacter())
                if (selectedCharacters.size == 4
                ) {
                    _loading.postValue(false)
                    return Resource.success(
                        selectedCharacters.toList()
                    )
                }
            }
        }
        return Resource.error(null)
    }

    private suspend fun getRandomCharacter(): Character {
        while (true) {
            val charList = repository.getCharacterList(Random.nextInt(0, 10))
            val pageSize = charList.data?.pageSize
            val character = charList.data?.characters?.get(Random.nextInt(0, pageSize ?: 19))
            println(character)
            if (character?.images?.isEmpty() == false) {
                return character
            }
        }
    }

    private fun clanGame() {
        getCharacterCall(
            { getFourClanCharacter() },
            { characterList ->
                if (characterList != null) {
                    askClan(characterList)
                }
            },
            null,
            null
        )
    }

    private fun askClan(characterList: List<Character?>) {
        val options = listOf(_firstOption, _secondOption, _thirdOption, _lastOption).shuffled()
        val firstCharacter = characterList[0]
        val secondCharacter = characterList[1]
        val thirdCharacter = characterList[2]
        val lastCharacter = characterList[3]

        if (firstCharacter != null) {
            _questionText.postValue(
                "Which one's clan name" + " is ${firstCharacter.personal?.clan?.get(0)}"
            )
        }

        if (firstCharacter != null) {
            val imageUrl = if (!firstCharacter.images.isNullOrEmpty()) {
                firstCharacter.images[0]
            } else {
                R.string.emptyImageUrl.toString()
            }
            options[0].postValue(
                SelectionModel(
                    imageUrl,
                    firstCharacter.name,
                    true
                )
            )
        }
        if (secondCharacter != null) {
            val imageUrl = if (!secondCharacter.images.isNullOrEmpty()) {
                secondCharacter.images[0]
            } else {
                R.string.emptyImageUrl.toString()
            }
            options[1].postValue(
                SelectionModel(
                    imageUrl,
                    secondCharacter.name,
                    false
                )
            )
        }
        if (thirdCharacter != null) {
            val imageUrl = if (!thirdCharacter.images.isNullOrEmpty()) {
                thirdCharacter.images[0]
            } else {
                R.string.emptyImageUrl.toString()
            }
            options[2].postValue(
                SelectionModel(
                    imageUrl,
                    thirdCharacter.name,
                    false
                )
            )
        }
        if (lastCharacter != null) {
            val imageUrl = if (!lastCharacter.images.isNullOrEmpty()) {
                lastCharacter.images[0]
            } else {
                R.string.emptyImageUrl.toString()
            }
            options[3].postValue(
                SelectionModel(
                    imageUrl,
                    lastCharacter.name,
                    false
                )
            )
        }
    }

    suspend fun getFourClanCharacter(): Resource<List<Character?>> { //4 tane klan arasından bana karakter döndürücek
        val clanList = repository.getClanList(57)
        val clanIdList = getFourRandomNumber(56)
        val firstClan = clanList.data?.clans?.get(clanIdList[0])
        val secondClan = clanList.data?.clans?.get(clanIdList[1])
        val thirdClan = clanList.data?.clans?.get(clanIdList[2])
        val lastClan = clanList.data?.clans?.get(clanIdList[3])
        val firstCharacter =
            firstClan?.characters?.get(Random.nextInt(0, firstClan.characters.size))
                ?.let { repository.getCharacter(it) }?.data
        val secondCharacter =
            secondClan?.characters?.get(Random.nextInt(0, secondClan.characters.size))
                ?.let { repository.getCharacter(it) }?.data
        val thirdCharacter =
            thirdClan?.characters?.get(Random.nextInt(0, thirdClan.characters.size))
                ?.let { repository.getCharacter(it) }?.data
        val lastCharacter =
            lastClan?.characters?.get(Random.nextInt(0, lastClan.characters.size))
                ?.let { repository.getCharacter(it) }?.data
        return Resource.success(
            listOf(
                firstCharacter,
                secondCharacter,
                thirdCharacter,
                lastCharacter
            )
        )
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