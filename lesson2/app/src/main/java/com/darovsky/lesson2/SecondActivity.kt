package com.darovsky.lesson2

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val button = findViewById<Button>(R.id.buttonNotification)
        button.setOnClickListener {
            Toast.makeText(this, intent.getStringExtra(KEY), Toast.LENGTH_LONG).show()
        }
    }
}