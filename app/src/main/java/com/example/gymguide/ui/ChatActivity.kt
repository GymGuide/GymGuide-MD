package com.example.gymguide.ui

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.gymguide.databinding.ActivityChatBinding

private const val DRAWABLE_LEFT = 0
//private const val DRAWABLE_TOP = 1
private const val DRAWABLE_RIGHT = 2
//private const val DRAWABLE_BOTTOM = 3
class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if (extras != null) {
            val name: String? = extras?.getString("name")
            val location: String? = extras?.getString("location")
            val picture: String? = extras?.getString("picture")
            val rating: String? = extras?.getString("rating")
            val description: String? = extras?.getString("description")

            binding.tvName.text = name
            Glide.with(this)
                .load(picture)
                .centerCrop()
                .into(binding.ivUser)
        }

        binding.etChat.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent): Boolean {

                if (event.action == MotionEvent.ACTION_UP) {
                    val drawableLeftWidth =
                        binding.etChat.compoundDrawables[DRAWABLE_LEFT].bounds.width() // Width of drawableLeft
                    val paddingLeft = binding.etChat.paddingStart // Padding on the left side

                    if (event.rawX <= binding.etChat.left + drawableLeftWidth + paddingLeft) {
                        // Click on drawableLeft
                        // Your action here
                        // For example, show a toast
                        Toast.makeText(applicationContext, "DrawableLeft Clicked", Toast.LENGTH_SHORT)
                            .show()
                        return true
                    }
                }

                if (event.action == MotionEvent.ACTION_UP) {
                    val drawableEndWidth =
                        binding.etChat.compoundDrawables[DRAWABLE_RIGHT].bounds.width() // Width of drawableEnd
                    val paddingEnd = binding.etChat.paddingEnd // Padding on the right side

                    if (event.rawX >= binding.etChat.right - drawableEndWidth - paddingEnd) {
                        // Click on drawableEnd
                        // Your action here
                        // For example, show a toast
                        Toast.makeText(applicationContext, "DrawableEnd Clicked", Toast.LENGTH_SHORT)
                            .show()
                        return true
                    }
                }

                return false
            }
        })



        binding.buttonBack.setOnClickListener {
            finish()
        }
    }
}