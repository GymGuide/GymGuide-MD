package com.example.gymguide.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gymguide.cards.Card
import com.example.gymguide.cards.CardView
import com.example.gymguide.databinding.FragmentConsultBinding
import com.google.android.material.tabs.TabLayoutMediator

class ConsultFragment : Fragment() {

    private var _binding: FragmentConsultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConsultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = binding.viewPager
        val tabLayout = binding.tabs

        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return CardView()
            }

            override fun getItemCount(): Int {
                return Card.DECK.size
            }
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = Card.DECK[position].toString()
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
