    package com.example.gymguide.ui

    import android.Manifest
    import android.app.Activity
    import android.content.Intent
    import android.content.pm.PackageManager
    import android.graphics.Bitmap
    import android.media.ThumbnailUtils
    import android.net.Uri
    import android.os.Bundle
    import android.provider.MediaStore
    import androidx.activity.result.ActivityResultLauncher
    import androidx.activity.result.contract.ActivityResultContracts
    import androidx.appcompat.app.AppCompatActivity
    import com.example.gymguide.databinding.ActivityCameraBinding
    import com.example.gymguide.ml.GymEquipmentV3
    import org.tensorflow.lite.DataType
    import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
    import java.io.IOException
    import java.nio.ByteBuffer
    import java.nio.ByteOrder


    /** Activity that displays the camera and performs object detection on the incoming frames */
    class CameraActivity : AppCompatActivity() {
        private var imageSize = 150
        private lateinit var binding: ActivityCameraBinding
        // ActivityResultLauncher for camera capture
        private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
            private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityCameraBinding.inflate(layoutInflater)
            setContentView(binding.root)

            // Initialize the ActivityResultLauncher
            cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                onCameraActivityResult(result)
            }
            galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                onGalleryActivityResult(result)
            }

            binding.cameraButton.setOnClickListener {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    // Start the activity using the launcher
                    cameraLauncher.launch(cameraIntent)
                } else {
                    requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
                }
            }

            binding.galleryButton.setOnClickListener {
                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                // Start the activity using the launcher
                galleryLauncher.launch(galleryIntent)
            }
        }

        private fun onCameraActivityResult(result: androidx.activity.result.ActivityResult) {
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                if (data != null && data.hasExtra("data")) {
                    var image = data.extras?.get("data") as Bitmap?
                    val dimension = image!!.width.coerceAtMost(image.height)
                    image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
                    binding.imageView.setImageBitmap(image)
                    // Resize the image before classification
                    image = resizeImage(image!!, imageSize)
                    classifyImage(image)
                }
            }
        }

        private fun onGalleryActivityResult(result: androidx.activity.result.ActivityResult) {
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null && data.data != null) {
                    val dat: Uri = data.data!!
                    var image: Bitmap?
                    try {
                        image = MediaStore.Images.Media.getBitmap(contentResolver, dat)
                        binding.imageView.setImageBitmap(image)
                        image = Bitmap.createScaledBitmap(image!!, imageSize, imageSize, false)
                        classifyImage(image)
                    } catch (e: IOException) {
                        e.printStackTrace()
                        // Handle the exception
                    }
                }
            }
        }

        private fun resizeImage(image: Bitmap, targetSize: Int): Bitmap {
            val aspectRatio: Float = image.width.toFloat() / image.height.toFloat()
            val targetWidth: Int
            val targetHeight: Int

            if (image.width >= image.height) {
                targetWidth = targetSize
                targetHeight = (targetSize / aspectRatio).toInt()
            } else {
                targetWidth = (targetSize * aspectRatio).toInt()
                targetHeight = targetSize
            }

            return Bitmap.createScaledBitmap(image, targetWidth, targetHeight, false)
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

                binding.tvResult.text = classes[maxPos]

                // Releases model resources if no longer used.
                model.close()
            } catch (e: IOException) {
                // TODO Handle the exception
            }
        }
    }