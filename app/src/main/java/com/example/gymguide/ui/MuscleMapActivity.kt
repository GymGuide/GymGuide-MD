// MuscleMapActivity.kt
package com.example.gymguide.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gymguide.databinding.ActivityMuscleMapBinding

class MuscleMapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMuscleMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMuscleMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up CustomImageViews and attach click listener
        binding.ivCalves.setOnClickListener {
            startActivity(Intent(this, MuscleDetailActivity::class.java))
            Toast.makeText(this, "ivCalves", Toast.LENGTH_SHORT).show()
        }

        binding.ivQuads.setOnClickListener {
            startActivity(Intent(this, MuscleDetailActivity::class.java))
            Toast.makeText(this, "ivQuads", Toast.LENGTH_SHORT).show()
        }

        binding.ivChest.setOnClickListener {
            startActivity(Intent(this, MuscleDetailActivity::class.java))
            Toast.makeText(this, "ivChest", Toast.LENGTH_SHORT).show()
        }

        binding.ivTraps.setOnClickListener {
            startActivity(Intent(this, MuscleDetailActivity::class.java))
            Toast.makeText(this, "ivTraps", Toast.LENGTH_SHORT).show()
        }

        binding.ivShoulders.setOnClickListener {
            startActivity(Intent(this, MuscleDetailActivity::class.java))
            Toast.makeText(this, "ivShoulders", Toast.LENGTH_SHORT).show()
        }

        binding.ivBiceps.setOnClickListener {
            startActivity(Intent(this, MuscleDetailActivity::class.java))
            Toast.makeText(this, "ivBiceps", Toast.LENGTH_SHORT).show()
        }

        binding.ivForearms.setOnClickListener {
            startActivity(Intent(this, MuscleDetailActivity::class.java))
            Toast.makeText(this, "ivForearms", Toast.LENGTH_SHORT).show()
        }


        binding.ivObliques.setOnClickListener {
            startActivity(Intent(this, MuscleDetailActivity::class.java))
            Toast.makeText(this, "ivObliques", Toast.LENGTH_SHORT).show()
        }

        binding.ivAbdominals.setOnClickListener {
            startActivity(Intent(this, MuscleDetailActivity::class.java))
            Toast.makeText(this, "ivAbdominals", Toast.LENGTH_SHORT).show()
        }
    }
}
