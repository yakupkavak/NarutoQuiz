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
    private var selectedOptionId = -1

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
        observe(viewModel.answerSelection){
            it.trueAnswer?.let { trueAnswerId -> // doğru seçtiğin zaman true_answer rengi oldu
                when(trueAnswerId){
                    0->{
                        binding.cvOne.setBackground(requireContext(),R.color.true_answer)
                    }
                    1->{
                        binding.cvTwo.setBackground(requireContext(),R.color.true_answer)
                    }
                    2->{
                        binding.cvThree.setBackground(requireContext(),R.color.true_answer)
                    }
                    3->{
                        binding.cvFour.setBackground(requireContext(),R.color.true_answer)
                    }
                }
            }
            it.falseAnswer?.let { falseAnswerId ->
                println("yanlış seçilen"+falseAnswerId)
                when(falseAnswerId){
                    0->{
                        binding.cvOne.setBackground(requireContext(),R.color.false_answer)
                    }
                    1->{
                        binding.cvTwo.setBackground(requireContext(),R.color.false_answer)
                    }
                    2->{
                        binding.cvThree.setBackground(requireContext(),R.color.false_answer)
                    }
                    3->{
                        binding.cvFour.setBackground(requireContext(),R.color.false_answer)
                    }
                }
            }
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
                if (selectedOptionId == -1){
                    println("Allert dialog")
                }else{
                    viewModel.checkQuestion(selectedOptionId)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}