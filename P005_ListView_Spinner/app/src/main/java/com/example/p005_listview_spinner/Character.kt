package com.example.p005_listview_spinner

class Character {

    val id: Any
    val name: String
    val isCustom: Boolean
    constructor(id: String, name: String) {
        this.id = id
        this.name = name
        isCustom = false
    }

    constructor(id: Long, name: String, isCustom: Boolean = false) {
        this.id = id
        this.name = name
        this.isCustom = isCustom
    }

    override fun toString(): String {
        return name
    }
}