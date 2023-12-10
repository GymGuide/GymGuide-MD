package com.example.gymguide.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymguide.data.RetrofitInstance
import com.example.gymguide.databinding.FragmentHomeBinding
import retrofit2.HttpException
import java.io.IOException

const val TAG = "HomeFragment"
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var exerciseAdapter: ExerciseAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        lifecycleScope.launchWhenCreated {
            try {
                val response = RetrofitInstance.api.getExercises()

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        // Set the new list of exercises to the adapter
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

    private fun setupRecyclerView() = binding.rvExercise.apply {
        exerciseAdapter = ExerciseAdapter()
        adapter = exerciseAdapter
        layoutManager = LinearLayoutManager(context,  LinearLayoutManager.HORIZONTAL, false)
    }

    //private fun getExerciseList() {
    //    val retrofit = ApiConfig.getInstance()
    //    val apiInterface = retrofit.create(ExerciseApi::class.java)
    //    lifecycleScope.launchWhenCreated {
    //        try {
    //            val response = apiInterface.getExercises()
    //            if (response.isSuccessful) {
    //                //your code for handaing success response
    //                Log.d("Response",response.toString())
    //
    //            } else {
    //                Toast.makeText(
    //                    requireContext(),
    //                    response.errorBody().toString(),
    //                    Toast.LENGTH_LONG
    //                ).show()
    //            }
    //        }catch (e:Exception){
    //            Log.e("Error",e.toString())
    //        }
    //    }
    //
    //}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}