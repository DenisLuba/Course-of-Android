package com.example.p015_recyclerview

import android.app.Application
import com.example.p015_recyclerview.model.UsersService

class App : Application() {

    val usersService = UsersService()
}