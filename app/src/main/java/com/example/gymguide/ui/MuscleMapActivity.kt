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

        val intent = Intent(this, MuscleDetailActivity::class.java)

        // Set up CustomImageViews and attach click listener
        binding.ivCalves.setOnClickListener {
            intent.putExtra("muscle","calves")
            intent.putExtra("muscle-text","Calves")
            startActivity(intent)
            Toast.makeText(this, "calves", Toast.LENGTH_SHORT).show()
        }

        binding.ivQuads.setOnClickListener {
            intent.putExtra("muscle","quads")
            intent.putExtra("muscle-text","Quads")
            startActivity(intent)
            Toast.makeText(this, "quads", Toast.LENGTH_SHORT).show()
        }

        binding.ivChest.setOnClickListener {
            intent.putExtra("muscle","chest")
            intent.putExtra("muscle-text","Chest")
            startActivity(intent)
            Toast.makeText(this, "chest", Toast.LENGTH_SHORT).show()
        }

        binding.ivTraps.setOnClickListener {
            intent.putExtra("muscle","traps")
            intent.putExtra("muscle-text","Traps")
            startActivity(intent)
            Toast.makeText(this, "traps", Toast.LENGTH_SHORT).show()
        }

        binding.ivShoulders.setOnClickListener {
            intent.putExtra("muscle","shoulders")
            intent.putExtra("muscle-text","Shoulders")
            startActivity(intent)
            Toast.makeText(this, "shoulders", Toast.LENGTH_SHORT).show()
        }

        binding.ivBiceps.setOnClickListener {
            intent.putExtra("muscle","biceps")
            intent.putExtra("muscle-text","Biceps")
            startActivity(intent)
            Toast.makeText(this, "biceps", Toast.LENGTH_SHORT).show()
        }

        binding.ivForearms.setOnClickListener {
            intent.putExtra("muscle","forearms")
            intent.putExtra("muscle-text","Forearms")
            startActivity(intent)
            Toast.makeText(this, "forearms", Toast.LENGTH_SHORT).show()
        }


        binding.ivObliques.setOnClickListener {
            intent.putExtra("muscle","obliques")
            intent.putExtra("muscle-text","Obliques")
            startActivity(intent)
            Toast.makeText(this, "obliques", Toast.LENGTH_SHORT).show()
        }

        binding.ivAbdominals.setOnClickListener {
            intent.putExtra("muscle","abdominals")
            intent.putExtra("muscle-text","Abdominals")
            startActivity(intent)
            Toast.makeText(this, "abdominals", Toast.LENGTH_SHORT).show()
        }
    }
}
