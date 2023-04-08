package com.example.p003_lifecycles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SecondaryLifecycleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secondary_lifecycle)
    }
}