package com.example.practical3

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.CallLog
import android.provider.MediaStore
import android.util.Log
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

        //Music Player
        fun stop()
        {
            Intent(applicationContext, MediaPlayerService::class.java).apply{stopService(this)}
        }

        fun playpause()
        {
            Log.d("MainActivity","Inside playpause")
            Intent(applicationContext, MediaPlayerService::class.java).putExtra("ServiceData","PlayPause").apply{startService(this)}
            Log.d("MainActivity","Inside playpuase after Intent")
        }

        findViewById<Button>(R.id.btn10).setOnClickListener {
            Log.d("MainActivity","Inside playpause")
            Intent(this, MediaPlayer::class.java).also{startActivity(it)}
            Log.d("MainActivity","Inside playpause")
        }

        e1= findViewById(R.id.editText1)
        val btn1: Button = findViewById(R.id.btn1)

        e2= findViewById(R.id.editText2)
        val btn2: Button = findViewById(R.id.btn2)

        val btn3: Button = findViewById(R.id.btn3)

        val btn4: Button = findViewById(R.id.btn4)

        val btn5: Button = findViewById(R.id.btn5)

        val btn6: Button = findViewById(R.id.btn6)

        val btn8: Button = findViewById(R.id.btn8)

        val btn9: Button = findViewById(R.id.btn9)

        //val btn7: Button = findViewById(R.id.btn7)

        btn8.setOnClickListener {
            Log.d("MainActivity","Inside play")
            playpause()
        }
        btn9.setOnClickListener {
            Log.d("MainActivity","Inside stop")
            stop()
        }

        btn1.setOnClickListener()
        {
          val msg= e1.text.toString()
          if (msg.isNotEmpty())
          {
              val show = Intent(Intent.ACTION_VIEW, msg.toUri())
              startActivity(show)
          }
        }

        btn2.setOnClickListener()
        {
            val num= e2.text.toString()
            if (num.isNotEmpty())
            {
                val show = Intent(Intent.ACTION_DIAL, ("tel:$num").toUri())
                startActivity (show)
            }
        }

        btn3.setOnClickListener()
        {
            val show = Intent(Intent.ACTION_VIEW)
            show.setData("content://call_log/calls".toUri())
            startActivity(show)
        }

        btn4.setOnClickListener()
        {
            val show = Intent(Intent.ACTION_VIEW).setType("image/*")
            startActivity(show)
        }
        btn5.setOnClickListener()
        {
            val show = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(show)
        }

        btn6.setOnClickListener()
        {
            val show = Intent(AlarmClock.ACTION_SHOW_ALARMS)
            startActivity(show)
        }
    /* Login Button */
        findViewById<Button>(R.id.btn7).setOnClickListener {
            Intent(this, LoginActivity::class.java).also { startActivity(it) }
        }
    }
}