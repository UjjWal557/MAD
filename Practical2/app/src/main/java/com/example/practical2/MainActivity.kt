package com.example.practical2

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

import android.util.Log//LogCat

class MainActivity : AppCompatActivity() {
    private val TAG="ActivityLifeCycle" //Logcat


    //SnackBar, must use lateinit, as snack variable is being assigned for the first time
    private lateinit var snack : ConstraintLayout //SnackBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        Log.i(TAG,"onCreate called") //Logcat

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //SnackBar
        snack=findViewById(R.id.main)

        //For onCreate, that's why inside onCreate
        Toast.makeText(this, "Toast created", Toast.LENGTH_SHORT).show()

        //SnackBar
        Snackbar.make(snack,"Snackbar created", Snackbar.LENGTH_SHORT).show()
    }


    //SnackBar
    /*
    override fun onPause() {
        super.onPause()
        Snackbar.make(snack,"App Paused",Snackbar.LENGTH_INDEFINITE).setAction("Close", View.OnClickListener{
            Toast.makeText(this, "Closed", Toast.LENGTH_SHORT).show()
        }).show()

    }
     */

    //Toast Message for some events
    /*
    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "Resumed", Toast.LENGTH_SHORT).show()
    }
    override fun onPause() {
        super.onPause()
        Toast.makeText(this, "App is Paused", Toast.LENGTH_SHORT).show()
    }
    */


    //LogCat
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
        Toast.makeText(this, "App is Paused", Toast.LENGTH_SHORT).show()
        Snackbar.make(snack,"App Paused",Snackbar.LENGTH_INDEFINITE).setAction("Close", View.OnClickListener{
            Toast.makeText(this, "Closed", Toast.LENGTH_SHORT).show()
        }).show()
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }
}
