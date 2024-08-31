package com.example.narutoquiz.ui.mainScreen.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.narutoquiz.R
import com.example.narutoquiz.data.model.DialogModel
import com.example.narutoquiz.databinding.FragmentGameBinding
import com.example.narutoquiz.domain.extension.getUrl
import com.example.narutoquiz.ui.extension.navigate
import com.example.narutoquiz.ui.extension.observe
import com.example.narutoquiz.ui.extension.popBackStack
import com.example.narutoquiz.ui.extension.setBackground
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private val args: GameFragmentArgs by navArgs()
    private val viewModel: GameViewModel by viewModels()
    private var selectedOptionId = -1
    private var gameState = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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
        val newFragment = GameDialogFragment(DialogModel(trueCount, wrongCount),
            playAgain = { viewModel.startGame() },
            mainScreen = { navigate(GameFragmentDirections.actionGameFragmentToFeedFragment()) })
        newFragment.show(parentFragmentManager, "game")
    }

    private fun setObserve() {
        viewModel.startGame()

        observe(viewModel.currentGameTopic) {
            binding.tvTopic.text = it
        }
        observe(viewModel.questionText) {
            binding.tvQuestion.text = it
        }
        observe(viewModel.firstOption) {
            with(binding) {
                ivOne.getUrl(it.imageUrl ?: "")
                tvOne.text = it.characterName ?: ""
                cvOne.setOnClickListener {
                    clearSelection()
                    cvOne.setBackground(requireContext(), R.color.selected_answer)
                    selectedOptionId = 0
                }
            }
        }
        observe(viewModel.secondOption) {
            with(binding) {
                ivTwo.getUrl(it.imageUrl ?: "")
                tvTwo.text = it.characterName ?: ""
                cvTwo.setOnClickListener {
                    clearSelection()
                    cvTwo.setBackground(requireContext(), R.color.selected_answer)
                    selectedOptionId = 1
                }
            }
        }
        observe(viewModel.thirdOption) {
            with(binding) {
                ivThree.getUrl(it.imageUrl ?: "")
                tvThree.text = it.characterName ?: ""
                cvThree.setOnClickListener {
                    clearSelection()
                    cvThree.setBackground(requireContext(), R.color.selected_answer)
                    selectedOptionId = 2
                }
            }
        }
        observe(viewModel.lastOption) {
            with(binding) {
                ivFour.getUrl(it.imageUrl ?: "")
                tvFour.text = it.characterName ?: ""
                cvFour.setOnClickListener {
                    clearSelection()
                    cvFour.setBackground(requireContext(), R.color.selected_answer)
                    selectedOptionId = 3
                }
            }
        }
        observe(viewModel.questionNumber) {
            binding.linearProgress.progress = it
        }
        observe(viewModel.answerSelection) {
            it.trueAnswer?.let { trueAnswerId ->
                when (trueAnswerId) {
                    0 -> {
                        binding.cvOne.setBackground(requireContext(), R.color.true_answer)
                    }

                    1 -> {
                        binding.cvTwo.setBackground(requireContext(), R.color.true_answer)
                    }

                    2 -> {
                        binding.cvThree.setBackground(requireContext(), R.color.true_answer)
                    }

                    3 -> {
                        binding.cvFour.setBackground(requireContext(), R.color.true_answer)
                    }
                }
            }
            it.falseAnswer?.let { falseAnswerId ->
                when (falseAnswerId) {
                    0 -> {
                        binding.cvOne.setBackground(requireContext(), R.color.false_answer)
                    }

                    1 -> {
                        binding.cvTwo.setBackground(requireContext(), R.color.false_answer)
                    }

                    2 -> {
                        binding.cvThree.setBackground(requireContext(), R.color.false_answer)
                    }

                    3 -> {
                        binding.cvFour.setBackground(requireContext(), R.color.false_answer)
                    }
                }
            }
        }
        observe(viewModel.trueAnswer) {}
        observe(viewModel.falseAnswer) {}
        observe(viewModel.loading) {
            if (it) {
                setInvisible()
                with(binding) {
                    lottieAnimationLoading.isVisible = true
                    lottieAnimationNaruto.isVisible = true
                    lottieAnimationLoading.playAnimation()
                    lottieAnimationNaruto.playAnimation()
                }
            } else {
                setVisible()
                with(binding) {
                    lottieAnimationLoading.isVisible = false
                    lottieAnimationNaruto.isVisible = false
                    lottieAnimationLoading.cancelAnimation()
                    lottieAnimationNaruto.cancelAnimation()
                }
            }
        }
        observe(viewModel.finishGame) {
            setDialog(it[0], it[1])
        }
    }

    private fun clearSelection() {
        with(binding) {
            cvOne.setBackground(requireContext(), R.color.transparent)
            cvTwo.setBackground(requireContext(), R.color.transparent)
            cvThree.setBackground(requireContext(), R.color.transparent)
            cvFour.setBackground(requireContext(), R.color.transparent)
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

    private fun setInvisible() {
        with(binding) {
            tvQuestion.isVisible = false
            cvOne.isVisible = false
            cvTwo.isVisible = false
            cvThree.isVisible = false
            cvFour.isVisible = false
            tvOne.isVisible = false
            tvTwo.isVisible = false
            tvThree.isVisible = false
            tvFour.isVisible = false
            btnCheck.isVisible = false
        }
    }

    private fun setVisible() {
        with(binding) {
            tvQuestion.isVisible = true
            cvOne.isVisible = true
            cvTwo.isVisible = true
            cvThree.isVisible = true
            cvFour.isVisible = true
            tvOne.isVisible = true
            tvTwo.isVisible = true
            tvThree.isVisible = true
            tvFour.isVisible = true
            btnCheck.isVisible = true
        }
    }

    private fun setOnClick() {
        with(binding) {
            fabClose.setOnClickListener {
                popBackStack()
            }
            btnCheck.setOnClickListener {
                if (gameState == 0) {
                    if (selectedOptionId == -1) {
                        println("Allert dialog")
                    } else {
                        viewModel.checkQuestion(selectedOptionId)
                        gameState = 1
                        btnCheck.text = getString(R.string.next)
                        falseFocusable()
                    }
                } else {
                    clearSelection()
                    gameState = 0
                    btnCheck.text = getString(R.string.check)
                    trueFocusable()
                    viewModel.nextQuestion()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}