package com.example.practical3

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MediaPlayer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_media_player)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Music Player
        fun playPause()
        {
            Intent(applicationContext, MediaPlayerService::class.java).putExtra("ServiceData","PlayPause").apply{startService(this)}
        }

        fun stop()
        {
            Intent(applicationContext, MediaPlayerService::class.java).apply{stopService(this)}
        }

        findViewById<FloatingActionButton>(R.id.play).setOnClickListener {
            playPause()
        }

        findViewById<FloatingActionButton>(R.id.stop).setOnClickListener {
            stop()
        }
    }
}