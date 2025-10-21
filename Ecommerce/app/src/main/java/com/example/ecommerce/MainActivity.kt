package com.example.ecommerce

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.signupButton).setOnClickListener {
            Intent(this, Register::class.java).also { startActivity(it) }
        }

        findViewById<AppCompatButton>(R.id.loginButton).setOnClickListener {
            performLogin()
        }
    }

    private fun performLogin() {
        val emailField = findViewById<EditText>(R.id.emailField)
        val passwordField = findViewById<EditText>(R.id.passwordField)

        val email = emailField.text.toString().trim()
        val password = passwordField.text.toString()

        // Clear previous errors
        emailField.error = null
        passwordField.error = null

        // Validate email
        if (email.isEmpty()) {
            emailField.error = getString(R.string.error_email_required)
            emailField.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailField.error = getString(R.string.error_invalid_email)
            emailField.requestFocus()
            return
        }

        // Validate password
        if (password.isEmpty()) {
            passwordField.error = getString(R.string.error_password_required)
            passwordField.requestFocus()
            return
        }

        if (password.length < 6) {
            passwordField.error = getString(R.string.error_password_too_short)
            passwordField.requestFocus()
            return
        }

        // TODO: Implement actual authentication with backend/Firebase
        // For now, accept any valid email/password combination
        Toast.makeText(this, getString(R.string.login_successful), Toast.LENGTH_SHORT).show()
        Intent(this, HomeActivity::class.java).also {
            startActivity(it)
            finish() // Prevent going back to login
        }
    }
}