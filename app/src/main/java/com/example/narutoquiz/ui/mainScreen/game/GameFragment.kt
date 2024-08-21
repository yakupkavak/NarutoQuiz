package com.example.narutoquiz.ui.mainScreen.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.narutoquiz.databinding.FragmentGameBinding
import com.example.narutoquiz.domain.extension.getUrl
import com.example.narutoquiz.ui.extension.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private val args: GameFragmentArgs by navArgs()
    private val viewModel : GameViewModel by viewModels()
    private var gameId: Int = 0
    private var gameTopic: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameId = args.gameId
        gameTopic = args.gameTopic
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
        binding.tvTopic.text = gameTopic
        setOnClick()
        setObserve()
    }

    private fun setObserve(){
        viewModel.startGame(gameId)
        observe(viewModel.questionText){
            binding.tvQuestion.text = it
        }
        observe(viewModel.firstOption){
            with(binding){
                ivOne.getUrl(it.imageUrl ?: "")
                println(it.imageUrl)
                tvOne.text = it.characterName ?: "Null"
            }
        }
        observe(viewModel.secondOption){
            with(binding){
                ivTwo.getUrl(it.imageUrl ?: "")
                tvTwo.text = it.characterName ?: "Null"
            }
        }
        observe(viewModel.thirdOption){
            with(binding){
                ivThree.getUrl(it.imageUrl ?: "")
                tvThree.text = it.characterName ?: "Null"
            }
        }
        observe(viewModel.lastOption){
            with(binding){
                ivFour.getUrl(it.imageUrl ?: "")
                tvFour.text = it.characterName ?: "Null"
            }
        }
    }

    private fun setOnClick(){
        with(binding){
            fabClose.setOnClickListener {
                findNavController().popBackStack()
            }
            btnCheck.setOnClickListener {
                linearProgress.progress += 1
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}