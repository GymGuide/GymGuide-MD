package com.example.gymguide.ui

import android.content.Intent
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


        val fragmentManager = supportFragmentManager
        //val homeFragment = HomeFragment()
        //val fragment = fragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)
        //if (fragment !is HomeFragment) {
        //    Log.d("MyFlexibleFragment", "Fragment Name :" + HomeFragment::class.java.simpleName)
        //    fragmentManager
        //        .beginTransaction()
        //        .add(R.id.frame_container, homeFragment, HomeFragment::class.java.simpleName)
        //        .commit()
        //}

        binding.consultButton.setOnClickListener {
            val consultFragment = ConsultFragment()
            fragmentManager
                .beginTransaction()
                .replace(
                    R.id.frame_container, consultFragment,
                    ConsultFragment::class.java.simpleName
                )
                .addToBackStack(null)
                .commit()
        }

        binding.homeButton.setOnClickListener {
            val homeFragment = HomeFragment()
            fragmentManager
                .beginTransaction()
                .replace(
                    R.id.frame_container, homeFragment,
                    HomeFragment::class.java.simpleName
                )
                .addToBackStack(null)
                .commit()
        }

        binding.fab.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }
    }
}