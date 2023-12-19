package com.example.gymguide.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymguide.data.Exercise
import com.example.gymguide.data.RetrofitInstance
import com.example.gymguide.databinding.ActivityMuscleDetailBinding
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MuscleDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMuscleDetailBinding
    private lateinit var exerciseAdapter: ExerciseAdapter
    private var muscle: String = "No Muscle"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMuscleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        // Convert String from Capitalize
        muscle = extras?.getString("muscle").toString()
        val muscleText = extras?.getString("muscle-text").toString()

        binding.tvMusclePart.text = "Exercise List for $muscleText"
        setupRecyclerView()

        fetchDataFromAPI()

        binding.failedIv.setOnClickListener {
            fetchDataFromAPI()
        }
    }

    private fun fetchDataFromAPI() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                try {
                    binding.progressBar.visibility = View.VISIBLE

                    val response = RetrofitInstance.api.getMuscle(muscle)

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

    private fun setupRecyclerView() = binding.rvExercise.apply {
        exerciseAdapter = ExerciseAdapter(2)
        exerciseAdapter.setClickListener(object : ExerciseAdapter.ClickListener {
            override fun onItemClicked(exercise: Exercise) {
                val intent = Intent(context, DetailExerciseActivity::class.java)
                intent.putExtra("id",exercise.id)
                intent.putExtra("name",exercise.name)
                intent.putExtra("type",exercise.type)
                intent.putExtra("muscle",exercise.muscle)
                intent.putExtra("equipment",exercise.equipment)
                intent.putExtra("difficulty",exercise.difficulty)
                intent.putExtra("instructions",exercise.instructions)
                intent.putExtra("link",exercise.link)
                intent.putExtra("picture",exercise.picture)
                intent.putExtra("animation",exercise.animation)
                startActivity(intent)
            }
        })
        adapter = exerciseAdapter
        layoutManager = LinearLayoutManager(context)
    }
}