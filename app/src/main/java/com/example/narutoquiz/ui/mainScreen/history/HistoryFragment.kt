package com.example.narutoquiz.ui.mainScreen.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.narutoquiz.R
import com.example.narutoquiz.databinding.FragmentRankBinding
import com.example.narutoquiz.ui.mainScreen.rankList.RankAdapter

class HistoryFragment : Fragment() {

    private var _binding: FragmentRankBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = HistoryAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //adapter.submit()
        binding.rvRank.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}