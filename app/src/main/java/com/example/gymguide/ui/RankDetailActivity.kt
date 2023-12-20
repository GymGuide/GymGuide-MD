package com.example.gymguide.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gymguide.R
import com.example.gymguide.databinding.ActivityChatBinding
import com.example.gymguide.databinding.ActivityRankDetailBinding

class RankDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRankDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRankDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView21.setOnClickListener {
            finish()
        }
    }
}