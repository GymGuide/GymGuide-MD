package com.example.gymguide.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gymguide.R
import com.example.gymguide.data.Rank
import com.example.gymguide.databinding.ItemRankBinding

class RankAdapter :
    RecyclerView.Adapter<RankAdapter.RankViewHolder>() {

    private var clickListener: ClickListener? = null

    inner class RankViewHolder(val binding: ItemRankBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Rank>() {
        override fun areItemsTheSame(oldItem: Rank, newItem: Rank): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Rank, newItem: Rank): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var ranks: List<Rank>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun getItemCount() = ranks.size

    fun setClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewHolder {
        val binding = ItemRankBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RankViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RankViewHolder, position: Int) {
        val rank = ranks[position]
        val context = holder.itemView.context

        holder.binding.apply {
            tvUserName.text = rank.name
            tvUserExp.text = context.getString(R.string.user_exp_format, rank.exp.toString())

            Glide.with(root.context)
                .load(rank.picture)
                .centerCrop()
                .into(ivUser)
        }
        holder.itemView.setOnClickListener { clickListener?.onItemClicked(rank) }
    }

    interface ClickListener {
        fun onItemClicked(rank: Rank)
    }
}
