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
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private lateinit var exerciseAdapter: ExerciseAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                try {
                    val response = RetrofitInstance.api.getExercises()

                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            exerciseAdapter.exercises = body.data
                        } else {
                            Log.e("HomeFragment", "Response body is null")
                        }
                    } else {
                        Log.e("HomeFragment", "Response not successful: ${response.code()}")
                    }
                } catch (e: IOException) {
                    Log.e("HomeFragment", "IOException, you might not have internet connection")
                } catch (e: HttpException) {
                    Log.e("HomeFragment", "HttpException, unexpected response: ${e.code()}")
                } finally {
                    //binding.progressBar.isVisible = false
                }
            }
        }
    }

    private fun setupRecyclerView() = binding.rvExercise.apply {
        exerciseAdapter = ExerciseAdapter(1)
        exerciseAdapter.setClickListener(object : ExerciseAdapter.ClickListener {
            override fun onItemClicked(exercise: Exercise) {
                val intent = Intent(requireContext(), DetailExerciseActivity::class.java)
                intent.putExtra("id",exercise.id)
                intent.putExtra("name",exercise.name)
                intent.putExtra("type",exercise.type)
                intent.putExtra("muscle",exercise.muscle)
                intent.putExtra("equipment",exercise.equipment)
                intent.putExtra("difficulty",exercise.difficulty)
                intent.putExtra("instructions",exercise.instructions)
                intent.putExtra("link",exercise.link)
                intent.putExtra("picture",exercise.picture)
                startActivity(intent)
            }
        })
        adapter = exerciseAdapter

        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}