package com.naruto.narutoquiz.ui.mainScreen.rankList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.naruto.narutoquiz.data.network.model.RankRowModel
import com.naruto.narutoquiz.databinding.RankRowBinding

class RankAdapter : Adapter<RankAdapter.RankViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<RankRowModel>() {
        override fun areItemsTheSame(oldItem: RankRowModel, newItem: RankRowModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: RankRowModel, newItem: RankRowModel): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    fun submit(items: ArrayList<RankRowModel>) {
        asyncListDiffer.submitList(items)
        println(items)
    }

    inner class RankViewHolder(private val binding: RankRowBinding) : ViewHolder(binding.root) {
        fun bind(data: RankRowModel) {
            with(binding) {
                tvUserRank.text = data.userRank.toString()
                tvUserName.text = data.userName
                tvUserScore.text = data.userScore.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewHolder {
        return RankViewHolder(
            RankRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: RankViewHolder, position: Int) {
        holder.bind(asyncListDiffer.currentList[position])
    }
}