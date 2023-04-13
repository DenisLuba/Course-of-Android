package com.example.p004_workingwithactivitystate

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.p004_workingwithactivitystate.databinding.ActivityMainBinding
import kotlin.random.Random
import java.io.Serializable

/**
 *  Класс, имплементирующий интерфейс Serializable, не экономный класс.
 *  Занимает много места (а у Bundle есть ограничение по размеру), долго грузится.
 */

class SimpleState3Activity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var state: State

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        with (binding) {
            btnIncrement.setOnClickListener { increment() }
            btnRandomColor.setOnClickListener { setRandomColor() }
            btnSwitchVisibility.setOnClickListener { switchVisibility() }
        }

        state = if (savedInstanceState == null) {
            State(
                counterValue = 0,
                counterTextColor = ContextCompat.getColor(this, R.color.purple_700),
                counterIsVisible = true
            )
        }
        else savedInstanceState.getSerializable(KEY_STATE) as State

        renderState()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY_STATE, state)
    }

    private fun increment() {
        state.counterValue++
        renderState()
    }

    private fun setRandomColor() {
        state.counterTextColor = Color.rgb(
            Random.nextInt(256), // red
            Random.nextInt(256), // green
            Random.nextInt(256), // blue
        )
        renderState()
    }

    private fun switchVisibility() {
        state.counterIsVisible = !state.counterIsVisible
        renderState()
    }

    private fun renderState() = with (binding.textViewCounter) {
        text = state.counterValue.toString()
        setTextColor(state.counterTextColor)
        visibility = if (state.counterIsVisible) View.VISIBLE else View.INVISIBLE
    }
    class State(
        var counterValue: Int,
        var counterTextColor: Int,
        var counterIsVisible: Boolean
    ) : Serializable

    companion object {
        @JvmStatic private val KEY_STATE = "STATE"
    }
}