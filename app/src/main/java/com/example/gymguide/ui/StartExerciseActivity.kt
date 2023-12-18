package com.example.gymguide.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.gymguide.databinding.ActivityStartExerciseBinding

class StartExerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartExerciseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        val id: String? = extras?.getString("id")
        val name: String? = extras?.getString("name")
        val type: String? = extras?.getString("type")
        val muscle: String? = extras?.getString("muscle")
        val equipment: String? = extras?.getString("equipment")
        val difficulty: String? = extras?.getString("difficulty")
        val instructions: String? = extras?.getString("instructions")
        val link: String? = extras?.getString("link")
        val picture: String? = extras?.getString("picture")
        val animation: String? = extras?.getString("animation")

        binding.tvExerciseName.text = name
        Glide.with(this)
            .load(animation)
            .fitCenter()
            .into(binding.ivExerciseAnimation)
    }
}