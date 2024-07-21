package com.darovsky.lesson2

import android.os.Bundle
import android.widget.Button
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

const val KEY = "key"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.buttonNavigateToSecondActivity)
        button.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java).apply {
                putExtra(KEY, "notification")
            }
            startActivity(intent)
        }
    }
}