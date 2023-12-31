package com.example.gymguide.ui

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gymguide.R
import com.example.gymguide.databinding.ActivityRegisterBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

private const val TAG = "RegisterActivity"
private const val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    private var showOneTapUI = true

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

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        oneTapClient = Identity.getSignInClient(this)
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(getString(R.string.google_web_client_id))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(true)
                    .build())
            .build()

        binding.signUpButton.setOnClickListener {
            // Hide keyboard on button click
            try {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            } catch (e: Exception) {
                Log.d(TAG, "Couldn't close keyboard: ${e.localizedMessage}")
            }

            binding.progressBar.visibility = View.VISIBLE
            val email = binding.etEmail.text.toString().trim()
            val name = binding.etName.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

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

            if (TextUtils.isEmpty(confirmPassword)) {
                binding.etConfirmPassword.error = "Password again is empty"
                hasError = true
            } else if (confirmPassword.length < 8) {
                binding.etPassword.error = "Password must be at least 8 characters long"
                hasError = true
            } else if (password != confirmPassword) {
                binding.etConfirmPassword.error = "Password again is different than password"
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

        binding.ivGoogleSignIn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE

            oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this) { result ->
                    binding.progressBar.visibility = View.GONE
                    try {
                        startIntentSenderForResult(
                            result.pendingIntent.intentSender, REQ_ONE_TAP,
                            null, 0, 0, 0, null)
                    } catch (e: IntentSender.SendIntentException) {
                        Log.e(TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
                    }
                }
                .addOnFailureListener(this) { e ->
                    binding.progressBar.visibility = View.GONE
                    // No saved credentials found. Launch the One Tap sign-up flow, or
                    // do nothing and continue presenting the signed-out UI.
                    e.localizedMessage?.let { it1 -> Log.d(TAG, it1) }
                }
        }

        binding.ivAppleSignIn.setOnClickListener {
            Toast.makeText(
                baseContext,
                "Apple sign in is still under development, use google or email",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.ivFacebookSignIn.setOnClickListener {
            Toast.makeText(
                baseContext,
                "Facebook sign in is still under development, use google or email",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.tvSignIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQ_ONE_TAP -> {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
                    val idToken = credential.googleIdToken
                    val username = credential.id
                    val password = credential.password
                    when {
                        idToken != null -> {
                            // Got an ID token from Google. Use it to authenticate
                            // with Firebase.
                            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                            auth.signInWithCredential(firebaseCredential)
                                .addOnCompleteListener(this) { task ->
                                    if (task.isSuccessful) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithCredential:success")
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
                                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                                        Log.w("RegisterActivity", "createUserWithEmail:failure", task.exception)
                                        Snackbar.make(binding.registerConstraintLayout, "Register failed. ${task.exception?.localizedMessage}", Snackbar.LENGTH_LONG).show()
                                    }
                                }
                        }
                        else -> {
                            // Shouldn't happen.
                            Log.d(TAG, "No ID token!")
                        }
                    }
                } catch (e: ApiException) {
                    when (e.statusCode) {
                        CommonStatusCodes.CANCELED -> {
                            Log.d(TAG, "One-tap dialog was closed.")
                            // Don't re-prompt the user.
                            showOneTapUI = false
                        }
                        CommonStatusCodes.NETWORK_ERROR -> {
                            Log.d(TAG, "One-tap encountered a network error.")
                            Snackbar.make(binding.registerConstraintLayout, "Encountered a network error, try again", Snackbar.LENGTH_LONG).show()
                            // Try again or just ignore.
                        }
                        else -> {
                            Log.d(TAG, "Couldn't get credential from result." +
                                    " (${e.localizedMessage})")
                            Snackbar.make(binding.registerConstraintLayout, "Couldn't get credential from result." +
                                    " (${e.localizedMessage})", Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}