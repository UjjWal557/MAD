package com.example.intentsdemo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.CallLog
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // EditTexts for user input
        val urlField = findViewById<EditText>(R.id.urlField)
        val phoneField = findViewById<EditText>(R.id.phoneField)

        // Buttons
        val browseBtn = findViewById<Button>(R.id.btnBrowse)
        val callBtn = findViewById<Button>(R.id.btnCall)
        val callLogBtn = findViewById<Button>(R.id.btnCallLog)
        val galleryBtn = findViewById<Button>(R.id.btnGallery)
        val cameraBtn = findViewById<Button>(R.id.btnCamera)
        val alarmBtn = findViewById<Button>(R.id.btnAlarm)
        val loginBtn = findViewById<Button>(R.id.btnLogin)

        // 🌐 Open Browser
        browseBtn.setOnClickListener {
            val url = urlField.text.toString()
            if (url.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }

        // 📞 Dial Phone Number
        callBtn.setOnClickListener {
            val phone = phoneField.text.toString()
            if (phone.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                startActivity(intent)
            }
        }

        // 📜 Open Call Log
        callLogBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                type = CallLog.Calls.CONTENT_TYPE
            }
            startActivity(intent)
        }

        // 🖼️ Open Gallery
        galleryBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivity(intent)
        }

        // 📷 Open Camera
        cameraBtn.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(intent)
        }

        // ⏰ Open Alarm App
        alarmBtn.setOnClickListener {
            val intent = Intent(AlarmClock.ACTION_SHOW_ALARMS)
            startActivity(intent)
        }

        // 🔑 Open Login Activity (custom screen)
        loginBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
