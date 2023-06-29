package com.example.p016_mvvm_architecture

import com.example.p016_mvvm_architecture.model.User

interface Navigator {

    fun showDetails(user: User)

    fun goBack()

    fun toast(messageRes: Int)

}