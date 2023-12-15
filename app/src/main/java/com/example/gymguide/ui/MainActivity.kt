package com.example.gymguide.ui

import android.animation.ObjectAnimator
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.gymguide.R
import com.example.gymguide.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var selectedButton: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectButton(binding.homeButton)
        showHomeFragment()

        binding.homeButton.setOnClickListener {
            selectButton(binding.homeButton)
            showHomeFragment()
        }

        binding.discoverButton.setOnClickListener {
            selectButton(binding.discoverButton)
            showDiscoverFragment()
        }

        binding.consultButton.setOnClickListener {
            selectButton(binding.consultButton)
            showConsultFragment()
        }

        binding.settingsButton.setOnClickListener {
            selectButton(binding.settingsButton)
            showSettingsFragment()
        }

        binding.fab.setOnClickListener {
            showScanFragment()
        }

    }

    private fun selectButton(textview: TextView) {
        selectedButton?.run {
            animateButton(this, 1f)
            setTextColor(ContextCompat.getColor(context, R.color.grey_menu))
            setTextViewDrawableColor(this, R.color.grey_menu)
        }

        textview.setTextColor(ContextCompat.getColor(textview.context, R.color.yellow_menu))
        setTextViewDrawableColor(textview, R.color.yellow_menu)
        animateButton(textview, 1.25f)

        selectedButton = textview
    }

    private fun animateButton(textView: TextView, scaleTo: Float) {
        val scaleAnimator = ObjectAnimator.ofFloat(textView, "scaleX", scaleTo)
        scaleAnimator.duration = 200 // Adjust the duration as needed
        scaleAnimator.interpolator = android.view.animation.AccelerateDecelerateInterpolator()
        scaleAnimator.addUpdateListener {
            val animatedValue = it.animatedValue as Float
            textView.scaleY = animatedValue
        }
        scaleAnimator.start()
    }

    private fun setTextViewDrawableColor(textView: TextView, color: Int) {
        for (drawable in textView.compoundDrawables) {
            drawable?.colorFilter = PorterDuffColorFilter(
                ContextCompat.getColor(textView.context, color),
                PorterDuff.Mode.SRC_IN
            )
        }
    }

    private fun showHomeFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, HomeFragment(), HomeFragment::class.java.simpleName)
            .commit()
    }

    private fun showConsultFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, ConsultFragment(), ConsultFragment::class.java.simpleName)
            .commit()
    }

    private fun showScanFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, ScanFragment(), ScanFragment::class.java.simpleName)
            .commit()
    }

    private fun showDiscoverFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, DiscoverFragment(), DiscoverFragment::class.java.simpleName)
            .commit()
    }

    private fun showSettingsFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, SettingsFragment(), SettingsFragment::class.java.simpleName)
            .commit()
    }
}
