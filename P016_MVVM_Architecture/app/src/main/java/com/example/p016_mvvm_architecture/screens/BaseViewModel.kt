package com.example.p016_mvvm_architecture.screens

import androidx.lifecycle.BaseViewModel
import com.example.p016_mvvm_architecture.tasks.Task

class Event<T>(
    private val value: T
) {
    
    private var handled: Boolean = false

    fun getValue(): T? {
        if (handled) return null
        handled = true
        return value
    }
}

open class BaseViewModel: ViewModel {

    private val tasks = mutableListOf<Task<*>>()

    override fun onCleared() {
        super.onCleared()
        tasks.forEach { it.cancel() }
    }

    fun <T> Task<T>.autoCancel() {
        tasks.add(this)
    }
}