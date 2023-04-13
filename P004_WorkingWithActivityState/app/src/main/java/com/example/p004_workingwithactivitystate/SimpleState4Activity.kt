package com.example.p004_workingwithactivitystate

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.p004_workingwithactivitystate.databinding.ActivityMainBinding
//import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

class SimpleState4Activity : AppCompatActivity() {
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

        state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            savedInstanceState?.getParcelable(KEY_STATE, State::class.java) ?: State(
                counterValue = 0,
                counterTextColor = ContextCompat.getColor(this, R.color.purple_700),
                counterIsVisible = true
            )
        } else {
            savedInstanceState?.getParcelable(KEY_STATE) ?: State(
                counterValue = 0,
                counterTextColor = ContextCompat.getColor(this, R.color.purple_700),
                counterIsVisible = true
            )
        }

        renderState()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_STATE, state)
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

    /**
     *  Чтобы использовать аннотацию @Parcelize необходимо в build.gradle(:app)
     *  добавить плагин:
     *
     *  plugins {
     *      id 'kotlin-parcelize'
     *  }
     *
     *  и импортировать класс import kotlinx.parcelize.Parcelize
     */
    @Parcelize
    class State(
        var counterValue: Int,
        var counterTextColor: Int,
        var counterIsVisible: Boolean
    ) : Parcelable

    /**
     *     Без аннотации @Parcelize пришлось бы написать много кода:
     */
//    class State(
//        var counterValue: Int,
//        var counterTextColor: Int,
//        var counterIsVisible: Boolean
//    ) : Parcelable {
//        constructor(parcel: Parcel) : this(
//            parcel.readInt(),
//            parcel.readInt(),
//            parcel.readByte() != 0.toByte()
//        ) {
//        }
//
//        override fun writeToParcel(parcel: Parcel, flags: Int) {
//            parcel.writeInt(counterValue)
//            parcel.writeInt(counterTextColor)
//            parcel.writeByte(if (counterIsVisible) 1 else 0)
//        }
//
//        override fun describeContents(): Int {
//            return 0
//        }
//
//        companion object CREATOR : Parcelable.Creator<State> {
//            override fun createFromParcel(parcel: Parcel): State {
//                return State(parcel)
//            }
//
//            override fun newArray(size: Int): Array<State?> {
//                return arrayOfNulls(size)
//            }
//        }
//    }

    companion object {
        @JvmStatic private val KEY_STATE = "STATE"
    }
}