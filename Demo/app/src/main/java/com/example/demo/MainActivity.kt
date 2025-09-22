package com.example.demo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
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
      /*  findViewById<Button>(R.id.browse).setOnClickListener()
        {
            val msg=findViewById<EditText>(R.id.edittext1).text.toString()
            Intent(Intent.ACTION_VIEW,msg.toUri()).also { startActivity(it) }
        }

        findViewById<Button>(R.id.call).setOnClickListener()
        {
            val num=findViewById<EditText>(R.id.edittext2)
            Intent(Intent.ACTION_DIAL,("tel:$num").toUri()).also{startActivity(it)}
        }
        */

        findViewById<Button>(R.id.browse).setOnClickListener()
        {
             //Intent(Intent.ACTION_VIEW).setData("content://call_log/calls".toUri()).also { startActivity(it) }
            val show = Intent(Intent.ACTION_VIEW)
            show.setData("content://call_log/calls".toUri())
            startActivity(show)
        }

        findViewById<Button>(R.id.call).setOnClickListener()
        {
             //Intent(Intent.ACTION_VIEW).setType("image/*").also{startActivity(it)}
            val show = Intent(Intent.ACTION_VIEW)
            show.setType("image/*")
            startActivity(show)
        }
}
}