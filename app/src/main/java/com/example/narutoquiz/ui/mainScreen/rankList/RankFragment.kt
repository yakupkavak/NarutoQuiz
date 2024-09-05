package com.example.narutoquiz.ui.mainScreen.rankList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.narutoquiz.databinding.FragmentRankBinding
import com.example.narutoquiz.ui.extension.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RankFragment : Fragment() {

    private var _binding: FragmentRankBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RankAdapter
    private val viewModel: RankViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = RankAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRankBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserve()
        viewModel.getRankList()
    }

    private fun setObserve() {
        observe(viewModel.success) { rankList ->
            rankList?.let {
                adapter.submit(rankList)
            }
            binding.rvRank.adapter = adapter
        }
        observe(viewModel.loading){
            if (it){
                println("yükleniyor")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}