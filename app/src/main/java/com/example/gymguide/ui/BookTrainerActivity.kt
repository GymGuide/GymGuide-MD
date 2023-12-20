package com.example.gymguide.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.gymguide.databinding.ActivityBookTrainerBinding

class BookTrainerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookTrainerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookTrainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        val name: String? = extras?.getString("name")
        val location: String? = extras?.getString("location")
        val picture: String? = extras?.getString("picture")
        val rating: String? = extras?.getString("rating")
        val description: String? = extras?.getString("description")

        binding.tvTrainerName.text = name
        binding.tvTrainerLocation.text = location
        Glide.with(this)
            .load(picture)
            .centerCrop()
            .into(binding.ivTrainerImage)

        binding.scheduleButton.setOnClickListener {
            val intent = Intent(this, ScheduleTrainerActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("location",location)
            intent.putExtra("picture",picture)
            intent.putExtra("rating",rating)
            intent.putExtra("description",description)
            startActivity(intent)
        }
    }
}