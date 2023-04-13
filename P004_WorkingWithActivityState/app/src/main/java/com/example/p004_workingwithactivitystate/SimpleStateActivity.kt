package com.example.p004_workingwithactivitystate

/**
 * P004_WorkingWithActivityState
 *
 * EditText сохраняет текст при повороте экрана, но не сохраняет цвет и видимость
 */
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.example.p004_workingwithactivitystate.databinding.ActivityMainBinding
import kotlin.random.Random


class SimpleStateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        with(binding) {
            btnIncrement.setOnClickListener { onClickIncrement() }
            btnRandomColor.setOnClickListener { onClickRandomColor() }
            btnSwitchVisibility.setOnClickListener { onClickSwitchVisibility() }
        }
    }


    private fun onClickIncrement() {
        var counter: Int = binding.textViewCounter.text.toString().toInt()
        counter++
        binding.textViewCounter.text = counter.toString()
    }

    private fun onClickRandomColor() {
        val randomColor = Color.rgb(
            Random.nextInt(256), // red
            Random.nextInt(256), // green
            Random.nextInt(256)  // blue
        )
        binding.textViewCounter.setTextColor(randomColor)
    }

    private fun onClickSwitchVisibility() = with(binding.textViewCounter) {
        visibility = if (visibility == VISIBLE) INVISIBLE
        else VISIBLE
    }
}