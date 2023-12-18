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

        binding.ivQuads.setOnClickListener {
            val intent = Intent(this, MuscleDetailActivity::class.java)
            startActivity(intent)
        }

        binding.ivChest.setOnClickListener {
            val intent = Intent(this, MuscleDetailActivity::class.java)
            startActivity(intent)
        }

        binding.ivTraps.setOnClickListener {
            val intent = Intent(this, MuscleDetailActivity::class.java)
            startActivity(intent)
        }

        binding.ivShoulders.setOnClickListener {
            val intent = Intent(this, MuscleDetailActivity::class.java)
            startActivity(intent)
        }

        binding.ivBicep.setOnClickListener {
            val intent = Intent(this, MuscleDetailActivity::class.java)
            startActivity(intent)
        }

        binding.ivForearms.setOnClickListener {
            val intent = Intent(this, MuscleDetailActivity::class.java)
            startActivity(intent)
        }

        binding.ivObliques.setOnClickListener {
            val intent = Intent(this, MuscleDetailActivity::class.java)
            startActivity(intent)
        }

        binding.ivAbdominals.setOnClickListener {
            val intent = Intent(this, MuscleDetailActivity::class.java)
            startActivity(intent)
        }

    }
}
