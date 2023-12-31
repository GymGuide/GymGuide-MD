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
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.gymguide.R
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

        binding.ivScanEquip.setOnClickListener {
            showPickImageDialogEquip()
        }

        binding.ivConsultTrainer.setOnClickListener {
            val intent = Intent(requireContext(), TrainerActivity::class.java)
            startActivity(intent)
        }

        binding.muscleMapButton.setOnClickListener {
            val intent = Intent(requireContext(), MuscleMapActivity::class.java)
            startActivity(intent)
        }

        binding.ivScanMeal.setOnClickListener {
            Toast.makeText(
                context,
                "scan food is still under development",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.ivNearbyLocation.setOnClickListener {
            showMapsFragment()
        }
    }

    private fun showMapsFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, MapsFragment(), MapsFragment::class.java.simpleName)
            .addToBackStack(null)
            .commit()
    }

    private fun showPickImageDialogEquip() {
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
                        Toast.makeText(context, "Permission denied, please enable it via settings", Toast.LENGTH_SHORT).show()
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
                var picture = data.getParcelableExtra<Bitmap>("data")
                val dimension = picture!!.width.coerceAtMost(picture.height)
                picture = ThumbnailUtils.extractThumbnail(picture, dimension, dimension)
                val intent = Intent(requireContext(), ClassifyEquipActivity::class.java)
                picture = Bitmap.createScaledBitmap(picture, imageSize, imageSize, false)
                intent.putExtra("picture",picture)
                startActivity(intent)
            }
        }
    }

    private fun onGalleryActivityResult(result: androidx.activity.result.ActivityResult) {
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val data: Intent? = result.data
            if (data != null && data.data != null) {
                val dat: Uri = data.data!!
                var picture: Bitmap?
                try {
                    val source = ImageDecoder.createSource(requireContext().contentResolver, dat)
                    picture = ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
                        decoder.setTargetSampleSize(1) // shrinking by
                        decoder.isMutableRequired =
                            true // this resolves the hardware type of bitmap problem
                    }
                    val intent = Intent(requireContext(), ClassifyEquipActivity::class.java)
                    picture = Bitmap.createScaledBitmap(picture, imageSize, imageSize, false)
                    intent.putExtra("picture", picture)
                    startActivity(intent)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
