package com.example.p004_workingwithactivitystate.viewmodel_parcelable

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.p004_workingwithactivitystate.R
import com.example.p004_workingwithactivitystate.databinding.ActivityMainBinding
import com.example.p004_workingwithactivitystate.viewmodel_parcelable.SimpleState6ViewModel.State

class SimpleState6Activity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<SimpleState6ViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        with (binding) {
            btnIncrement.setOnClickListener { viewModel.increment() }
            btnRandomColor.setOnClickListener { viewModel.setRandomColor() }
            btnSwitchVisibility.setOnClickListener { viewModel.switchVisibility() }
        }

        viewModel.initState(savedInstanceState?.getParcelable(KEY_STATE) ?: State(
            counterValue = 0,
            counterTextColor = ContextCompat.getColor(this, R.color.purple_700),
            counterIsVisible = true
        ))

// Добавляет данного наблюдателя в список наблюдателей в течение срока жизни данного владельца.
// События отправляются в основной поток.
// Если у LiveData уже есть набор данных, он будет доставлен наблюдателю.
        viewModel.stateLiveData.observe(this) { renderState(it) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_STATE, viewModel.stateLiveData.value)
    }

    private fun renderState(state: State) = with (binding.textViewCounter) {
        text = state.counterValue.toString()
        setTextColor(state.counterTextColor)
        visibility = if (state.counterIsVisible) View.VISIBLE else View.INVISIBLE
    }

    companion object {
        @JvmStatic val KEY_STATE = "STATE"
    }
}