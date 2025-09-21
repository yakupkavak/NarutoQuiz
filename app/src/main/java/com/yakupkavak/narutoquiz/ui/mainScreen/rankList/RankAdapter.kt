package com.yakupkavak.narutoquiz.ui.mainScreen.rankList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.yakupkavak.narutoquiz.R
import com.yakupkavak.narutoquiz.data.network.model.RankRowModel
import com.yakupkavak.narutoquiz.databinding.RankRowBinding

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
    }

    inner class RankViewHolder(private val binding: RankRowBinding) : ViewHolder(binding.root) {
        fun bind(data: RankRowModel) {
            with(binding) {
                tvUserRank.text = data.userRank.toString()
                tvUserName.text = data.userName
                tvUserScore.text = data.userScore.toString()
            }
        }

        fun valuableBind(data: RankRowModel, positionNumber: Int) {
            with(binding) {
                when (positionNumber) {
                    0 -> {
                        root.setBackgroundResource(R.drawable.first_rank_design)
                        ivUserRank.setImageResource(R.drawable.winner)
                        ivUserRank.isVisible = true
                    }

                    1 -> {
                        root.setBackgroundResource(R.drawable.second_rank_design)
                        ivUserRank.setImageResource(R.drawable.second)
                        ivUserRank.isVisible = true
                    }

                    2 -> {
                        root.setBackgroundResource(R.drawable.third_rank_design)
                        ivUserRank.setImageResource(R.drawable.third)
                        ivUserRank.isVisible = true
                    }
                }
                tvUserRank.text = data.userRank.toString()
                tvUserName.text = data.userName
                tvUserScore.text = data.userScore.toString()
            }
        }

        fun clearBind() {
            with(binding) {
                ivUserRank.isVisible = false
                root.setBackgroundResource(R.drawable.feed_row_design)
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
        holder.clearBind()
        if (position < 3) {
            holder.valuableBind(asyncListDiffer.currentList[position], position)
        } else {
            holder.bind(asyncListDiffer.currentList[position])
        }
    }
}