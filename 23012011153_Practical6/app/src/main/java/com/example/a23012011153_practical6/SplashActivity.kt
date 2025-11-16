package com.example.a23012011153_practical6

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity(), Animation.AnimationListener {
    lateinit var guniFrameAnimation: AnimationDrawable
    lateinit var guniLogo: ImageView
    lateinit var animgrowspine: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        guniLogo = findViewById(R.id.uvpce_logo)
        guniLogo.setBackgroundResource(R.drawable.uvpce_animation)
        guniFrameAnimation = guniLogo.background as AnimationDrawable
        animgrowspine = AnimationUtils.loadAnimation(this, R.anim.splash_animation)
        animgrowspine.setAnimationListener(this)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            guniFrameAnimation.start()
            guniLogo.startAnimation(animgrowspine)
        } else
            guniFrameAnimation.stop()
    }

    override fun onAnimationEnd(animation: Animation?) {
        Intent(this, MainActivity::class.java).also { startActivity(it) }
    }

    override fun onAnimationRepeat(animation: Animation?) {
    }

    override fun onAnimationStart(animation: Animation?) {

    }
}
