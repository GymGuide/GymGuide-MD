package com.example.gymguide.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.gymguide.databinding.ActivityDetailExerciseBinding

class DetailExerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailExerciseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extras = intent.extras
        var id: String? = extras?.getString("id")
        var name: String? = extras?.getString("name")
        var type: String? = extras?.getString("type")
        var muscle: String? = extras?.getString("muscle")
        var equipment: String? = extras?.getString("equipment")
        var difficulty: String? = extras?.getString("difficulty")
        var instructions: String? = extras?.getString("instructions")
        var link: String? = extras?.getString("link")
        var picture: String? = extras?.getString("picture")

        binding.tvExerciseName.text = name
        binding.tvExerciseDetail.text = instructions
        binding.tvExerciseDifficulty.text = difficulty
        binding.tvExerciseEquipment.text = equipment
        Glide.with(this)
            .load(picture)
            .centerCrop()
            .into(binding.ivExercisePicture)
    }
}