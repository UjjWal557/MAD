package com.example.ecommerce

import android.content.Intent
import android.os.Bundle
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
            val email=findViewById<EditText>(R.id.emailField).text.toString()
            val pass=findViewById<EditText>(R.id.passwordField).text.toString()
            if(email.isNotEmpty() && pass.isNotEmpty()) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show()
                Intent(this, HomeActivity::class.java).also{startActivity(it)}
            }
            else{
                Toast.makeText(this, "Please enter your credentials", Toast.LENGTH_LONG).show()
            }
        }
    }
}