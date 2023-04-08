package com.example.p003_lifecycles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.p003_lifecycles.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        fun log(message: String) = Log.d("myLogs", message)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        log("onCreate, savedInstanceState = $savedInstanceState")

        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.buttonAnotherActivity.setOnClickListener {
            startActivity(Intent(this, SecondaryLifecycleActivity::class.java))
        }

        binding.buttonTransparentActivity.setOnClickListener {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }

        binding.buttonMinimizeApp.setOnClickListener {
            moveTaskToBack(false)
        }
    }
}