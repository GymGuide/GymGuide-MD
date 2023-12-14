package com.example.gymguide.ui

import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gymguide.R

class DetailExerciseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_exercise)
        val extras = intent.extras
        var image: Bitmap? = extras?.getParcelable("image")
    }
}