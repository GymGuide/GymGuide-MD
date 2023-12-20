package com.example.gymguide.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.gymguide.R
import com.example.gymguide.databinding.ActivityBookTrainerBinding
import com.example.gymguide.databinding.ActivityChatBinding
import com.example.gymguide.databinding.ActivityScheduleTrainerBinding

class ScheduleTrainerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScheduleTrainerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleTrainerBinding.inflate(layoutInflater)
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


        binding.confirmbutton.setOnClickListener {
            binding.ivConfirmed.visibility = View.VISIBLE
        }

        binding.ivConfirmed.setOnClickListener {
            binding.ivConfirmed.visibility = View.GONE
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}