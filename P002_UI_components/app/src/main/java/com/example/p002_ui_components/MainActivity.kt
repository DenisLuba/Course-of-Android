package com.example.p002_ui_components

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.p002_ui_components.databinding.ActivityMainBinding

class MainActivity() : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(saveInstanceState: Bundle?) {
        super.onCreate(saveInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetText.setOnClickListener { startActivity(Intent(this, TextViewActivity::class.java)) }
        binding.btnGetImage.setOnClickListener { startActivity(Intent(this, ImageButtonCheckEditActivity::class.java)) }
        binding.btnGetRadio.setOnClickListener { startActivity(Intent(this, RadioButtonProgressBarActivity::class.java)) }

//        Не работает
//
//        val image = ImageView(this)
//        val imageParams = ConstraintLayout.LayoutParams(300, 300)
//
//        with(image) {
//            id = R.id.imageView
//            layoutParams = imageParams
//            setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_launcher_foreground,null))
//            background = ResourcesCompat.getDrawable(resources, R.drawable.ic_launcher_background,null)
//        }
//
//
//        val constraintSet = ConstraintSet()
//
//        with(constraintSet) {
//            connect(image.id, ConstraintSet.START, binding.root.id, ConstraintSet.START)
//            connect(image.id, ConstraintSet.END, binding.root.id, ConstraintSet.END)
//            connect(image.id, ConstraintSet.TOP, binding.root.id, ConstraintSet.TOP)
//            connect(image.id, ConstraintSet.BOTTOM, binding.root.id, ConstraintSet.BOTTOM)
//
//            applyTo(binding.root)
//        }
//
//        binding.root.addView(image)
//
    }
}