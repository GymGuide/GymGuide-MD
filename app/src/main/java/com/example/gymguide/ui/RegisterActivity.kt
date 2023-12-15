package com.example.gymguide.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.gymguide.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpButton.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val name = binding.etName.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val passwordAgain = binding.etPasswordAgain.text.toString().trim()

            var hasError = false

            if (TextUtils.isEmpty(email)) {
                binding.etEmail.error = "Email is empty"
                hasError = true
            }

            if (TextUtils.isEmpty(name)) {
                binding.etName.error = "Name is empty"
                hasError = true
            }

            if (TextUtils.isEmpty(password)) {
                binding.etPassword.error = "Password is empty"
                hasError = true
            }

            if (TextUtils.isEmpty(passwordAgain)) {
                binding.etPasswordAgain.error = "Enter password again is empty"
                hasError = true
            }

            if (!hasError) {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }

        binding.tvSignIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}