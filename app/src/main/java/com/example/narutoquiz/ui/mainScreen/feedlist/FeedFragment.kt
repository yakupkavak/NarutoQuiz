package com.example.narutoquiz.ui.mainScreen.feedlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.narutoquiz.data.model.DialogModel
import com.example.narutoquiz.databinding.FragmentFeedBinding
import com.example.narutoquiz.ui.extension.navigate
import com.example.narutoquiz.ui.mainScreen.game.GameDialogFragment

class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = FeedAdapter().also { adapter ->
            adapter.onItemClick = { gameId, gameTopic ->
                navigate(FeedFragmentDirections.actionFeedFragmentToGameFragment(gameId, gameTopic))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.submit(getRowModelList())
        binding.rvFeed.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}