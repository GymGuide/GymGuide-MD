package com.example.gymguide.ui

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.gymguide.databinding.FragmentScanBinding
import java.io.IOException

class ScanFragment : Fragment() {
    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!

    private var imageSize = 150
    // ActivityResultLauncher for camera capture
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the ActivityResultLauncher
        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                onCameraActivityResult(result)
            }
        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                onGalleryActivityResult(result)
            }

        binding.ivScanMeal.setOnClickListener {
            showPickImageDialog()
        }

    }

    private fun showPickImageDialog() {
        val builderSingle = AlertDialog.Builder(requireContext()) // Use 'this' instead of 'MainActivity.this'
        builderSingle.setTitle("Select One Option")
        val arrayAdapter = ArrayAdapter<String>(
            requireContext(),  // Use 'this' instead of 'MainActivity.this'
            android.R.layout.select_dialog_singlechoice
        ) // Use the correct layout resource ID
        arrayAdapter.add("Camera")
        arrayAdapter.add("Gallery")
        builderSingle.setNegativeButton(
            "cancel"
        ) { dialog, _ -> dialog.dismiss() }
        builderSingle.setAdapter(
            arrayAdapter
        ) { _, which ->
            when (which) {
                0 -> {
                    if (ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        cameraLauncher.launch(cameraIntent)
                    } else {
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(Manifest.permission.CAMERA),
                            100
                        )
                    }
                }

                1 -> {
                    val galleryIntent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    // Start the activity using the launcher
                    galleryLauncher.launch(galleryIntent)
                }
            }
        }
        builderSingle.show()
    }

    @Suppress("DEPRECATION")
    private fun onCameraActivityResult(result: androidx.activity.result.ActivityResult) {
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val data: Intent? = result.data
            if (data != null && data.hasExtra("data")) {
                //Non-deprecated method require API 33 (Tiramisu)
                //Use the type-safer getParcelableExtra(String, Class) starting from Android Build.VERSION_CODES.TIRAMISU.
                var image = data.getParcelableExtra<Bitmap>("data")
                val dimension = image!!.width.coerceAtMost(image.height)
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
                val intent = Intent(requireContext(), ClassifyEquipActivity::class.java)
                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
                intent.putExtra("image",image)
                startActivity(intent)
                //binding.imageView.setImageBitmap(image)
                //image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
                //classifyImage(image)
            }
        }
    }

    private fun onGalleryActivityResult(result: androidx.activity.result.ActivityResult) {
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val data: Intent? = result.data
            if (data != null && data.data != null) {
                val dat: Uri = data.data!!
                var image: Bitmap?
                try {
                    val source = ImageDecoder.createSource(requireContext().contentResolver, dat)
                    image = ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
                        decoder.setTargetSampleSize(1) // shrinking by
                        decoder.isMutableRequired =
                            true // this resolves the hardware type of bitmap problem
                    }
                    val intent = Intent(requireContext(), ClassifyEquipActivity::class.java)
                    image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
                    intent.putExtra("image", image)
                    startActivity(intent)
                    //binding.imageView.setImageBitmap(image)
                    //image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
                    //classifyImage(image)
                } catch (e: IOException) {
                    e.printStackTrace()
                    // Handle the exception
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
