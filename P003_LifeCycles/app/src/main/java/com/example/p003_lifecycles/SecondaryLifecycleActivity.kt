package com.example.p003_lifecycles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class SecondaryLifecycleActivity : AppCompatActivity() {

    private fun log(message: String) = Log.d(TAG, message)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("onCreate, savedState = $savedInstanceState")
        setContentView(R.layout.activity_secondary_lifecycle)
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