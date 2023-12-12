package com.example.gymguide.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.gymguide.data.RetrofitInstance
import com.example.gymguide.databinding.ActivityClassifyEquipBinding
import com.example.gymguide.ml.GymEquipmentV3
import kotlinx.coroutines.launch
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import retrofit2.HttpException
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder


/** Activity that displays the camera and performs object detection on the incoming frames */
class ClassifyEquipActivity : AppCompatActivity() {
    private var imageSize = 150
    private lateinit var binding: ActivityClassifyEquipBinding
    private lateinit var exerciseAdapter: ExerciseAdapter
    private var prediction: String = "No Prediction"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClassifyEquipBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extras = intent.extras
        var image: Bitmap? = extras?.getParcelable("image")

        if (image != null) {
            // Now 'image' contains the Bitmap extra named "image"
            // You can use 'image' as needed in your activity
            Glide.with(this)
                .load(image)
                .centerCrop()
                .into(binding.ivClassifiedImage)
            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
            classifyImage(image)
        }

        setupRecyclerView()

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                try {
                    val response = RetrofitInstance.api.getExercise(prediction)

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
        exerciseAdapter = ExerciseAdapter(2)
        adapter = exerciseAdapter
        layoutManager = LinearLayoutManager(context)
    }

    private fun classifyImage(image: Bitmap) {
        try {
            val model: GymEquipmentV3 = GymEquipmentV3.newInstance(applicationContext)
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
                    byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 1))
                    byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 1))
                    byteBuffer.putFloat((`val` and 0xFF) * (1f / 1))
                }
            }
            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.
            val outputs: GymEquipmentV3.Outputs = model.process(inputFeature0)
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

            val classes = arrayOf(
                "abdominal-machine",
                "arm-curl",
                "arm-extension",
                "back-extension",
                "back-row-machine",
                "bench-press",
                "cable-lat-pulldown",
                "chest-fly",
                "chest-press",
                "dip-chin-assist",
                "hip-abduction-adduction",
                "incline-bench",
                "lat-pulldown",
                "leg-extension",
                "leg-press",
                "lying-down-leg-curl",
                "overhead-shoulder-press",
                "pulley-machine",
                "seated-cable-row",
                "seated-leg-curl",
                "smith-machine",
                "squat-rack",
                "torso-rotation-machine"
            )
            prediction = classes[maxPos]
            binding.tvResult.text = prediction
            model.close()

            // Releases model resources if no longer used.
        } catch (e: IOException) {
            // TODO Handle the exception
        }
    }
}