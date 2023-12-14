package com.example.gymguide.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gymguide.data.Exercise
import com.example.gymguide.databinding.ItemExerciseBinding
import com.example.gymguide.databinding.ItemExerciseRecommendationBinding
import com.example.gymguide.databinding.ItemTrainerBinding


class ExerciseAdapter(private val layoutType: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var clickListener: ClickListener? = null
    private val viewTypeOld = 1
    private val viewTypeNew = 2
    private val viewTypeNew1 = 3


    inner class ExerciseViewHolder(val binding: ItemExerciseBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class ExerciseViewHolderNew(val binding: ItemExerciseRecommendationBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class ExerciseViewHolderNew1(val binding: ItemTrainerBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Exercise>() {
        override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var exercises: List<Exercise>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun getItemCount() = exercises.size

    fun setClickListener(clickListener: ClickListener?) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            viewTypeOld -> {
                val binding = ItemExerciseBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ExerciseViewHolder(binding)
            }

            viewTypeNew -> {
                val bindingNew = ItemExerciseRecommendationBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ExerciseViewHolderNew(bindingNew)
            }

            viewTypeNew1 -> {
                val bindingNew1 = ItemTrainerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ExerciseViewHolderNew1(bindingNew1)
            }

            else -> throw IllegalArgumentException("Invalid view type: $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return layoutType
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ExerciseViewHolder -> {
                val exercise = exercises[position]
                holder.binding.apply {
                    tvExerciseName.text = exercise.name
                    tvExerciseDesc.text = exercise.instructions
                    Glide.with(root.context)
                        .load(exercise.picture)
                        .centerCrop()
                        .into(ivExercise)
                }
                holder.itemView.setOnClickListener { clickListener!!.onItemClicked(exercise) }
            }

            is ExerciseViewHolderNew -> {
                val exercise = exercises[position]
                holder.binding.apply {
                    tvExerciseName.text = exercise.name
                    tvExerciseDesc.text = exercise.instructions
                    Glide.with(root.context)
                        .load(exercise.picture)
                        .centerCrop()
                        .into(ivExercise)
                }
                //holder.itemView.setOnClickListener { clickListener!!.onItemClicked(exercise) }


            }

            is ExerciseViewHolderNew1 -> {
                val exercise = exercises[position]
                holder.binding.apply {
                    tvExerciseName.text = exercise.name
                    tvExerciseDesc.text = exercise.instructions
                    Glide.with(root.context)
                        .load(exercise.picture)
                        .centerCrop()
                        .into(ivExercise)
                }
                //holder.itemView.setOnClickListener { clickListener!!.onItemClicked(exercise) }
            }

        }
    }
    interface ClickListener {
        fun onItemClicked(exercise: Exercise)
    }
}

