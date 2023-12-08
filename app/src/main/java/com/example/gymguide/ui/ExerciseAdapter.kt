package com.example.gymguide.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gymguide.R

class ExerciseAdapter(private val exercises: ArrayList<Exercise>) :
    RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {
    private var clickListener: ClickListener? = null
    fun setClickListener(clickListener: ClickListener?) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = exercises[position]
        holder.setData(student)
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView
        private val tvDesc: TextView

        init {
            tvName = itemView.findViewById(R.id.tv_exercise_name)
            tvDesc = itemView.findViewById(R.id.tv_exercise_desc)
        }

        fun setData(exercise: Exercise) {
            tvName.text = exercise.name
            tvDesc.text = exercise.nim
            itemView.setOnClickListener { _: View? -> clickListener!!.onItemClicked(exercise) }
        }
    }

    interface ClickListener {
        fun onItemClicked(exercise: Exercise?)
    }
}