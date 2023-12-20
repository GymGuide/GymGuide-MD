package com.example.gymguide.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gymguide.R
import com.example.gymguide.databinding.ActivityBookTrainerBinding
import com.example.gymguide.databinding.ActivityChatBinding
import com.example.gymguide.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView22.setOnClickListener {
            finish()
        }
    }
}