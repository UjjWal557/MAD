package com.example.alarm

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class AlarmService : Service() {
    lateinit var mediaPlayer: MediaPlayer// object of media class
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!this::mediaPlayer.isInitialized)
        {
            mediaPlayer= MediaPlayer.create(this,R.raw.alarm)//create raw folder in res directory for this song
        }
        if(intent!=null)
        {
                    mediaPlayer.start()
            }

        return START_STICKY
    }

    override fun onDestroy() {
        mediaPlayer.stop()
        super.onDestroy()
    }

}