package com.example.p004_workingwithactivitystate

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.p004_workingwithactivitystate.databinding.ActivityMainBinding
import kotlin.properties.Delegates.notNull
import kotlin.random.Random

class SimpleState2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

//    делегирование (by) для отложенной инициализации примитивов (вроде lateinit для не примитивов)
    private var counterValue by notNull<Int>()
    private var counterTextColor by notNull<Int>()
    private var counterIsVisible by notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        with(binding) {
            btnIncrement.setOnClickListener { onClickIncrement() }
            btnRandomColor.setOnClickListener { onClickRandomColor() }
            btnSwitchVisibility.setOnClickListener { onClickSwitchVisibility() }
        }

        if (savedInstanceState == null) {
            counterValue = 0
            counterTextColor = ContextCompat.getColor(this, R.color.purple_700)
            counterIsVisible = true
        } else {
            counterValue = savedInstanceState.getInt(KEY_COUNTER)
            counterTextColor = savedInstanceState.getInt(KEY_COLOR)
            counterIsVisible = savedInstanceState.getBoolean(KEY_IS_VISIBLE)
        }
        renderState()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_COUNTER, counterValue)
        outState.putInt(KEY_COLOR, counterTextColor)
        outState.putBoolean(KEY_IS_VISIBLE, counterIsVisible)
    }

    private fun onClickIncrement() {
        counterValue++
        renderState()
    }

    private fun onClickRandomColor() {
        counterTextColor = Color.rgb(
            Random.nextInt(256), // red
            Random.nextInt(256), // green
            Random.nextInt(256)  // blue
        )
        renderState()
    }

    private fun onClickSwitchVisibility() = with(binding.textViewCounter) {
        counterIsVisible = !counterIsVisible
        renderState()
    }

    private fun renderState() = with (binding) {
        textViewCounter.text = counterValue.toString()
        textViewCounter.setTextColor(counterTextColor)
        textViewCounter.visibility = if (counterIsVisible) View.VISIBLE else View.INVISIBLE
    }

    companion object {
        @JvmStatic private val KEY_COUNTER = "COUNTER"
        @JvmStatic private val KEY_COLOR = "COLOR"
        @JvmStatic private val KEY_IS_VISIBLE = "IS_VISIBLE"
    }
}