    package com.example.gymguide.ui

    import android.content.Intent
    import android.os.Bundle
    import android.util.Log
    import android.view.View
    import android.webkit.WebChromeClient
    import android.webkit.WebSettings
    import androidx.appcompat.app.AppCompatActivity
    import androidx.lifecycle.Lifecycle
    import androidx.lifecycle.lifecycleScope
    import androidx.lifecycle.repeatOnLifecycle
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.bumptech.glide.Glide
    import com.example.gymguide.data.Exercise
    import com.example.gymguide.data.RetrofitInstance
    import com.example.gymguide.databinding.ActivityDetailExerciseBinding
    import kotlinx.coroutines.launch
    import retrofit2.HttpException
    import java.io.IOException


    class DetailExerciseActivity : AppCompatActivity() {
        private lateinit var binding: ActivityDetailExerciseBinding
        private lateinit var exerciseAdapter: ExerciseAdapter
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityDetailExerciseBinding.inflate(layoutInflater)
            setContentView(binding.root)



            val extras = intent.extras
            val id: String? = extras?.getString("id")
            val name: String? = extras?.getString("name")
            val type: String? = extras?.getString("type")
            val muscle: String? = extras?.getString("muscle")
            val equipment: String? = extras?.getString("equipment")
            val difficulty: String? = extras?.getString("difficulty")
            val instructions: String? = extras?.getString("instructions")
            val link: String? = extras?.getString("link")
            val picture: String? = extras?.getString("picture")
            val animation: String? = extras?.getString("animation")
            val video =
                "<iframe width=\"100%\" height=\"100%\" src=\"$link\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
            binding.webView.loadData(video, "text/html", "utf-8")
            binding.webView.settings.javaScriptEnabled = true
            binding.webView.webChromeClient = WebChromeClient()
            binding.webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
            binding.webView.settings.domStorageEnabled = true

            binding.tvExerciseName.text = name
            binding.tvExerciseDetail.text = instructions
            binding.tvExerciseDifficulty.text = difficulty
            binding.tvExerciseEquipment.text = equipment
            Glide.with(this)
                .load(picture)
                .centerCrop()
                .into(binding.ivExercisePicture)

            setupRecyclerView()

            fetchDataFromAPI(equipment)

            binding.failedIv.setOnClickListener {
                fetchDataFromAPI(equipment)
            }

            binding.buttonStartExercise.setOnClickListener{
                val intent = Intent(this, StartExerciseActivity::class.java)
                intent.putExtra("id",id)
                intent.putExtra("name",name)
                intent.putExtra("type",type)
                intent.putExtra("muscle",muscle)
                intent.putExtra("equipment",equipment)
                intent.putExtra("difficulty",difficulty)
                intent.putExtra("instructions",instructions)
                intent.putExtra("link",link)
                intent.putExtra("picture",picture)
                intent.putExtra("animation",animation)
                startActivity(intent)
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
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        private fun fetchDataFromAPI(equipment: String?) {
            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                    try {
                        binding.progressBar.visibility = View.VISIBLE
                        val response = RetrofitInstance.api.getEquipment(equipment!!)

                        if (response.isSuccessful) {
                            val body = response.body()
                            if (body != null) {
                                exerciseAdapter.exercises = body.data
                                // Show success layout
                                binding.successLayout.visibility = View.VISIBLE
                                binding.failedLayout.visibility = View.GONE
                            } else {
                                Log.e("HomeFragment", "Response body is null")
                                // Show failed layout
                                binding.successLayout.visibility = View.GONE
                                binding.failedLayout.visibility = View.VISIBLE
                            }
                        } else {
                            // Show failed layout
                            binding.successLayout.visibility = View.GONE
                            binding.failedLayout.visibility = View.VISIBLE
                            Log.e("HomeFragment", "Response not successful: ${response.code()}")
                        }
                    } catch (e: IOException) {
                        // Show failed layout
                        binding.successLayout.visibility = View.GONE
                        binding.failedLayout.visibility = View.VISIBLE
                        Log.e("HomeFragment", "IOException, you might not have an internet connection")
                    } catch (e: HttpException) {
                        // Show failed layout
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