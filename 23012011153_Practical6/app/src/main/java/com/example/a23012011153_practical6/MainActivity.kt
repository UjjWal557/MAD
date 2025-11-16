package com.example.a23012011153_practical6


import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

/* Without the functionality of create/stop alarm
class MainActivity : AppCompatActivity(), Animation.AnimationListener {
    lateinit var alarmAnimation: AnimationDrawable
    lateinit var heartAnimation: AnimationDrawable
    lateinit var alarmImage: ImageView
    lateinit var heartImage: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus){
            alarmImage=findViewById(R.id.img)
            alarmImage.setBackgroundResource(R.drawable.alarm_animation)
            alarmAnimation=alarmImage.background as AnimationDrawable
            heartImage=findViewById<ImageView>(R.id.img2)
            heartImage.setBackgroundResource(R.drawable.heart_animation)
            heartAnimation=heartImage.background as AnimationDrawable
            alarmAnimation.start()
            heartAnimation.start()
        }
        else{
            alarmAnimation.stop()
            heartAnimation.stop()
        }
    }

    override fun onAnimationEnd(animation: Animation?) {
        Intent(this, MainActivity::class.java).also { startActivity(it) }
    }

    override fun onAnimationRepeat(animation: Animation?) {

    }

    override fun onAnimationStart(animation: Animation?) {

    }
}*/

//With the functionality of create/stop alarm
class MainActivity : AppCompatActivity() {
    lateinit var alarmAnimation: AnimationDrawable
    lateinit var heartAnimation: AnimationDrawable
    lateinit var alarmImage: ImageView
    lateinit var heartImage: ImageView
    lateinit var btnCreateAlarm: MaterialButton
    lateinit var btnCancelAlarm: MaterialButton

    private var mediaPlayer: MediaPlayer? = null
    private var isAlarmActive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnCreateAlarm = findViewById(R.id.btn1)
        btnCancelAlarm = findViewById(R.id.btnCancel)

        btnCreateAlarm.setOnClickListener {
            createAlarm()
        }

        btnCancelAlarm.setOnClickListener {
            cancelAlarm()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus){
            alarmImage = findViewById(R.id.img)
            alarmImage.setBackgroundResource(R.drawable.alarm_animation)
            alarmAnimation = alarmImage.background as AnimationDrawable

            heartImage = findViewById<ImageView>(R.id.img2)
            heartImage.setBackgroundResource(R.drawable.heart_animation)
            heartAnimation = heartImage.background as AnimationDrawable

            alarmAnimation.start()
            heartAnimation.start()
        }
        else{
            alarmAnimation.stop()
            heartAnimation.stop()
        }
    }

    private fun createAlarm() {
        if (!isAlarmActive) {
            isAlarmActive = true

            // Start alarm sound
            try {
                mediaPlayer = MediaPlayer.create(this, R.raw.alarm)
                mediaPlayer?.isLooping = true
                mediaPlayer?.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // Update button states
            btnCreateAlarm.isEnabled = false
            btnCancelAlarm.isEnabled = true
        }
    }

    private fun cancelAlarm() {
        if (isAlarmActive) {
            isAlarmActive = false

            // Stop alarm sound
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.stop()
                }
                it.release()
            }
            mediaPlayer = null

            // Update button states
            btnCreateAlarm.isEnabled = true
            btnCancelAlarm.isEnabled = false
        }
    }

    /*
     These creates infinite loop, and as we remove this, remove Animation.AnimationListener implementation from MainActivity
    override fun onAnimationEnd(animation: Animation?) {
             Intent(this, MainActivity::class.java).also { startActivity(it) }
    }

    override fun onAnimationRepeat(animation: Animation?) {
        // Not used
    }

    override fun onAnimationStart(animation: Animation?) {
        // Not used
    }
    */

    override fun onPause() {
        super.onPause()
        // Pause sound when app goes to background
        mediaPlayer?.pause()
    }

    override fun onResume() {
        super.onResume()
        // Resume sound if alarm was active
        if (isAlarmActive) {
            mediaPlayer?.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release media player resources
        mediaPlayer?.release()
        mediaPlayer = null
    }
}