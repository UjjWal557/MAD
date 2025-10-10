package com.example.smartecommerceanalyzer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        // Apply window insets for the edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- Find views by their ID ---
        val nameTextView = findViewById<TextView>(R.id.textViewName)
        val emailTextView = findViewById<TextView>(R.id.textViewEmail)
        val phoneTextView = findViewById<TextView>(R.id.textViewPhone)
        val addressTextView = findViewById<TextView>(R.id.textViewAddress)
        val logoutButton = findViewById<Button>(R.id.buttonLogout)

        // --- Display Dummy Profile Data ---
        // In a real app, this data would come from a user database or login session
        nameTextView.text = "Name: Android User"
        emailTextView.text = "Email: user@android.com"
        phoneTextView.text = "Phone: +91 12345 67890"
        addressTextView.text = "Address: 1600 Amphitheatre Parkway, Mountain View, CA"

        // --- Logout Button Logic ---
        logoutButton.setOnClickListener {
            // Create an intent to go back to the MainActivity (login screen)
            val intent = Intent(this, MainActivity::class.java)

            // These flags clear the entire task history, so the user can't press
            // the back button to return to the profile screen after logging out.
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }
}