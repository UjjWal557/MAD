package com.example.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View
import android.widget.TextView
import android.widget.TextClock
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var card2: MaterialCardView
    private lateinit var btnCreate: MaterialButton
    private lateinit var btnCancel: MaterialButton
    private lateinit var textAlarmTime: TextView
    private lateinit var textClock1: TextClock

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        card2 = findViewById(R.id.card2)
        btnCreate = findViewById(R.id.create_alarm)
        btnCancel = findViewById(R.id.stop_alarm)
        textAlarmTime = findViewById(R.id.alarm_time)
        textClock1 = findViewById(R.id.textClock1)

        // Hide alarm card initially
        card2.visibility = View.GONE

        // Create alarm button
        btnCreate.setOnClickListener {
            showTimePickerDialog()
        }

        // Cancel alarm button
        btnCancel.setOnClickListener {
            card2.visibility = View.GONE
        }
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val picker = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            setAlarmTime(selectedHour, selectedMinute)
        }, hour, minute, false)

        picker.show()
    }


    private fun setAlarmTime(hour: Int, minute: Int) {
        val alarmCalendar = Calendar.getInstance()
        alarmCalendar.set(Calendar.HOUR_OF_DAY, hour)
        alarmCalendar.set(Calendar.MINUTE, minute)
        alarmCalendar.set(Calendar.SECOND, 0)

        val formatter = SimpleDateFormat("hh:mm:ss a, MMM dd yyyy", Locale.getDefault())
        val formattedTime = formatter.format(alarmCalendar.time)

        // Update the alarm time display in CardView 2

        textAlarmTime.text = formattedTime

        card2.visibility = View.VISIBLE

        // Optional: schedule the alarm
        // setAlarm(alarmCalendar.timeInMillis, "Start")
    }

   /* private fun setAlarm(millisTime:Long,str:String)
    {
        val intent= Intent(this, AlarmBroadcastReceiver::class.java)
        intent.putExtra("Service1",str)
        val pendingIntent= PendingIntent.getBroadcast(applicationContext,2343243,intent,
            PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager=getSystemService(ALARM_SERVICE) as AlarmManager
        if (str=="Start")
        {
            if (alarmManager.canScheduleExactAlarms())
            {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,millisTime,pendingIntent)
                Toast.makeText(this,"Start Alarm", Toast.LENGTH_SHORT).show()
            }
            else{
                startActivity(Intent(Setting,ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
            }
        }
        else if (str="Stop")
        {
            alarmManager.cancel(pendingIntent)

        }
    }*/
}
