package com.example.practical3

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.MediaStore
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText
import androidx.core.net.toUri

class MainActivity : AppCompatActivity() {
    private lateinit var e1: EditText
    private lateinit var e2: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        e1= findViewById(R.id.editText1)
        val btn1: Button = findViewById(R.id.btn1)

        e2= findViewById(R.id.editText2)
        val btn2: Button = findViewById(R.id.btn2)

        val btn3: Button = findViewById(R.id.btn3)

        val btn4: Button = findViewById(R.id.btn4)

        val btn5: Button = findViewById(R.id.btn5)

        val btn6: Button = findViewById(R.id.btn6)

        //URL
        btn1.setOnClickListener()
        {
          val msg= e1.text.toString()
          if (msg.isNotEmpty())
          {
              val show = Intent(Intent.ACTION_VIEW, msg.toUri())
              startActivity(show)
          }
        }

        //Call
        btn2.setOnClickListener()
        {
            val num= e2.text.toString()
            if (num.isNotEmpty())
            {
                val show = Intent(Intent.ACTION_DIAL, ("tel:$num").toUri())
                startActivity (show)
            }
        }

        //Call Log
        btn3.setOnClickListener()
        {
            val show = Intent(Intent.ACTION_VIEW)
            show.setData("content://call_log/calls".toUri())
            startActivity(show)
        }

        //Gallery
        btn4.setOnClickListener()
        {
            val show = Intent(Intent.ACTION_VIEW).setType("image/*")
            startActivity(show)
        }

        //Camera
        btn5.setOnClickListener()
        {
            val show = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(show)
        }

        //Alarm
        btn6.setOnClickListener()
        {
            val show = Intent(AlarmClock.ACTION_SHOW_ALARMS)
            startActivity(show)
        }

        //Login
        findViewById<Button>(R.id.btn7).setOnClickListener {
            Intent(this, LoginActivity::class.java).also { startActivity(it) }
        }

        //Music
        findViewById<Button>(R.id.btn8).setOnClickListener {
            Intent(this, MediaPlayer::class.java).also{startActivity(it)}
        }
    }
}