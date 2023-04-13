package com.example.p004_workingwithactivitystate.viewmodel

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.p004_workingwithactivitystate.R
import com.example.p004_workingwithactivitystate.viewmodel.SimpleState5ViewModel.State
import com.example.p004_workingwithactivitystate.databinding.ActivityMainBinding

class SimpleState5Activity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /**
     * Для использования ViewModel в файле build.gradle(:app) необходимо:
     *
     *     compileOptions {
     *         sourceCompatibility JavaVersion.VERSION_1_8
     *         targetCompatibility JavaVersion.VERSION_1_8
     *     }
     *     kotlinOptions {
     *         jvmTarget = '1.8'
     *     }
     *
     *     implementation 'androidx.activity:activity-ktx:1.7.0'
     */
    private val viewModel by viewModels<SimpleState5ViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        with (binding) {
            btnIncrement.setOnClickListener { viewModel.increment() }
            btnRandomColor.setOnClickListener { viewModel.setRandomColor() }
            btnSwitchVisibility.setOnClickListener { viewModel.switchVisibility() }
        }

        if (viewModel.stateLiveData.value == null) {
            viewModel.initState(
                State(
                    counterValue = 0,
                    counterTextColor = ContextCompat.getColor(this, R.color.purple_700),
                    counterIsVisible = true
                )
            )
        }
// Добавляет данного наблюдателя в список наблюдателей в течение срока жизни данного владельца.
// События отправляются в основной поток.
// Если у LiveData уже есть набор данных, он будет доставлен наблюдателю.
        viewModel.stateLiveData.observe(
            this, // Activity не будет получать обновления, когда она не в активном состоянии
            Observer { renderState(it) }
        )
    }

    private fun renderState(state: State) = with (binding.textViewCounter) {
        text = state.counterValue.toString()
        setTextColor(state.counterTextColor)
        visibility = if (state.counterIsVisible) View.VISIBLE else View.INVISIBLE
    }
}