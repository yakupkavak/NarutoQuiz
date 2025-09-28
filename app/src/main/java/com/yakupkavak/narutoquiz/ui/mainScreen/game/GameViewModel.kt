package com.yakupkavak.narutoquiz.ui.mainScreen.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yakupkavak.narutoquiz.R
import com.yakupkavak.narutoquiz.data.local.model.StoryQuestionModel
import com.yakupkavak.narutoquiz.data.local.repository.DaoRepository
import com.yakupkavak.narutoquiz.data.local.repository.MockRepository
import com.yakupkavak.narutoquiz.data.network.model.Akatsuki
import com.yakupkavak.narutoquiz.data.network.model.AnswerModel
import com.yakupkavak.narutoquiz.data.network.model.Character
import com.yakupkavak.narutoquiz.data.network.model.GroupModel
import com.yakupkavak.narutoquiz.data.network.model.OptionModel
import com.yakupkavak.narutoquiz.data.network.model.SelectionModel
import com.yakupkavak.narutoquiz.data.network.repository.FirestoreRepository
import com.yakupkavak.narutoquiz.data.network.repository.GeminiRepository
import com.yakupkavak.narutoquiz.data.network.repository.NarutoRepository
import com.yakupkavak.narutoquiz.data.network.util.Resource
import com.yakupkavak.narutoquiz.domain.extension.getFirstNonNullField
import com.yakupkavak.narutoquiz.ui.base.BaseViewModel
import com.yakupkavak.narutoquiz.ui.extension.getRandom
import com.yakupkavak.narutoquiz.ui.extension.getRandomNumList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.AKATSUKI_GAME_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.AKATSUKI_SIZE
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.ASK_CLAN_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.ASK_FAMILY_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.ASK_JINCURIKI_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.ASK_STORY_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.ASK_TEAM_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.CHALLENGE_GAME_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.CHARACTER_PAGE_RANGE
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.CLAN_GAME_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.CLAN_PAGE_SIZE
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.CLASSIC_GAME_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.FIRST_CHARACTER_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.FIRST_OPTION_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.GAME_REPEAT_COUNT
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.LAST_CHARACTER_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.LAST_OPTION_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.SECOND_CHARACTER_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.SECOND_OPTION_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.STORY_CHARACTER_SIZE
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.STORY_GAME_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.TAIL_PAGE_RANGE
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.TAILED_GAME_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.TEAM_GAME_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.TEAM_PAGE_SIZE
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.THIRD_CHARACTER_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.THIRD_OPTION_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.popularCharacterList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val narutoRepository: NarutoRepository,
    private val firestoreRepository: FirestoreRepository,
    private val geminiRepository: GeminiRepository,
    private val daoRepository: DaoRepository,
    private val mockRepository: MockRepository,
) : BaseViewModel() {

    private val _questionText = MutableLiveData<String>()
    val questionText: LiveData<String> get() = _questionText

    private val _storyText = MutableLiveData<String>()
    val storyText: LiveData<String> get() = _storyText

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

    private val _falseAnswer = MutableLiveData(0)

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

    private val _currentGameId = MutableLiveData<Int>()
    val currentGameId: LiveData<Int> get() = _currentGameId

    private val _currentGameTopic = MutableLiveData<Int>()
    val currentGameTopic: LiveData<Int> get() = _currentGameTopic

    private val _answerSelection =
        MutableLiveData<AnswerModel>()
    val answerSelection: LiveData<AnswerModel> get() = _answerSelection

    private val _finishGame = MutableLiveData<List<Int?>>()
    val finishGame: LiveData<List<Int?>> get() = _finishGame

    private val _questionId = MutableLiveData<Int>()
    val questionId: LiveData<Int> get() = _questionId

    private val _hintText = MutableLiveData<String>()
    val hintText: LiveData<String> get() = _hintText

    private var trueCharacter: String? = null
    private var trueAnswerId: Int? = null
    private val storyRepository = StoryQuestionRepository()

    private fun clearGame() {
        _questionNumber.postValue(0)
        _trueAnswer.postValue(0)
        _falseAnswer.postValue(0)
    }

    fun initializeGame(gameId: Int, gameTopic: Int) {
        _currentGameId.value = gameId
        _currentGameTopic.value = gameTopic
        startGame()
    }

    fun getHint() {
        trueCharacter?.let { character ->
            getDataCall(
                dataCall = { geminiRepository.getHint(character) },
                onSuccess = { _hintText.postValue(it) },
                onLoading = {},
                onError = {}
            )
        }
    }

    fun checkQuestion(selectedOptionId: Int) {
        if (firstOption.value?.trueAnswer == true) {
            if (selectedOptionId == 0) {
                _trueAnswer.postValue(_trueAnswer.value?.plus(1))
                _answerSelection.postValue(
                    AnswerModel(
                        0,
                        null
                    )
                )
            } else {
                _falseAnswer.postValue(_falseAnswer.value?.plus(1))
                _answerSelection.postValue(
                    AnswerModel(
                        trueAnswerId,
                        selectedOptionId
                    )
                )
            }
        } else if (secondOption.value?.trueAnswer == true) {
            if (selectedOptionId == 1) {
                _trueAnswer.postValue(_trueAnswer.value?.plus(1))
                _answerSelection.postValue(
                    AnswerModel(
                        1,
                        null
                    )
                )
            } else {
                _falseAnswer.postValue(_falseAnswer.value?.plus(1))
                _answerSelection.postValue(
                    AnswerModel(
                        trueAnswerId,
                        selectedOptionId
                    )
                )
            }
        } else if (thirdOption.value?.trueAnswer == true) {
            if (selectedOptionId == 2) {
                _trueAnswer.postValue(_trueAnswer.value?.plus(1))
                _answerSelection.postValue(
                    AnswerModel(
                        2,
                        null
                    )
                )
            } else {
                _falseAnswer.postValue(_falseAnswer.value?.plus(1))
                _answerSelection.postValue(
                    AnswerModel(
                        trueAnswerId,
                        selectedOptionId
                    )
                )
            }
        } else if (lastOption.value?.trueAnswer == true) {
            if (selectedOptionId == 3) {
                _trueAnswer.postValue(_trueAnswer.value?.plus(1))
                _answerSelection.postValue(
                    AnswerModel(
                        3,
                        null
                    )
                )
            } else {
                _falseAnswer.postValue(_falseAnswer.value?.plus(1))
                _answerSelection.postValue(
                    AnswerModel(
                        trueAnswerId,
                        selectedOptionId
                    )
                )
            }
        }
    }

    fun startGame() {
        clearGame()
        viewModelScope.launch {
            when (currentGameId.value) {
                STORY_GAME_ID -> {
                    storyGame()
                }

                CHALLENGE_GAME_ID -> {
                    challengeGame()
                }

                CLASSIC_GAME_ID -> {
                    classicGame()
                }

                AKATSUKI_GAME_ID -> {
                    akatsukiGame()
                }

                CLAN_GAME_ID -> {
                    clanGame()
                }

                TAILED_GAME_ID -> {
                    tailedGame()
                }

                TEAM_GAME_ID -> {
                    teamGame()
                }
            }
        }
    }

    fun nextQuestion() {
        viewModelScope.launch {
            if (checkGameSituation()) {
                when (currentGameId.value) {
                    STORY_GAME_ID -> {
                        storyGame()
                    }

                    CHALLENGE_GAME_ID -> {
                        challengeGame()
                    }

                    CLASSIC_GAME_ID -> {
                        classicGame()
                    }

                    AKATSUKI_GAME_ID -> {
                        akatsukiGame()
                    }

                    CLAN_GAME_ID -> {
                        clanGame()
                    }

                    TAILED_GAME_ID -> {
                        tailedGame()
                    }

                    TEAM_GAME_ID -> {
                        teamGame()
                    }
                }
            } else {
                gameOver()
            }
        }
    }

    private fun checkGameSituation(): Boolean {
        return if (_currentGameId.value != CHALLENGE_GAME_ID) {
            _questionNumber.value != GAME_REPEAT_COUNT
        } else {
            _falseAnswer.value == 0 //there is no wrong choice in the challenge mod.
        }
    }

    private suspend fun gameOver() {
        _finishGame.postValue(listOf(_trueAnswer.value, _falseAnswer.value))
        if (_currentGameId.value == CHALLENGE_GAME_ID) {
            firestoreRepository.postGameScore(trueAnswer = _trueAnswer.value)
        }

        withContext(Dispatchers.IO) {
            daoRepository.insertGame(
                gameId = _currentGameId.value ?: 0,
                trueCount = _trueAnswer.value ?: 0,
                falseCount = _falseAnswer.value ?: 0,
            )
        }
    }

    private fun challengeGame() {
        when (getRandom(includeUntil = 6)) {
            0 -> {
                familyGame()
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

            5 -> {
                familyGame()
            }

            6 -> {
                storyGame()
            }
        }
    }

    private fun classicGame() {
        when (getRandom(includeUntil = 6)) {
            0 -> {
                familyGame()
            }

            1 -> {
                clanGame()
            }

            2 -> {
                tailedGame()
            }

            3 -> {
                teamGame()
            }

            4 -> {
                familyGame()
            }

            5 -> {
                storyGame()
            }

            6 -> {
                storyGame()
            }
        }
    }

    private fun familyGame() {
        getDataCall(
            dataCall = { getFourRandomCharacter() },
            onSuccess = { characterList ->
                if (characterList != null) {
                    askFamily(characterList).also { _loading.postValue(false) }.also {
                        _questionNumber.postValue(_questionNumber.value?.plus(1))
                    }
                }
            },
            onError = { _error.postValue(true) },
            onLoading = { _loading.postValue(true) }
        )
    }

    private fun askFamily(characterList: List<Character?>) {
        _questionId.postValue(ASK_FAMILY_ID)

        val nonNullPair: Pair<String, String>?
        val firstCharacter = characterList[FIRST_CHARACTER_ID]

        if (firstCharacter != null) {
            nonNullPair = firstCharacter.family?.getFirstNonNullField()
            if (nonNullPair != null) {
                _questionText.postValue(
                    "${nonNullPair.first} is ${nonNullPair.second}"
                )
                trueCharacter = firstCharacter.name
            }
        }
        setOptions(
            listOf(
                firstCharacter,
                characterList[SECOND_CHARACTER_ID],
                characterList[THIRD_CHARACTER_ID],
                characterList[LAST_CHARACTER_ID]
            )
        )
    }

    private fun storyGame() {
        val question = storyRepository.getQuestion()
        _questionId.postValue(ASK_STORY_ID)
        when (question) {
            is StoryQuestionModel.StoryCharacterModel -> {
                _questionText.postValue(mockRepository.getStringValue(question.questionTitle))
                askStoryClassic(question.answerCharacterId)
            }
            is StoryQuestionModel.StoryTextModel -> {
                question.trueAnswerId
                // Burada question.trueAnswerId, question.answerTwoId vs. var
            }
        }
    }

    private fun askStoryClassic(characterId: Int) {
        getDataCall(
            dataCall = { getThreeRandomCharacter(characterId = characterId) },
            onSuccess = { characterList ->
                if (characterList != null) {
                    createClassicStory(characterId = characterId, characterList = characterList).also { _loading.postValue(false) }.also {
                        _questionNumber.postValue(_questionNumber.value?.plus(1))
                    }
                }
            },
            onError = { _error.postValue(true) },
            onLoading = { _loading.postValue(true) }
        )
    }

    private suspend fun createClassicStory(characterId: Int, characterList: List<Character?>){
        val firstCharacter = getCharacter(characterId)

        trueCharacter = firstCharacter?.name

        setOptions(
            listOf(
                firstCharacter,
                characterList[FIRST_CHARACTER_ID],
                characterList[SECOND_CHARACTER_ID],
                characterList[THIRD_CHARACTER_ID]
            )
        )
    }

    private suspend fun getCharacter(id: Int): Character? {
        val character = narutoRepository.getCharacter(charcerId = id)
        return  character.data
    }

    private fun akatsukiGame() {
        getDataCall(
            dataCall = { getFourAkatsukiCharacter() },
            onSuccess = { characterList ->
                if (characterList != null) {
                    askFamily(characterList).also { _loading.postValue(false) }.also {
                        _questionNumber.postValue(_questionNumber.value?.plus(1))
                    }
                }
            },
            onError = { _error.postValue(true) },
            onLoading = { _loading.postValue(true) }
        )
    }

    private suspend fun getFourAkatsukiCharacter(): Resource<List<Character>> {
        var firstCharacter: Character
        var secondCharacter: Character
        var thirdCharacter: Character
        var lastCharacter: Character
        while (true) {
            val charList = narutoRepository.getAkatsukiList(AKATSUKI_SIZE)
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
        for (i in 1..6) {
            val character =
                charList.data?.akatsuki?.get(getRandom(from = 0, includeUntil = AKATSUKI_SIZE - 1))
            if (character?.images?.isEmpty() == false) {
                return character
            }
        }
        val randomAkatsuki = mockRepository.getRandomCharacterFromRaw(R.raw.akatsuki)
        return randomAkatsuki!!
    }

    private suspend fun getThreeRandomCharacter(characterId: Int): Resource<List<Character?>> {
        var firstCharacter: Character?
        for (i in 1..20) {
            val selectedCharacters =
                mutableSetOf<Character?>()
            val filteredList = popularCharacterList.filter { it != characterId }
            val randomIds = filteredList.shuffled().take(STORY_CHARACTER_SIZE)
            firstCharacter = getCharacter(randomIds[FIRST_CHARACTER_ID])
            selectedCharacters.add(firstCharacter)
            selectedCharacters.add(getCharacter(randomIds[SECOND_CHARACTER_ID]))
            selectedCharacters.add(getCharacter(randomIds[THIRD_CHARACTER_ID]))
            if (selectedCharacters.size == STORY_CHARACTER_SIZE
            ) {
                return Resource.success(
                    selectedCharacters.toList()
                )
            }
        }
        return Resource.error(null)
    }

    private suspend fun getFourRandomCharacter(): Resource<List<Character?>> {
        var firstCharacter: Character?
        for (i in 1..20) {
            val selectedCharacters =
                mutableSetOf<Character?>()
            firstCharacter = getRandomCharacter()
            if (firstCharacter?.family?.getFirstNonNullField() != null) {
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

    private suspend fun getRandomCharacter(): Character? {
        val charList = getRandomCharList()
        return charList?.get(getRandom(includeUntil = charList.size - 1))
    }

    private suspend fun getRandomCharList(): List<Character>? {
        return narutoRepository.getCharacterList(
            getRandom(
                includeUntil = CHARACTER_PAGE_RANGE
            )
        ).data?.characters?.filter { character -> character.images?.isEmpty() == false }
    }

    private fun askClan(characterList: List<Character?>) {
        _questionId.postValue(ASK_CLAN_ID)

        val firstCharacter = characterList[FIRST_CHARACTER_ID]

        if (firstCharacter != null) {
            _questionText.postValue(
                firstCharacter.personal?.clan?.get(0)
            )
            trueCharacter = firstCharacter.name
        }

        setOptions(
            listOf(
                firstCharacter,
                characterList[SECOND_CHARACTER_ID],
                characterList[THIRD_CHARACTER_ID],
                characterList[LAST_CHARACTER_ID]
            )
        )
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
                _error.postValue(true)
            },
            onLoading = {
                _loading.postValue(true)
            }
        )
    }

    private suspend fun getFourClanCharacter(): Resource<List<Character?>> {
        val clanList = narutoRepository.getClanList(CLAN_PAGE_SIZE)
        val clanIdList = getRandomNumList(4, CLAN_PAGE_SIZE - 1)
        val firstClan = clanList.data?.clans?.get(clanIdList[0])
        val secondClan = clanList.data?.clans?.get(clanIdList[1])
        val thirdClan = clanList.data?.clans?.get(clanIdList[2])
        val lastClan = clanList.data?.clans?.get(clanIdList[3])
        return setGroupModel(listOf(firstClan, secondClan, thirdClan, lastClan))
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
            onError = { _error.postValue(true) },
            onLoading = { _loading.postValue(true) }
        )
    }

    private fun askTeam(characterList: List<Character?>) {
        _questionId.postValue(ASK_TEAM_ID)

        val firstCharacter = characterList[FIRST_CHARACTER_ID]

        if (firstCharacter != null) {
            _questionText.postValue(
                firstCharacter.personal?.team?.get(0)
            )
            trueCharacter = firstCharacter.name
        }
        setOptions(
            listOf(
                firstCharacter,
                characterList[SECOND_CHARACTER_ID],
                characterList[THIRD_CHARACTER_ID],
                characterList[LAST_CHARACTER_ID]
            )
        )
    }

    private suspend fun getFourTeamCharacter(): Resource<List<Character?>> {
        val teamList = narutoRepository.getTeamList(TEAM_PAGE_SIZE)
        val teamIdList = getRandomNumList(4, TEAM_PAGE_SIZE - 1)
        val firstTeam = teamList.data?.teams?.get(teamIdList[0])
        val secondTeam = teamList.data?.teams?.get(teamIdList[1])
        val thirdTeam = teamList.data?.teams?.get(teamIdList[2])
        val lastTeam = teamList.data?.teams?.get(teamIdList[3])
        return setGroupModel(listOf(firstTeam, secondTeam, thirdTeam, lastTeam))
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
            onError = { _error.postValue(true) },
            onLoading = { _loading.postValue(true) }
        )
    }

    private fun askJinckuri(characterList: List<Character?>) {
        _questionId.postValue(ASK_JINCURIKI_ID)

        val firstCharacter = characterList[FIRST_CHARACTER_ID]

        if (firstCharacter != null) {
            _questionText.postValue(
                firstCharacter.personal?.jinchuriki?.get(0)
            )
            trueCharacter = firstCharacter.name
        }
        setOptions(
            listOf(
                firstCharacter,
                characterList[SECOND_CHARACTER_ID],
                characterList[THIRD_CHARACTER_ID],
                characterList[LAST_CHARACTER_ID]
            )
        )
    }

    private suspend fun getFourTailCharacter(): Resource<List<Character?>> {
        val tailList = narutoRepository.getTailedBeastList()
        val tailIdList = getRandomNumList(4, TAIL_PAGE_RANGE)
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

    private fun setOptions(characterList: List<Character?>) {
        val options = listOf(
            OptionModel(optionId = FIRST_OPTION_ID, option = _firstOption),
            OptionModel(optionId = SECOND_OPTION_ID, option = _secondOption),
            OptionModel(optionId = THIRD_OPTION_ID, option = _thirdOption),
            OptionModel(optionId = LAST_OPTION_ID, option = _lastOption)
        ).shuffled()
        trueAnswerId = options[FIRST_OPTION_ID].optionId

        setOptionTrue(characterList[FIRST_CHARACTER_ID], options[FIRST_OPTION_ID])
        setOptionWrong(characterList[SECOND_CHARACTER_ID], options[SECOND_OPTION_ID])
        setOptionWrong(characterList[THIRD_CHARACTER_ID], options[THIRD_OPTION_ID])
        setOptionWrong(characterList[LAST_CHARACTER_ID], options[LAST_OPTION_ID])
    }

    private fun setOptionTrue(
        character: Character?,
        option: OptionModel
    ) {
        character?.let { getCharacter ->
            option.option.postValue(
                SelectionModel(
                    imageUrl = if (!getCharacter.images.isNullOrEmpty()) {
                        getCharacter.images[0]
                    } else {
                        ""
                    },
                    characterName = getCharacter.name,
                    trueAnswer = true
                )
            )
        }
    }

    private fun setOptionWrong(
        character: Character?,
        option: OptionModel
    ) {
        character?.let { getCharacter ->
            option.option.postValue(
                SelectionModel(
                    imageUrl = if (!getCharacter.images.isNullOrEmpty()) {
                        getCharacter.images[0]
                    } else {
                        ""
                    },
                    characterName = getCharacter.name,
                    trueAnswer = false
                )
            )
        }
    }

    private suspend fun setGroupModel(
        groupModelList: List<GroupModel?>,
    ): Resource<List<Character?>> {
        val firstModel = groupModelList[FIRST_CHARACTER_ID]
        val secondModel = groupModelList[SECOND_CHARACTER_ID]
        val thirdModel = groupModelList[THIRD_CHARACTER_ID]
        val lastModel = groupModelList[LAST_CHARACTER_ID]
        var firstCharacter: Character?
        var secondCharacter: Character?
        var thirdCharacter: Character?
        var lastCharacter: Character?

        withContext(Dispatchers.IO) {
            val getFirstCharacter = async {
                getCharacter(firstModel)
            }
            firstCharacter = getFirstCharacter.await()

            val getSecondCharacter = async {
                getCharacter(secondModel)
            }

            secondCharacter = getSecondCharacter.await()

            val getThirdCharacter = async {
                getCharacter(thirdModel)
            }
            thirdCharacter = getThirdCharacter.await()

            val getLastCharacter = async {
                getCharacter(lastModel)
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

    private suspend fun getCharacter(groupModel: GroupModel?): Character? {
        return groupModel?.characters?.get(
            getRandom(
                includeUntil = groupModel.characters.size - 1
            )
        )?.let { narutoRepository.getCharacter(it) }?.data
    }
}