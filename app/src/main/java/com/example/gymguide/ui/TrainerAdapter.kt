package com.example.gymguide.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gymguide.R
import com.example.gymguide.data.Trainer
import com.example.gymguide.databinding.ItemTrainerBinding

class TrainerAdapter :
    RecyclerView.Adapter<TrainerAdapter.TrainerViewHolder>() {

    private var clickListener: ClickListener? = null

    inner class TrainerViewHolder(val binding: ItemTrainerBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Trainer>() {
        override fun areItemsTheSame(oldItem: Trainer, newItem: Trainer): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Trainer, newItem: Trainer): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var trainers: List<Trainer>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun getItemCount() = trainers.size

    fun setClickListener(clickListener: ClickListener?) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainerViewHolder {
        val binding = ItemTrainerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TrainerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrainerViewHolder, position: Int) {
        val trainer = trainers[position]
        val context = holder.itemView.context

        holder.binding.apply {
            tvTrainerName.text = trainer.name
            tvTrainerLocation.text = context.getString(R.string.trainer_desc_format, trainer.location)
            tvTrainerRating.text = context.getString(R.string.trainer_rating_format, trainer.rating.toString())

            Glide.with(root.context)
                .load(trainer.picture)
                .centerCrop()
                .into(ivTrainer)
        }
        holder.itemView.setOnClickListener { clickListener?.onItemClicked(trainer) }
    }

    interface ClickListener {
        fun onItemClicked(trainer: Trainer)
    }
}
