package com.example.ecommerce

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ecommerce.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupBottomNavigation()
        setupButtons()
        setupDarkModeToggle()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.selectedItemId = R.id.navigation_profile

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
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
        binding.buttonEditProfile.setOnClickListener {
            Toast.makeText(this, getString(R.string.feature_coming_soon), Toast.LENGTH_SHORT).show()
        }

        binding.buttonSettings.setOnClickListener {
            Toast.makeText(this, getString(R.string.feature_coming_soon), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupDarkModeToggle() {
        val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)

        // Set the initial state of the switch
        binding.switchDarkMode.isChecked = isDarkMode

        // Set up the toggle listener
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            // Save preference
            with(sharedPreferences.edit()) {
                putBoolean("dark_mode", isChecked)
                apply()
            }

            // Apply theme immediately
            val nightMode = if (isChecked) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(nightMode)
        }
    }
}