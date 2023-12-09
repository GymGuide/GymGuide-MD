package com.example.gymguide.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymguide.data.ApiConfig
import com.example.gymguide.data.ApiService
import com.example.gymguide.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val exerciseList = mutableListOf<Exercise>()
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


        // setup rv
        binding.rvExercise.setHasFixedSize(true)
        binding.rvExercise.layoutManager = LinearLayoutManager(context,  LinearLayoutManager.HORIZONTAL, false)

        // set adapter to rv
        val adapter = ExerciseAdapter(DataSource.students)
        adapter.setClickListener(object : ExerciseAdapter.ClickListener {
            override fun onItemClicked(exercise: Exercise?) {
                Toast.makeText(view.context, exercise?.name, Toast.LENGTH_SHORT).show()
            }
        })
        binding.rvExercise.adapter = adapter
        getExerciseList()
    }

    private fun getExerciseList() {
        val retrofit = ApiConfig.getInstance()
        val apiInterface = retrofit.create(ApiService::class.java)
        lifecycleScope.launchWhenCreated {
            try {
                val response = apiInterface.getExercises()
                if (response.isSuccessful) {
                    //your code for handaling success response
                    Log.d("Response",response.toString())

                } else {
                    Toast.makeText(
                        requireContext(),
                        response.errorBody().toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }catch (e:Exception){
                Log.e("Error",e.localizedMessage)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}