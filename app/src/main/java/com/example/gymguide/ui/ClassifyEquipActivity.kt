    package com.example.gymguide.ui

    import android.content.Intent
    import android.graphics.Bitmap
    import android.os.Bundle
    import android.util.Log
    import android.view.View
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity
    import androidx.lifecycle.Lifecycle
    import androidx.lifecycle.lifecycleScope
    import androidx.lifecycle.repeatOnLifecycle
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.bumptech.glide.Glide
    import com.example.gymguide.data.Exercise
    import com.example.gymguide.data.RetrofitInstance
    import com.example.gymguide.data.UploadResponse
    import com.example.gymguide.databinding.ActivityClassifyEquipBinding
    import com.example.gymguide.ml.GymEquipmentQuantized
    import com.google.gson.Gson
    import kotlinx.coroutines.launch
    import okhttp3.MediaType.Companion.toMediaType
    import okhttp3.MultipartBody
    import okhttp3.RequestBody.Companion.asRequestBody
    import org.tensorflow.lite.DataType
    import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
    import retrofit2.HttpException
    import java.io.ByteArrayOutputStream
    import java.io.File
    import java.io.FileOutputStream
    import java.io.IOException
    import java.nio.ByteBuffer
    import java.nio.ByteOrder


    /** Activity that displays the camera and performs object detection on the incoming frames */
    class ClassifyEquipActivity : AppCompatActivity() {
        private var imageSize = 150
        private lateinit var binding: ActivityClassifyEquipBinding
        private lateinit var exerciseAdapter: ExerciseAdapter
        private var prediction: String = "No Prediction"
        private lateinit var classes: Array<String>
        private lateinit var classesText: Array<String>
        private lateinit var classToText: Map<String, String>

        @Suppress("DEPRECATION")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityClassifyEquipBinding.inflate(layoutInflater)
            setContentView(binding.root)

            classes = ExerciseConstants.classes
            classesText = ExerciseConstants.classesText
            classToText = ExerciseConstants.classToTextMap

            val extras = intent.extras
            var picture: Bitmap? = extras?.getParcelable("picture")

            if (picture != null) {
                // Now 'image' contains the Bitmap extra named "image"
                // You can use 'image' as needed in your activity
                Glide.with(this)
                    .load(picture)
                    .centerCrop()
                    .into(binding.ivClassifiedImage)

                uploadImage(picture)
            }

            setupRecyclerView()

            binding.failedIv.setOnClickListener {
                fetchDataFromAPI()
            }
        }

        private fun fetchDataFromAPI() {
            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                    try {
                        binding.progressBar.visibility = View.VISIBLE

                        val response = RetrofitInstance.api.getEquipment(prediction)

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

        private fun uploadImage(picture: Bitmap) {
            lifecycleScope.launch {
                try {
                    binding.progressBar.visibility = View.VISIBLE

                    val file = bitmapToFile(picture)

                    // Create a RequestBody for the image
                    val requestImageFile = file.asRequestBody("image/*".toMediaType())

                    // Create a MultipartBody.Part for the image
                    val multipartBody =
                        MultipartBody.Part.createFormData("image", file.name, requestImageFile)

                    // Make sure that the Retrofit API service has a method named "uploadImage"
                    val successResponse = RetrofitInstance.model.uploadImage("Bearer secret", multipartBody)
                    Log.e("HomeFragment", successResponse.data.equipmentPrediction)
                    Toast.makeText(applicationContext, successResponse.data.equipmentPrediction, Toast.LENGTH_SHORT).show()

                    prediction = successResponse.data.equipmentPrediction
                    val predictionText = classToText[prediction]
                    binding.tvResult.text = predictionText

                    fetchDataFromAPI()
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, UploadResponse::class.java)

                    Log.e("HomeFragment", errorResponse.status.code.toString())
                    Log.e("HomeFragment", errorResponse.status.message)
                    Toast.makeText(applicationContext, errorResponse.status.message, Toast.LENGTH_SHORT).show()
                    Toast.makeText(applicationContext, "Internet issue, using tf-lite instead with less accuracy", Toast.LENGTH_SHORT).show()
                    // If upload fails, call classifyImage
                    classifyImage(picture)

                    fetchDataFromAPI()

                } catch (e: Exception) {
                    Log.e("HomeFragment", "Error uploading image: ${e.message}")
                    Toast.makeText(applicationContext, "Internet issue, using tf-lite instead with less accuracy", Toast.LENGTH_SHORT).show()

                    // If upload fails, call classifyImage
                    classifyImage(picture)

                    fetchDataFromAPI()
                } finally {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
        private fun bitmapToFile(bitmap: Bitmap): File {
            val file = File(cacheDir, "image.png")
            file.createNewFile()

            // Convert bitmap to byte array
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()

            // Write the bytes in file
            FileOutputStream(file).use { fileOutputStream ->
                fileOutputStream.write(byteArray)
            }

            return file
        }

        private fun classifyImage(image: Bitmap) {
            try {
                val model: GymEquipmentQuantized = GymEquipmentQuantized.newInstance(applicationContext)
                // Creates inputs for reference.
                val inputFeature0 =
                    TensorBuffer.createFixedSize(intArrayOf(1, 150, 150, 3), DataType.FLOAT32)
                val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
                byteBuffer.order(ByteOrder.nativeOrder())
                val intValues = IntArray(imageSize * imageSize)
                image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
                var pixel = 0
                //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
                for (i in 0 until imageSize) {
                    for (j in 0 until imageSize) {
                        val `val` = intValues[pixel++] // RGB
                        byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 255f))  // Normalize to [0, 1]
                        byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 255f))
                        byteBuffer.putFloat((`val` and 0xFF) * (1f / 255f))
                    }
                }
                inputFeature0.loadBuffer(byteBuffer)

                // Runs model inference and gets result.
                val outputs: GymEquipmentQuantized.Outputs = model.process(inputFeature0)
                val outputFeature0: TensorBuffer = outputs.outputFeature0AsTensorBuffer
                val confidences = outputFeature0.floatArray
                // find the index of the class with the biggest confidence.
                var maxPos = 0
                var maxConfidence = 0f
                for (i in confidences.indices) {
                    if (confidences[i] > maxConfidence) {
                        maxConfidence = confidences[i]
                        maxPos = i
                    }
                }


                prediction = classes[maxPos]
                val predictionText = classToText[prediction]
                binding.tvResult.text = predictionText
                model.close()

                // Releases model resources if no longer used.
            } catch (e: IOException) {
                // TODO Handle the exception
            }
        }
    }