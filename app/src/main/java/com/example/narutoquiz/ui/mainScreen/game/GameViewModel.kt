package com.example.narutoquiz.ui.mainScreen.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.narutoquiz.R
import com.example.narutoquiz.data.model.Akatsuki
import com.example.narutoquiz.data.model.AnswerModel
import com.example.narutoquiz.data.model.Character
import com.example.narutoquiz.data.model.OptionModel
import com.example.narutoquiz.data.model.SelectionModel
import com.example.narutoquiz.data.repository.NarutoRepository
import com.example.narutoquiz.data.util.Resource
import com.example.narutoquiz.domain.extension.getFirstNonNullField
import com.example.narutoquiz.ui.base.BaseViewModel
import com.example.narutoquiz.ui.extension.getRandom
import com.example.narutoquiz.ui.extension.getRandomNumList
import com.example.narutoquiz.ui.mainScreen.feedlist.getNullCharacter
import com.example.narutoquiz.ui.mainScreen.game.GameConst.akatsukiSize
import com.example.narutoquiz.ui.mainScreen.game.GameConst.characterPageRange
import com.example.narutoquiz.ui.mainScreen.game.GameConst.clanPageSize
import com.example.narutoquiz.ui.mainScreen.game.GameConst.tailPageRange
import com.example.narutoquiz.ui.mainScreen.game.GameConst.teamPageSize
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.abs

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

    private val _currentGameId = MutableLiveData<Int>()
    private val currentGameId: LiveData<Int> get() = _currentGameId

    private val _currentGameTopic = MutableLiveData<String>()
    val currentGameTopic: LiveData<String> get() = _currentGameTopic

    private val _answerSelection = MutableLiveData<AnswerModel>()
    val answerSelection: LiveData<AnswerModel> get() = _answerSelection

    private val _finishGame = MutableLiveData<List<Int>>()
    val finishGame: LiveData<List<Int>> get() = _finishGame

    private var trueAnswerId: Int? = null

    fun initializeGame(gameId: Int, gameTopic: String) {
        _currentGameId.value = gameId
        _currentGameTopic.value = gameTopic
    }

    fun checkQuestion(selectedOptionId: Int) {
        if (firstOption.value?.trueAnswer == true) {
            if (selectedOptionId == 0) {
                _trueAnswer.postValue(_trueAnswer.value?.plus(1))
                _answerSelection.postValue(AnswerModel(0, null))
            } else {
                _falseAnswer.postValue(_falseAnswer.value?.plus(1))
                _answerSelection.postValue(AnswerModel(trueAnswerId, selectedOptionId))
            }
        } else if (secondOption.value?.trueAnswer == true) {
            if (selectedOptionId == 1) {
                _trueAnswer.postValue(_trueAnswer.value?.plus(1))
                _answerSelection.postValue(AnswerModel(1, null))
            } else {
                _falseAnswer.postValue(_falseAnswer.value?.plus(1))
                _answerSelection.postValue(AnswerModel(trueAnswerId, selectedOptionId))
            }
        } else if (thirdOption.value?.trueAnswer == true) {
            if (selectedOptionId == 2) {
                _trueAnswer.postValue(_trueAnswer.value?.plus(1))
                _answerSelection.postValue(AnswerModel(2, null))
            } else {
                _falseAnswer.postValue(_falseAnswer.value?.plus(1))
                _answerSelection.postValue(AnswerModel(trueAnswerId, selectedOptionId))
            }
        } else if (lastOption.value?.trueAnswer == true) {
            if (selectedOptionId == 3) {
                _trueAnswer.postValue(_trueAnswer.value?.plus(1))
                _answerSelection.postValue(AnswerModel(3, null))
            } else {
                _falseAnswer.postValue(_falseAnswer.value?.plus(1))
                _answerSelection.postValue(AnswerModel(trueAnswerId, selectedOptionId))
            }
        }
    }

    fun startGame() {
        viewModelScope.launch {
            _questionNumber.postValue(0)
            when (currentGameId.value) {
                0 -> {
                    challangeGame()
                }

                1 -> {
                    classicGame()
                }

                2 -> {
                    akatsukiGame()
                }

                3 -> {
                    clanGame()
                }

                4 -> {
                    tailedGame()
                }

                5 -> {
                    teamGame()
                }
            }
        }
    }

    fun nextQuestion() {
        viewModelScope.launch {
            if (checkGameSituation()) {
                when (currentGameId.value) {
                    0 -> {
                        challangeGame()
                    }

                    1 -> {
                        classicGame()
                    }

                    2 -> {
                        akatsukiGame()
                    }

                    3 -> {
                        clanGame()
                    }

                    4 -> {
                        tailedGame()
                    }

                    5 -> {
                        teamGame()
                    }
                }
            } else {
                gameOver()
            }
        }
    }

    private fun checkGameSituation(): Boolean {
        return if (_currentGameId.value != 0) {
            _questionNumber.value != 9
        } else {
            _falseAnswer.value == 0
        }
    }

    private fun gameOver() {
        _finishGame.postValue(listOf(_trueAnswer.value, _falseAnswer.value) as List<Int>?)
    }

    private fun challangeGame() {
        when (getRandom(from = 0, until = 4)) {
            0 -> {
                classicGame()
            }

            1 -> {
                akatsukiGame()
            }

            2 -> {
                clanGame()
            }

            3 -> {
                teamGame()
            }

            4 -> {
                tailedGame()
            }
        }
    }

    private fun classicGame() {
        getDataCall(
            dataCall = { getFourRandomCharacter() },
            onSuccess = { characterList ->
                if (characterList != null) {
                    when (getRandom(from = 0, until = 1)) {
                        0 -> {
                            askFamily(characterList).also { _loading.postValue(false) }.also {
                                _questionNumber.postValue(_questionNumber.value?.plus(1))
                            }
                        }

                        1 -> {
                            askVoiceActor(characterList).also { _loading.postValue(false) }.also {
                                _questionNumber.postValue(_questionNumber.value?.plus(1))
                            }
                        }
                    }
                }
            },
            onError = { println("error geldi") },
            onLoading = { _loading.postValue(true) }
        )
    }

    private fun askFamily(characterList: List<Character>) {
        val options = listOf(
            OptionModel(0, _firstOption),
            OptionModel(1, _secondOption),
            OptionModel(2, _thirdOption),
            OptionModel(3, _lastOption)
        ).shuffled()
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

        trueAnswerId = options[0].optionId

        options[0].option.postValue(
            SelectionModel(
                firstCharacter.images?.get(0), firstCharacter.name, true
            )
        )
        options[1].option.postValue(
            SelectionModel(
                secondCharacter.images?.get(0), secondCharacter.name, false
            )
        )
        options[2].option.postValue(
            SelectionModel(
                thirdCharacter.images?.get(0), thirdCharacter.name, false
            )
        )
        options[3].option.postValue(
            SelectionModel(
                lastCharacter.images?.get(0), lastCharacter.name, false
            )
        )
    }

    private fun akatsukiGame() {
        getDataCall(
            dataCall = { getFourAkatsukiCharacter() },
            onSuccess = { characterList ->
                if (characterList != null) {
                    when (getRandom(from = 0, until = 1)) {
                        0 -> {
                            askFamily(characterList).also { _loading.postValue(false) }.also {
                                _questionNumber.postValue(_questionNumber.value?.plus(1))
                            }
                        }

                        1 -> {
                            askVoiceActor(characterList).also { _loading.postValue(false) }.also {
                                _questionNumber.postValue(_questionNumber.value?.plus(1))
                            }
                        }
                    }
                }
            },
            onError = { println("error geldi") },
            onLoading = { _loading.postValue(true) }
        )
    }

    private suspend fun getFourAkatsukiCharacter(): Resource<List<Character>> {
        var firstCharacter: Character
        var secondCharacter: Character
        var thirdCharacter: Character
        var lastCharacter: Character
        while (true) {
            val charList = repository.getAkatsukiList(akatsukiSize)
            firstCharacter = getAkatsuki(charList)
            if (firstCharacter.family?.getFirstNonNullField() != null) {
                secondCharacter = getAkatsuki(charList)
                thirdCharacter = getAkatsuki(charList)
                lastCharacter = getAkatsuki(charList)
                if (setOf(firstCharacter, secondCharacter, thirdCharacter, lastCharacter).size == 4
                ) {
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
            val character = charList.data?.akatsuki?.get(getRandom(from = 0, until = akatsukiSize))
            if (character?.images?.isEmpty() == false) {
                return character
            }
        }
    }

    private fun askVoiceActor(characterList: List<Character>) {
        val options = listOf(
            OptionModel(0, _firstOption),
            OptionModel(1, _secondOption),
            OptionModel(2, _thirdOption),
            OptionModel(3, _lastOption)
        ).shuffled()

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

        trueAnswerId = options[0].optionId

        options[0].option.postValue(
            SelectionModel(
                firstCharacter.images?.get(0), firstCharacter.name, true
            )
        )
        options[1].option.postValue(
            SelectionModel(
                secondCharacter.images?.get(0), secondCharacter.name, false
            )
        )
        options[2].option.postValue(
            SelectionModel(
                thirdCharacter.images?.get(0), thirdCharacter.name, false
            )
        )
        options[3].option.postValue(
            SelectionModel(
                lastCharacter.images?.get(0), lastCharacter.name, false
            )
        )
    }

    private suspend fun getFourRandomCharacter(): Resource<List<Character>> {
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
                    return Resource.success(
                        selectedCharacters.toList()
                    )
                }
            }
        }
        return Resource.error(null)
    }

    private suspend fun getRandomCharacter(): Character {
        val charList = repository.getCharacterList(
            getRandom(
                from = 0,
                until = characterPageRange
            )
        ).data?.characters?.filter { character -> character.images?.isEmpty() == false }
        val pageSize = charList?.size
        return charList?.get(getRandom(from = 0, until = pageSize ?: 19)) ?: getNullCharacter()
    }

    private fun askClan(characterList: List<Character?>) {
        val options = listOf(
            OptionModel(0, _firstOption),
            OptionModel(1, _secondOption),
            OptionModel(2, _thirdOption),
            OptionModel(3, _lastOption)
        ).shuffled()

        val firstCharacter = characterList[0]
        val secondCharacter = characterList[1]
        val thirdCharacter = characterList[2]
        val lastCharacter = characterList[3]

        if (firstCharacter != null) {
            _questionText.postValue(
                "Which one's clan name" + " is ${firstCharacter.personal?.clan?.get(0)}"
            )
        }

        trueAnswerId = options[0].optionId

        if (firstCharacter != null) {
            val imageUrl = if (!firstCharacter.images.isNullOrEmpty()) {
                firstCharacter.images[0]
            } else {
                R.string.emptyImageUrl.toString()
            }
            options[0].option.postValue(
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
            options[1].option.postValue(
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
            options[2].option.postValue(
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
            options[3].option.postValue(
                SelectionModel(
                    imageUrl,
                    lastCharacter.name,
                    false
                )
            )
        }
    }

    private fun clanGame() {
        getDataCall(dataCall = { getFourClanCharacter() },
            onSuccess = { characterList ->
                if (characterList != null) {
                    askClan(characterList).also { _loading.postValue(false) }.also {
                        _questionNumber.postValue(_questionNumber.value?.plus(1))
                    }
                }
            },
            onError = {
                println("error allert")
            },
            onLoading = {
                _loading.postValue(true)
            }
        )
    }

    private suspend fun getFourClanCharacter(): Resource<List<Character?>> {
        val clanList = repository.getClanList(clanPageSize)
        val clanIdList = getRandomNumList(4, clanPageSize-1)
        val firstClan = clanList.data?.clans?.get(clanIdList[0])
        val secondClan = clanList.data?.clans?.get(clanIdList[1])
        val thirdClan = clanList.data?.clans?.get(clanIdList[2])
        val lastClan = clanList.data?.clans?.get(clanIdList[3])
        var firstCharacter: Character?
        var secondCharacter: Character?
        var thirdCharacter: Character?
        var lastCharacter: Character?

        withContext(Dispatchers.IO) {
            val getFirstCharacter = async {
                firstClan?.characters?.get(getRandom(from = 0, until = firstClan.characters.size))
                    ?.let { repository.getCharacter(it) }?.data
            }
            firstCharacter = getFirstCharacter.await()
            val getSecondCharacter = async {
                secondClan?.characters?.get(getRandom(from = 0, until = secondClan.characters.size))
                    ?.let { repository.getCharacter(it) }?.data
            }
            secondCharacter = getSecondCharacter.await()

            val getThirdCharacter = async {
                thirdClan?.characters?.get(getRandom(from = 0, until = thirdClan.characters.size))
                    ?.let { repository.getCharacter(it) }?.data
            }
            thirdCharacter = getThirdCharacter.await()

            val getLastCharacter = async {
                lastClan?.characters?.get(getRandom(from = 0, until = lastClan.characters.size))
                    ?.let { repository.getCharacter(it) }?.data
            }
            lastCharacter = getLastCharacter.await()
        }
        return Resource.success(
            listOf(
                firstCharacter,
                secondCharacter,
                thirdCharacter,
                lastCharacter
            )
        )
    }

    private fun teamGame() {
        getDataCall(
            dataCall = { getFourTeamCharacter() },
            onSuccess = { characterList ->
                if (characterList != null) {
                    askTeam(characterList).also { _loading.postValue(false) }.also {
                        _questionNumber.postValue(_questionNumber.value?.plus(1))
                    }
                }
            },
            onError = {println("allert error")},
            onLoading = { _loading.postValue(true) }
        )
    }

    private fun askTeam(characterList: List<Character?>) {
        val options = listOf(
            OptionModel(0, _firstOption),
            OptionModel(1, _secondOption),
            OptionModel(2, _thirdOption),
            OptionModel(3, _lastOption)
        ).shuffled()
        val firstCharacter = characterList[0]
        val secondCharacter = characterList[1]
        val thirdCharacter = characterList[2]
        val lastCharacter = characterList[3]

        if (firstCharacter != null) {
            _questionText.postValue(
                "Which one's team name" + " is ${firstCharacter.personal?.team?.get(0)}"
            )
        }
        trueAnswerId = options[0].optionId

        if (firstCharacter != null) {
            val imageUrl = if (!firstCharacter.images.isNullOrEmpty()) {
                firstCharacter.images[0]
            } else {
                R.string.emptyImageUrl.toString()
            }
            options[0].option.postValue(
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
            options[1].option.postValue(
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
            options[2].option.postValue(
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
            options[3].option.postValue(
                SelectionModel(
                    imageUrl,
                    lastCharacter.name,
                    false
                )
            )
        }
    }

    private suspend fun getFourTeamCharacter(): Resource<List<Character?>> {
        val teamList = repository.getTeamList(teamPageSize)
        val teamIdList = getRandomNumList(4, teamPageSize-1)
        val firstTeam = teamList.data?.teams?.get(teamIdList[0])
        val secondTeam = teamList.data?.teams?.get(teamIdList[1])
        val thirdTeam = teamList.data?.teams?.get(teamIdList[2])
        val lastTeam = teamList.data?.teams?.get(teamIdList[3])
        val firstCharacter =
            firstTeam?.characters?.get(getRandom(from = 0, until = firstTeam.characters.size))
                ?.let { repository.getCharacter(it) }?.data
        val secondCharacter =
            secondTeam?.characters?.get(getRandom(from = 0, until = secondTeam.characters.size))
                ?.let { repository.getCharacter(it) }?.data
        val thirdCharacter =
            thirdTeam?.characters?.get(getRandom(from = 0, until = thirdTeam.characters.size))
                ?.let { repository.getCharacter(it) }?.data
        val lastCharacter =
            lastTeam?.characters?.get(getRandom(from = 0, until = lastTeam.characters.size))
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

    private fun tailedGame() {
        getDataCall(
            dataCall = { getFourTailCharacter() },
            onSuccess = { characterList ->
                if (characterList != null) {
                    askJinckuri(characterList).also { _loading.postValue(false) }.also {
                        _questionNumber.postValue(_questionNumber.value?.plus(1))
                    }
                }
            },
            onError = { println("error geldi") },
            onLoading = { _loading.postValue(true) }
        )
    }

    private fun askJinckuri(characterList: List<Character?>) {
        val options = listOf(
            OptionModel(0, _firstOption),
            OptionModel(1, _secondOption),
            OptionModel(2, _thirdOption),
            OptionModel(3, _lastOption)
        ).shuffled()

        val firstCharacter = characterList[0]
        val secondCharacter = characterList[1]
        val thirdCharacter = characterList[2]
        val lastCharacter = characterList[3]

        if (firstCharacter != null) {
            _questionText.postValue(
                "Which one's jinchuriki name" + " is ${firstCharacter.personal?.jinchuriki?.get(0)}"
            )
        }
        trueAnswerId = options[0].optionId

        if (firstCharacter != null) {
            val imageUrl = if (!firstCharacter.images.isNullOrEmpty()) {
                firstCharacter.images[0]
            } else {
                R.string.emptyImageUrl.toString()
            }
            options[0].option.postValue(
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
            options[1].option.postValue(
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
            options[2].option.postValue(
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
            options[3].option.postValue(
                SelectionModel(
                    imageUrl,
                    lastCharacter.name,
                    false
                )
            )
        }
    }

    private suspend fun getFourTailCharacter(): Resource<List<Character?>> {
        val tailList = repository.getTailedBeastList()
        val tailIdList = getRandomNumList(4, tailPageRange)
        val firstTail = tailList.data?.tailedBeasts?.get(tailIdList[0])
        val secondTail = tailList.data?.tailedBeasts?.get(tailIdList[1])
        val thirdTail = tailList.data?.tailedBeasts?.get(tailIdList[2])
        val lastTail = tailList.data?.tailedBeasts?.get(tailIdList[3])
        return Resource.success(
            listOf(
                firstTail,
                secondTail,
                thirdTail,
                lastTail
            )
        )
    }

}