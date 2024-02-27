package com.example.hw14_camera

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import java.io.File


class MainActivity : AppCompatActivity() {
    lateinit var imageView : ImageView
    lateinit var button : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) {
            success: Boolean ->
            if (success) {
                // The image was saved into the given Uri -> do something with it
                //val imgFile = Uri.parse("android.resource://com.example.hw14_camera/img.jpg")
                val file = FileProvider.getUriForFile(this, "photos", File("img.jpg"))
                val imageStream = contentResolver.openInputStream(file)
                val bitmap = BitmapFactory.decodeStream(imageStream)
                imageView.setImageURI(file)
                imageStream?.close()
            }
        }

        imageView = findViewById<ImageView>(R.id.imageView)
        button = findViewById<Button>(R.id.button)
        val file = FileProvider.getUriForFile(this, "photos", File("img.jpg"))
        val imgFile = file
        button.setOnClickListener {
            takePicture.launch(imgFile)
        }
    }

}