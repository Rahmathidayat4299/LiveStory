package com.dicoding.livestory.addlivestory

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.dicoding.livestory.R
import com.dicoding.livestory.databinding.ActivityAddStoryBinding
import com.dicoding.livestory.story.CameraActivity
import com.dicoding.livestory.story.ListStory
import com.dicoding.livestory.util.*
import com.dicoding.livestory.viewmodel.ViewModelFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import com.dicoding.livestory.model.Result

class AddStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStoryBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var file: File? = null
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Don't have  a permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                CODE_PERMISSIONS
            )
        }
        binding.btnCamera.setOnClickListener { cameraX() }
        binding.btnGallery.setOnClickListener { imageFromGaleri() }
        binding.addStory.setOnClickListener { uploadImage() }
    }

    private val imageByGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@AddStoryActivity)
            file = myFile
            val resultImage = rotateBitmap(
                BitmapFactory.decodeFile(file?.path),
            )
            binding.imagePreview.setImageBitmap(resultImage)
        }
    }

    private fun imageFromGaleri() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        imageByGallery.launch(chooser)
    }

    private fun cameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFiles = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            file = myFiles
            val result = rotateBitmap(
                BitmapFactory.decodeFile(file?.path),
                isBackCamera
            )

            binding.imagePreview.setImageBitmap(result)
        }
    }

    private fun uploadImage() {
        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[AddLiveStoryVm::class.java]
        sharedPreferences = SharedPreferences(this)
        if (file != null && !binding.edtDescription.text.isNullOrEmpty()) {
            val files = reduceFileImage(file as File)
            val description = binding.edtDescription.text.toString()
                .toRequestBody("text/plain".toMediaType())
            val requestImageFile = files.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                files.name,
                requestImageFile
            )
            viewModel.uploadStory( sharedPreferences.getToken(),description,imageMultipart)
                .observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is Result.Loading -> {
                                binding.progressBarUpload.visible()
                            }
                            is Result.Success -> {
                                binding.progressBarUpload.gone()
                                Toast.makeText(this, "Success Upload story", Toast.LENGTH_SHORT)
                                    .show()
                                Intent(this, ListStory::class.java).also { startActivity(it) }
                            }
                            is Result.Error -> {
                                binding.progressBarUpload.gone()
                                Toast.makeText(this, "upload failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }


        }
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val CODE_PERMISSIONS = 10
    }
}