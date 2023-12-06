package com.example.gymguide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymguide.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setup rv
        binding.rvExercise.setHasFixedSize(true)
        binding.rvExercise.layoutManager = LinearLayoutManager(context,  LinearLayoutManager.HORIZONTAL, false)

        // set adapter to rv
        val adapter = ExerciseAdapter(DataSource.students)
        adapter.setClickListener(object : ExerciseAdapter.ClickListener {
            override fun onItemClicked(student: Student?) {
                Toast.makeText(view.context, student?.name, Toast.LENGTH_SHORT).show()
            }
        })
        binding.rvExercise.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}