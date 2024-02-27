package com.example.hw14_camera

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    lateinit var imageView: ImageView
    lateinit var button: Button
    lateinit var capturedImage: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById<ImageView>(R.id.imageView)
        button = findViewById<Button>(R.id.button)

        capturedImage = createImageFile()

        button.setOnClickListener {
            val photoURI = FileProvider.getUriForFile(
                this,
                "com.example.hw14_camera.fileprovider",
                capturedImage
            )

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

            // Grant permission to the receiving app
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            startActivityForResult(intent, 0)
        }
    }


    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            loadImage(capturedImage)
        }
    }

    private fun loadImage(file: File) {
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, file.toUri())
        imageView.setImageBitmap(bitmap)
    }
}
