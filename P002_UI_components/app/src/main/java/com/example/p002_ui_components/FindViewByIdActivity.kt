package com.example.p002_ui_components

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class FindViewByIdActivity : AppCompatActivity() {

    lateinit var helloWorldTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        helloWorldTextView = findViewById(R.id.helloWorldId)
        helloWorldTextView.setTextColor(android.graphics.Color.RED)

        helloWorldTextView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}