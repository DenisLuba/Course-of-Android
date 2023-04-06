package com.example.p002_ui_components

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.p002_ui_components.databinding.ActivityTextViewBinding

class TextViewActivity : AppCompatActivity() {

    lateinit var binding: ActivityTextViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.helloWorldId) {
            setText(R.string.hy)
            setTextColor(Color.GREEN)
            setLines(3)
        }
    }
}