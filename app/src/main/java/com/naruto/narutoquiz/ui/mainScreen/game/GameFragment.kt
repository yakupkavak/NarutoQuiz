package com.naruto.narutoquiz.ui.mainScreen.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.naruto.narutoquiz.R
import com.naruto.narutoquiz.data.network.model.DialogModel
import com.naruto.narutoquiz.databinding.FragmentGameBinding
import com.naruto.narutoquiz.domain.extension.getUrl
import com.naruto.narutoquiz.ui.extension.navigate
import com.naruto.narutoquiz.ui.extension.observe
import com.naruto.narutoquiz.ui.extension.popBackStack
import com.naruto.narutoquiz.ui.extension.setBackground
import com.naruto.narutoquiz.ui.extension.showToast
import com.naruto.narutoquiz.ui.mainScreen.game.GameConst.AskClanId
import com.naruto.narutoquiz.ui.mainScreen.game.GameConst.AskFamilyId
import com.naruto.narutoquiz.ui.mainScreen.game.GameConst.AskJinckuriId
import com.naruto.narutoquiz.ui.mainScreen.game.GameConst.AskTeamId
import com.naruto.narutoquiz.ui.mainScreen.game.GameConst.AskVoiceActorId
import com.naruto.narutoquiz.ui.mainScreen.game.GameConst.ChallangeGameId
import com.naruto.narutoquiz.ui.mainScreen.game.GameConst.FirstOptionId
import com.naruto.narutoquiz.ui.mainScreen.game.GameConst.LastOptionId
import com.naruto.narutoquiz.ui.mainScreen.game.GameConst.SecondOptionId
import com.naruto.narutoquiz.ui.mainScreen.game.GameConst.ThirdOptionId
import com.naruto.narutoquiz.ui.mainScreen.main.ErrorDialogFragment
import com.naruto.narutoquiz.ui.mainScreen.main.GameDialogFragment
import com.naruto.narutoquiz.ui.mainScreen.main.HintDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private val args: GameFragmentArgs by navArgs()
    private val viewModel: GameViewModel by viewModels()
    private var selectedOptionId = -1
    private var gameState = 0
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
        setObserve()
    }

    private fun setDialog(trueCount: Int, wrongCount: Int) {
        val newFragment = GameDialogFragment(
            DialogModel(trueCount, wrongCount),
            playAgain = { viewModel.startGame() },
            mainScreen = { navigate(GameFragmentDirections.actionGameFragmentToFeedFragment()) })
        newFragment.show(parentFragmentManager, "game")
    }

    private fun setObserve() {
        observe(viewModel.currentGameTopic) {
            binding.tvTopic.text = it
        }
        observe(viewModel.questionId) { getQuestionId ->
            questionId = getQuestionId
        }
        observe(viewModel.questionText) {
            with(binding) {
                when (questionId) {
                    AskFamilyId -> {
                        tvQuestion.text = getString(R.string.family_question, it)
                    }

                    AskVoiceActorId -> {
                        tvQuestion.text = getString(R.string.voice_question, it)
                    }

                    AskClanId -> {
                        tvQuestion.text = getString(R.string.clan_question, it)
                    }

                    AskTeamId -> {
                        tvQuestion.text = getString(R.string.team_question, it)
                    }

                    AskJinckuriId -> {
                        tvQuestion.text = getString(R.string.jinckuri_question, it)
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
                    selectedOptionId = FirstOptionId
                }
            }
        }
        observe(viewModel.currentGameId){
            currentGameId = it
        }
        observe(viewModel.secondOption) {
            with(binding) {
                ivTwo.getUrl(it.imageUrl ?: "")
                tvTwo.text = it.characterName ?: ""
                cvTwo.setOnClickListener {
                    clearSelection()
                    cvTwo.setBackground(R.color.selected_answer)
                    selectedOptionId = SecondOptionId
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
                    selectedOptionId = ThirdOptionId
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
                    selectedOptionId = LastOptionId
                }
            }
        }
        observe(viewModel.questionNumber) {
            binding.linearProgress.progress = it
        }
        observe(viewModel.answerSelection) {
            it.trueAnswer?.let { trueAnswerId ->
                when (trueAnswerId) {
                    FirstOptionId -> {
                        binding.cvOne.setBackground(R.color.true_answer)
                    }

                    SecondOptionId -> {
                        binding.cvTwo.setBackground(R.color.true_answer)
                    }

                    ThirdOptionId -> {
                        binding.cvThree.setBackground(R.color.true_answer)
                    }

                    LastOptionId -> {
                        binding.cvFour.setBackground(R.color.true_answer)
                    }
                }
            }
            it.falseAnswer?.let { falseAnswerId ->
                when (falseAnswerId) {
                    FirstOptionId -> {
                        binding.cvOne.setBackground(R.color.false_answer)
                    }

                    SecondOptionId -> {
                        binding.cvTwo.setBackground(R.color.false_answer)
                    }

                    ThirdOptionId -> {
                        binding.cvThree.setBackground(R.color.false_answer)
                    }

                    LastOptionId -> {
                        binding.cvFour.setBackground(R.color.false_answer)
                    }
                }
            }
        }
        observe(viewModel.loading) {
            if (it) {
                with(binding) {
                    fabGemini.isVisible = false
                    group.isVisible = false
                    lottieAnimationLoading.isVisible = true
                    lottieAnimationNaruto.isVisible = true
                    lottieAnimationLoading.playAnimation()
                    lottieAnimationNaruto.playAnimation()
                }
            } else {
                with(binding) {
                    fabGemini.isVisible = currentGameId != ChallangeGameId
                    group.isVisible = true
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
                }
            }
        }
        observe(viewModel.hintText){ text ->
            val newFragment = HintDialogFragment(
                hintText = text)
            newFragment.show(parentFragmentManager, "game")
            hintLoaded()
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

    private fun loadingHint(){
        with(binding.fabGemini){
            setImageResource(R.drawable.spinnerblack)
            isClickable = false
        }
    }

    private fun hintLoaded(){
        with(binding.fabGemini){
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
            }
            btnCheck.setOnClickListener {
                if (gameState == 0) {
                    if (selectedOptionId == -1) {
                        showToast(getString(R.string.select))
                    } else {
                        viewModel.checkQuestion(selectedOptionId)
                        gameState = 1
                        btnCheck.text = getString(R.string.next)
                        falseFocusable()
                        selectedOptionId = -1
                    }
                } else {
                    clearSelection()
                    gameState = 0
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