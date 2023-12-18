package com.example.gymguide.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymguide.data.Rank
import com.example.gymguide.data.RankDataSource
import com.example.gymguide.databinding.FragmentRankBinding

class RankFragment : Fragment() {
    private var _binding: FragmentRankBinding? = null

    private val binding get() = _binding!!
    private lateinit var rankAdapter: RankAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        rankAdapter.ranks = RankDataSource.ranks
    }

    private fun setupRecyclerView() = binding.rvUser.apply {
        rankAdapter = RankAdapter()
        rankAdapter.setClickListener(object : RankAdapter.ClickListener {
            override fun onItemClicked(rank: Rank) {
                val intent = Intent(requireContext(), DetailTrainerActivity::class.java)
                intent.putExtra("name",rank.name)
                intent.putExtra("nim",rank.nim)
                startActivity(intent)
            }
        })
        adapter = rankAdapter
        layoutManager = LinearLayoutManager(context)
    }
}