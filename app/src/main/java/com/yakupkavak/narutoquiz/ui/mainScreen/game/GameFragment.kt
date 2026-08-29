package com.yakupkavak.narutoquiz.ui.mainScreen.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.yakupkavak.narutoquiz.R
import com.yakupkavak.narutoquiz.data.network.model.DialogModel
import com.yakupkavak.narutoquiz.databinding.FragmentGameBinding
import com.yakupkavak.narutoquiz.domain.extension.getUrl
import com.yakupkavak.narutoquiz.ui.extension.loadGif
import com.yakupkavak.narutoquiz.ui.extension.navigate
import com.yakupkavak.narutoquiz.ui.extension.observe
import com.yakupkavak.narutoquiz.ui.extension.popBackStack
import com.yakupkavak.narutoquiz.ui.extension.setBackground
import com.yakupkavak.narutoquiz.ui.extension.showToast
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.ASK_CLAN_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.ASK_FAMILY_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.ASK_JINCURIKI_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.ASK_STORY_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.ASK_TEAM_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.ASK_VOICE_ACTOR_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.CHALLENGE_GAME_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.FIRST_OPTION_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.LAST_OPTION_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.SECOND_OPTION_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.THIRD_OPTION_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.FIRST_CHALLENGE_LEVEL
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.LAST_CHALLENGE_LEVEL
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.SECOND_CHALLENGE_LEVEL
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.STORY_GAME_ID
import com.yakupkavak.narutoquiz.ui.mainScreen.game.GameConst.THIRD_CHALLENGE_LEVEL
import com.yakupkavak.narutoquiz.ui.mainScreen.main.ErrorDialogFragment
import com.yakupkavak.narutoquiz.ui.mainScreen.main.GameDialogFragment
import com.yakupkavak.narutoquiz.ui.mainScreen.main.HintDialogFragment
import com.yakupkavak.narutoquiz.ui.mainScreen.main.MainScreenActivity
import com.yakupkavak.narutoquiz.ui.mainScreen.main.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private val args: GameFragmentArgs by navArgs()
    private val viewModel: GameViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var selectedOptionId = -1
    private var canNextQuestion = false
    private var questionId = 0
    private var currentGameId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initializeGame(args.gameId, args.gameTopic)
        setOnClick()
        setObserveViewModel()
        setObserveSharedViewModel()
    }

    private fun setDialog(trueCount: Int, wrongCount: Int) {
        val newFragment = GameDialogFragment(
            DialogModel(trueCount, wrongCount),
            playAgain = { viewModel.startGame() },
            mainScreen = { navigate(GameFragmentDirections.actionGameFragmentToFeedFragment()) })
        newFragment.show(parentFragmentManager, "game")
    }

    private fun setObserveViewModel() {
        observe(viewModel.currentGameTopic) { gameTopic ->
            binding.tvTopic.setText(gameTopic)
        }
        observe(viewModel.questionId) { getQuestionId ->
            questionId = getQuestionId
        }
        observe(viewModel.questionText) {
            with(binding) {
                when (questionId) {
                    ASK_FAMILY_ID -> {
                        tvQuestion.text = getString(R.string.family_question, it)
                    }

                    ASK_VOICE_ACTOR_ID -> {
                        tvQuestion.text = getString(R.string.voice_question, it)
                    }

                    ASK_CLAN_ID -> {
                        tvQuestion.text = getString(R.string.clan_question, it)
                    }

                    ASK_TEAM_ID -> {
                        tvQuestion.text = getString(R.string.team_question, it)
                    }

                    ASK_JINCURIKI_ID -> {
                        tvQuestion.text = getString(R.string.jinckuri_question, it)
                    }
                    ASK_STORY_ID -> {
                        tvQuestion.text = it
                    }
                }
            }
        }
        observe(viewModel.error) {
            if (it) {
                val newFragment = ErrorDialogFragment(
                    onClick = { navigate(GameFragmentDirections.actionGameFragmentToFeedFragment()) })
                newFragment.show(parentFragmentManager, "game")
            }
        }
        observe(viewModel.firstOption) {
            with(binding) {
                ivOne.getUrl(it.imageUrl ?: "")
                tvOne.text = it.characterName ?: ""
                cvOne.setOnClickListener {
                    clearSelection()
                    cvOne.setBackground(R.color.selected_answer)
                    selectedOptionId = FIRST_OPTION_ID
                }
            }
        }
        observe(viewModel.currentGameId) { gameId ->
            currentGameId = gameId
            if (gameId == CHALLENGE_GAME_ID) {
                with(binding) {
                    linearProgress.isVisible = false
                    tvQuestionNumber.isVisible = true
                }
            }
        }
        observe(viewModel.secondOption) {
            with(binding) {
                ivTwo.getUrl(it.imageUrl ?: "")
                tvTwo.text = it.characterName ?: ""
                cvTwo.setOnClickListener {
                    clearSelection()
                    cvTwo.setBackground(R.color.selected_answer)
                    selectedOptionId = SECOND_OPTION_ID
                }
            }
        }
        observe(viewModel.thirdOption) {
            with(binding) {
                ivThree.getUrl(it.imageUrl ?: "")
                tvThree.text = it.characterName ?: ""
                cvThree.setOnClickListener {
                    clearSelection()
                    cvThree.setBackground(R.color.selected_answer)
                    selectedOptionId = THIRD_OPTION_ID
                }
            }
        }
        observe(viewModel.lastOption) {
            with(binding) {
                ivFour.getUrl(it.imageUrl ?: "")
                tvFour.text = it.characterName ?: ""
                cvFour.setOnClickListener {
                    clearSelection()
                    cvFour.setBackground(R.color.selected_answer)
                    selectedOptionId = LAST_OPTION_ID
                }
            }
        }
        observe(viewModel.questionNumber) { questionNumber ->
            with(binding) {
                linearProgress.progress = questionNumber
                tvQuestionNumber.text = getString(R.string.question_number, questionNumber)
                if (questionNumber > LAST_CHALLENGE_LEVEL) {
                    tvQuestionNumber.setTextAppearance(R.style.LastChallengeLevel)
                } else if (questionNumber > THIRD_CHALLENGE_LEVEL) {
                    tvQuestionNumber.setTextAppearance(R.style.ThirdChallengeLevel)
                } else if (questionNumber > SECOND_CHALLENGE_LEVEL) {
                    tvQuestionNumber.setTextAppearance(R.style.SecondChallengeLevel)
                } else if (questionNumber > FIRST_CHALLENGE_LEVEL) {
                    tvQuestionNumber.setTextAppearance(R.style.FirstChallengeLevel)
                }
            }
        }
        observe(viewModel.answerSelection) {
            it.trueAnswer?.let { trueAnswerId ->
                when (trueAnswerId) {
                    FIRST_OPTION_ID -> {
                        binding.cvOne.setBackground(R.color.true_answer)
                    }

                    SECOND_OPTION_ID -> {
                        binding.cvTwo.setBackground(R.color.true_answer)
                    }

                    THIRD_OPTION_ID -> {
                        binding.cvThree.setBackground(R.color.true_answer)
                    }

                    LAST_OPTION_ID -> {
                        binding.cvFour.setBackground(R.color.true_answer)
                    }
                }
            }
            it.falseAnswer?.let { falseAnswerId ->
                when (falseAnswerId) {
                    FIRST_OPTION_ID -> {
                        binding.cvOne.setBackground(R.color.false_answer)
                    }

                    SECOND_OPTION_ID -> {
                        binding.cvTwo.setBackground(R.color.false_answer)
                    }

                    THIRD_OPTION_ID -> {
                        binding.cvThree.setBackground(R.color.false_answer)
                    }

                    LAST_OPTION_ID -> {
                        binding.cvFour.setBackground(R.color.false_answer)
                    }
                }
            }
        }
        observe(viewModel.loading) {
            if (it) {
                with(binding) {
                    if (currentGameId == CHALLENGE_GAME_ID) {
                        challengeGroup.isVisible = false
                    } else {
                        classicGroup.isVisible = false
                    }
                    fabGemini.isVisible = false
                    lottieAnimationLoading.isVisible = true
                    lottieAnimationNaruto.isVisible = true
                    lottieAnimationLoading.playAnimation()
                    lottieAnimationNaruto.playAnimation()
                }
            } else {
                with(binding) {
                    if (currentGameId == CHALLENGE_GAME_ID) {
                        challengeGroup.isVisible = true
                    } else {
                        classicGroup.isVisible = true
                    }
                    fabGemini.isVisible = currentGameId != CHALLENGE_GAME_ID
                    lottieAnimationLoading.isVisible = false
                    lottieAnimationNaruto.isVisible = false
                    lottieAnimationLoading.cancelAnimation()
                    lottieAnimationNaruto.cancelAnimation()
                }
            }
        }
        observe(viewModel.finishGame) { gameState ->
            gameState[0]?.let { trueCount ->
                gameState[1]?.let { wrongCount ->
                    setDialog(trueCount, wrongCount)
                    binding.btnCheck.text = getString(R.string.game_over)
                    (activity as? MainScreenActivity)?.onGameFinished()
                }
            }
        }
        observe(viewModel.hintText) { text ->
            val newFragment = HintDialogFragment(
                hintText = text
            )
            newFragment.show(parentFragmentManager, "game")
            hintLoaded()
        }
    }

    private fun setObserveSharedViewModel() {
        observe(sharedViewModel.tokenCount) { tokenCount ->
            if (tokenCount == 0) {
                binding.fabGemini.isEnabled = false
            }
            binding.tvTokenCount.text = tokenCount.toString()
        }
    }

    private fun clearSelection() {
        with(binding) {
            cvOne.setBackground(R.color.transparent)
            cvTwo.setBackground(R.color.transparent)
            cvThree.setBackground(R.color.transparent)
            cvFour.setBackground(R.color.transparent)
        }
    }

    private fun falseFocusable() {
        with(binding) {
            cvOne.isClickable = false
            cvTwo.isClickable = false
            cvThree.isClickable = false
            cvFour.isClickable = false
        }
    }

    private fun trueFocusable() {
        with(binding) {
            cvOne.isClickable = true
            cvTwo.isClickable = true
            cvThree.isClickable = true
            cvFour.isClickable = true
        }
    }

    private fun loadingHint() {
        with(binding.fabGemini) {
            loadGif(sourceId = R.drawable.spinnerblack, view = this)
            isClickable = false
        }
    }

    private fun hintLoaded() {
        with(binding.fabGemini) {
            setImageResource(R.drawable.lightblub)
            isClickable = true
        }
    }

    private fun setOnClick() {
        with(binding) {
            fabClose.setOnClickListener {
                popBackStack()
            }
            fabGemini.setOnClickListener {
                viewModel.getHint()
                loadingHint()
                sharedViewModel.showHint()
            }
            btnCheck.setOnClickListener {
                if (!canNextQuestion) {
                    if (selectedOptionId == -1) {
                        showToast(getString(R.string.select))
                    } else {
                        viewModel.checkQuestion(selectedOptionId)
                        canNextQuestion = true
                        btnCheck.text = getString(R.string.next)
                        falseFocusable()
                        selectedOptionId = -1
                    }
                } else { //doğru yanlış cevap gösterildikten sonra next'e geçiş
                    clearSelection()
                    canNextQuestion = false
                    btnCheck.text = getString(R.string.check)
                    trueFocusable()
                    viewModel.nextQuestion()
                    selectedOptionId = -1
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}