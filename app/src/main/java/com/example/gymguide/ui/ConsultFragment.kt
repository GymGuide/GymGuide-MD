package com.example.gymguide.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymguide.R
import com.example.gymguide.data.RetrofitInstance
import com.example.gymguide.databinding.FragmentConsultBinding
import com.example.gymguide.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

const val TAG = "ConsultFragment"

class ConsultFragment : Fragment() {

    private var _binding: FragmentConsultBinding? = null

    private val binding get() = _binding!!
    private lateinit var exerciseAdapter: ExerciseAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentConsultBinding.inflate(inflater, container, false)
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
                            Log.e(TAG, "Response body is null")
                        }
                    } else {
                        Log.e(TAG, "Response not successful: ${response.code()}")
                    }
                } catch (e: IOException) {
                    Log.e(TAG, "IOException, you might not have internet connection")
                } catch (e: HttpException) {
                    Log.e(TAG, "HttpException, unexpected response: ${e.code()}")
                } finally {
                    //binding.progressBar.isVisible = false
                }
            }
        }
    }

    private fun setupRecyclerView() = binding.rvExercise.apply {
        exerciseAdapter = ExerciseAdapter()
        adapter = exerciseAdapter
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}