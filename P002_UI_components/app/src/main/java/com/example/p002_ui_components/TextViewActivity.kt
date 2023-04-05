package com.example.p002_ui_components

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.p002_ui_components.databinding.TextViewActivityBinding

class TextViewActivity : AppCompatActivity() {

    lateinit var binding: TextViewActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TextViewActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.helloWorldId) {
            setText(R.string.hy)
            setTextColor(Color.GREEN)
            setLines(3)
        }


    }
}