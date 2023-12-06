package com.example.gymguide

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExerciseAdapter(private val students: ArrayList<Student>) :
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
        val student = students[position]
        holder.setData(student)
    }

    override fun getItemCount(): Int {
        return students.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView
        private val tvDesc: TextView

        init {
            tvName = itemView.findViewById(R.id.tv_exercise_name)
            tvDesc = itemView.findViewById(R.id.tv_exercise_desc)
        }

        fun setData(student: Student) {
            tvName.text = student.name
            tvDesc.text = student.nim
            itemView.setOnClickListener { _: View? -> clickListener!!.onItemClicked(student) }
        }
    }

    interface ClickListener {
        fun onItemClicked(student: Student?)
    }
}