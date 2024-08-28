package com.example.narutoquiz.ui.mainScreen.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.narutoquiz.R
import com.example.narutoquiz.databinding.FragmentGameBinding
import com.example.narutoquiz.domain.extension.getUrl
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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
                }
            }
        }
        observe(viewModel.questionNumber) {
            binding.linearProgress.progress = it
        }
    }

    private fun clearSelection() {
        with(binding) {
            cvOne.setBackground(requireContext(), R.color.system_background)
            cvTwo.setBackground(requireContext(), R.color.system_background)
            cvThree.setBackground(requireContext(), R.color.system_background)
            cvFour.setBackground(requireContext(), R.color.system_background)
        }
    }

    private fun setOnClick() {
        with(binding) {
            fabClose.setOnClickListener {
                popBackStack()
            }
            btnCheck.setOnClickListener {
                viewModel.nextQuestion()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}