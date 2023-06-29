package com.example.p016_mvvm_architecture

import android.app.Application
import com.example.p016_mvvm_architecture.model.UsersService

class App : Application() {

    val usersService = UsersService()
}