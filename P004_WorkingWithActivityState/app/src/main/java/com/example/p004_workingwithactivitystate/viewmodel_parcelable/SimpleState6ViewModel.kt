package com.example.p004_workingwithactivitystate.viewmodel_parcelable

import android.graphics.Color
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

class SimpleState6ViewModel : ViewModel() {

    val stateLiveData: LiveData<State> get() = stateMutableLiveData
    private val stateMutableLiveData = MutableLiveData<State>()

    fun initState(state: State) {
        stateMutableLiveData.value = state
    }

    fun increment() {
        val oldState = stateMutableLiveData.value
        stateMutableLiveData.value = oldState?.copy(
            counterValue = oldState.counterValue + 1
        )
    }

    fun switchVisibility() {
        val oldState = stateMutableLiveData.value
        stateMutableLiveData.value = oldState?.copy(
            counterIsVisible = !oldState.counterIsVisible
        )
    }

    fun setRandomColor() {
        val oldState = stateMutableLiveData.value
        stateMutableLiveData.value = oldState?.copy(
            counterTextColor = Color.rgb(
                Random.nextInt(256), // red
                Random.nextInt(256), // green
                Random.nextInt(256) // blue
            )
        )
    }

    @Parcelize
    data class State(
        val counterValue: Int,
        val counterTextColor: Int,
        val counterIsVisible: Boolean
    ) : Parcelable
}