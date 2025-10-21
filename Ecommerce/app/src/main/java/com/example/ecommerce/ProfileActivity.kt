package com.example.ecommerce

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupBottomNavigation()
        setupButtons()
    }

    private fun setupBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.navigation_profile

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                    true
                }
                R.id.navigation_cart -> {
                    startActivity(Intent(this, CartActivity::class.java))
                    finish()
                    true
                }
                R.id.navigation_profile -> {
                    true
                }
                else -> false
            }
        }
    }

    private fun setupButtons() {
        findViewById<com.google.android.material.button.MaterialButton>(R.id.buttonEditProfile).setOnClickListener {
            Toast.makeText(this, getString(R.string.feature_coming_soon), Toast.LENGTH_SHORT).show()
        }

        findViewById<com.google.android.material.button.MaterialButton>(R.id.buttonSettings).setOnClickListener {
            Toast.makeText(this, getString(R.string.feature_coming_soon), Toast.LENGTH_SHORT).show()
        }
    }
}