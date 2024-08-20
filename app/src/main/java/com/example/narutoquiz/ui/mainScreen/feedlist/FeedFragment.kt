package com.example.narutoquiz.ui.mainScreen.feedlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.narutoquiz.R
import com.example.narutoquiz.databinding.FragmentFeedBinding
import com.example.narutoquiz.databinding.FragmentSignupBinding
import com.example.narutoquiz.ui.extension.navigate

class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = FeedAdapter().also { adapter ->
            adapter.onItemClick = {
                navigate(FeedFragmentDirections.actionFeedFragmentToGameFragment(it))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.submit(GenerateRowList())
        binding.rvFeed.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}