package com.example.practical3

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MediaPlayerService : Service() {
        lateinit var mediaPlayer: MediaPlayer // object of media class
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!this::mediaPlayer.isInitialized)
        {
            mediaPlayer= MediaPlayer.create(this,R.raw.song)//create raw folder in res directory for this song
        }
        if(intent!=null)
        {
            val strData=intent.getStringExtra("ServiceData")
            if(strData=="PlayPause")
            {
                if (!mediaPlayer.isPlaying) {
                    mediaPlayer.start()
                }
                else{
                    mediaPlayer.pause()
                }
            }
        }
        else
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