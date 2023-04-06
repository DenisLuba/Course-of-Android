package com.example.p002_ui_components

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.p002_ui_components.databinding.ActivityRadioButtonBinding

class RadioButtonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRadioButtonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRadioButtonBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}