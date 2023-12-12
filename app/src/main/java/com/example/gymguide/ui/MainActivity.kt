package com.example.gymguide.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gymguide.R
import com.example.gymguide.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            showHomeFragment()
        }

        binding.consultButton.setOnClickListener {
            showConsultFragment()
        }

        binding.homeButton.setOnClickListener {
            showHomeFragment()
        }

        binding.fab.setOnClickListener {
            showScanFragment()
        }

        //binding.fab.setOnClickListener {
        //    startActivity(Intent(this, CameraActivity::class.java))
        //}
    }

    private fun showHomeFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, HomeFragment(), HomeFragment::class.java.simpleName)
            .addToBackStack(null)
            .commit()
    }

    private fun showConsultFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, ConsultFragment(), ConsultFragment::class.java.simpleName)
            .addToBackStack(null)
            .commit()
    }

    private fun showScanFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, ScanFragment(), ScanFragment::class.java.simpleName)
            .addToBackStack(null)
            .commit()
    }
}
