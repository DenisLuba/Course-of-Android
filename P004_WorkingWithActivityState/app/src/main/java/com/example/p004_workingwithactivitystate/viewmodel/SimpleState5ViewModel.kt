package com.example.p004_workingwithactivitystate.viewmodel

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class SimpleState5ViewModel : ViewModel() {

    /**
     *      LiveData<> - контейнер с данными, который при изменении оповещает всех слушателей
     *      с помощью метода observe()
     *
     *      MutableLiveData - ReadWrite контейнер, он private.
     *      LiveData - ReadOnly контейнер, он public.
     *      Изменять состояние мы можем только внутри ViewModel в MutableLiveData,
     *      внутри Activity мы можем только читать данные из LiveData.
     *
     *      ViewModel сохраняет состояние при повороте экрана, но не сохраняет его при уничтожении
     *      приложения системой. В отличие от Bundle.
     */
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

    data class State(
        val counterValue: Int,
        val counterTextColor: Int,
        val counterIsVisible: Boolean
    )
}