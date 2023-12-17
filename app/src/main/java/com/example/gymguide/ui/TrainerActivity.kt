package com.example.gymguide.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gymguide.databinding.ActivityTrainerBinding
import com.example.gymguide.trainer.TrainerCategory
import com.example.gymguide.trainer.TrainerView
import com.google.android.material.tabs.TabLayoutMediator

class TrainerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTrainerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager = binding.viewPager
        val tabLayout = binding.tabs

        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return TrainerView()
            }

            override fun getItemCount(): Int {
                return TrainerCategory.CATEGORIES.size
            }
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = TrainerCategory.CATEGORIES[position]
        }.attach()
    }
}