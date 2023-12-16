package com.example.gymguide.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gymguide.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.signUpButton.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            val email = binding.etEmail.text.toString().trim()
            val name = binding.etName.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val passwordAgain = binding.etPasswordAgain.text.toString().trim()

            var hasError = false

            if (TextUtils.isEmpty(email)) {
                binding.etEmail.error = "Email is empty"
                hasError = true
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.etEmail.error = "Invalid email format"
                hasError = true
            }

            if (TextUtils.isEmpty(name)) {
                binding.etName.error = "Name is empty"
                hasError = true
            }

            if (TextUtils.isEmpty(password)) {
                binding.etPassword.error = "Password is empty"
                hasError = true
            } else if (password.length < 8) {
                binding.etPassword.error = "Password must be at least 8 characters long"
                hasError = true
            }

            if (TextUtils.isEmpty(passwordAgain)) {
                binding.etPasswordAgain.error = "Password again is empty"
                hasError = true
            } else if (passwordAgain.length < 8) {
                binding.etPassword.error = "Password must be at least 8 characters long"
                hasError = true
            } else if (password != passwordAgain) {
                binding.etPasswordAgain.error = "Password again is different than password"
                hasError = true
            }

            if (hasError) {
                binding.progressBar.visibility = View.GONE
            }

            if (!hasError) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        binding.progressBar.visibility = View.GONE
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("RegisterActivity", "createUserWithEmail:success")
                            Toast.makeText(
                                baseContext,
                                "Account created successfully.",
                                Toast.LENGTH_SHORT,
                            ).show()
                            val intent = Intent(this, LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("RegisterActivity", "createUserWithEmail:failure", task.exception)
                            Snackbar.make(binding.registerConstraintLayout, "Register failed. ${task.exception?.localizedMessage}", Snackbar.LENGTH_LONG).show()
                        }
                    }
            }
        }

        binding.tvSignIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}