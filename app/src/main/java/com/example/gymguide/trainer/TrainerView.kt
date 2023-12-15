package com.example.gymguide.trainer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymguide.data.DataSource
import com.example.gymguide.databinding.FragmentTrainerBinding
import com.example.gymguide.ui.TrainerAdapter

class TrainerView : Fragment() {

    private var _binding: FragmentTrainerBinding? = null

    private val binding get() = _binding!!
    private lateinit var trainerAdapter: TrainerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTrainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        trainerAdapter.trainers = DataSource.trainers

    }

    private fun setupRecyclerView() = binding.rvTrainer.apply {
        trainerAdapter = TrainerAdapter()
        adapter = trainerAdapter
        layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}