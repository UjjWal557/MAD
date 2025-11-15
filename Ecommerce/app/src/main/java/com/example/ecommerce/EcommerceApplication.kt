package com.example.ecommerce

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class EcommerceApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Apply saved theme preference on app start
        val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}