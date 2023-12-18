// MuscleMapActivity.kt
package com.example.gymguide.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gymguide.databinding.ActivityMuscleMapBinding

class MuscleMapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMuscleMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMuscleMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val muscleClickListener = { startActivity(Intent(this, MuscleDetailActivity::class.java)) }

        // Set up CustomImageViews and attach click listener
        binding.ivCalves.apply { setOnClickListener { muscleClickListener() } }
        binding.ivQuads.apply { setOnClickListener { muscleClickListener() } }
        binding.ivChest.apply { setOnClickListener { muscleClickListener() } }
        binding.ivTraps.apply { setOnClickListener { muscleClickListener() } }
        binding.ivShoulders.apply { setOnClickListener { muscleClickListener() } }
        binding.ivBiceps.apply { setOnClickListener { muscleClickListener() } }
        binding.ivForearms.apply { setOnClickListener { muscleClickListener() } }
        binding.ivObliques.apply { setOnClickListener { muscleClickListener() } }
        binding.ivAbdominals.apply { setOnClickListener { muscleClickListener() } }
    }
}
