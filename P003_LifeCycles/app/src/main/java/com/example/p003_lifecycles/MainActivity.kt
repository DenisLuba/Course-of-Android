package com.example.p003_lifecycles

import android.content.Intent
import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.p003_lifecycles.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    fun log(message: String) = Log.d(TAG, message)

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

    override fun onStart() {
        super.onStart()
        log("onStart")
    }

    override fun onResume() {
        super.onResume()
        log("onResume")
    }

    override fun onPause() {
        super.onPause()
        log("onPause")
    }

    override fun onStop() {
        super.onStop()
        log("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        log("onSaveInstanceState")
    }

    companion object {
        @JvmStatic val TAG = "myLogs"
    }
}