package com.example.gymguide.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymguide.data.Exercise
import com.example.gymguide.data.RetrofitInstance
import com.example.gymguide.databinding.FragmentHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.text.DateFormat.getDateInstance
import java.util.Date


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private lateinit var exerciseAdapter: ExerciseAdapter

    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        val user = auth.currentUser

        user?.let {
            // Name, email address, and profile photo Url
            val name = it.displayName ?: it.email
            val photoUrl = it.photoUrl

            // Check if user's email is verified
            val emailVerified = it.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            val uid = it.uid
            val welcomeText = "Welcome, $name!"
            binding.tvWelcome.text = welcomeText
        }

        val sdf = getDateInstance()
        val currentDate = sdf.format(Date())

        binding.tvCurrentDate.text = currentDate

        setupRecyclerView()

        fetchDataFromAPI()

        binding.failedIv.setOnClickListener {
            fetchDataFromAPI()
        }
    }

    private fun setupRecyclerView() = binding.rvExercise.apply {
        exerciseAdapter = ExerciseAdapter(1)
        exerciseAdapter.setClickListener(object : ExerciseAdapter.ClickListener {
            override fun onItemClicked(exercise: Exercise) {
                val intent = Intent(requireContext(), DetailExerciseActivity::class.java)
                intent.putExtra("id", exercise.id)
                intent.putExtra("name", exercise.name)
                intent.putExtra("type", exercise.type)
                intent.putExtra("muscle", exercise.muscle)
                intent.putExtra("equipment", exercise.equipment)
                intent.putExtra("difficulty", exercise.difficulty)
                intent.putExtra("instructions", exercise.instructions)
                intent.putExtra("link", exercise.link)
                intent.putExtra("picture", exercise.picture)
                intent.putExtra("animation", exercise.animation)
                startActivity(intent)
            }
        })
        adapter = exerciseAdapter

        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun fetchDataFromAPI() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                try {
                    binding.progressBar.visibility = View.VISIBLE

                    val response = RetrofitInstance.api.getExercises()

                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            exerciseAdapter.exercises = body.data
                            binding.successLayout.visibility = View.VISIBLE
                            binding.failedLayout.visibility = View.GONE
                        } else {
                            binding.successLayout.visibility = View.GONE
                            binding.failedLayout.visibility = View.VISIBLE
                            Log.e("HomeFragment", "Response body is null")
                        }
                    } else {
                        binding.successLayout.visibility = View.GONE
                        binding.failedLayout.visibility = View.VISIBLE
                        Log.e("HomeFragment", "Response not successful: ${response.code()}")
                    }
                } catch (e: IOException) {
                    binding.successLayout.visibility = View.GONE
                    binding.failedLayout.visibility = View.VISIBLE
                    Log.e("HomeFragment", "IOException, you might not have internet connection")
                } catch (e: HttpException) {
                    binding.successLayout.visibility = View.GONE
                    binding.failedLayout.visibility = View.VISIBLE
                    Log.e("HomeFragment", "HttpException, unexpected response: ${e.code()}")
                } finally {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }
}