package com.example.p014_customviews2

import android.content.Context
import android.util.AttributeSet
import android.view.View

typealias OnCellActionListener = (row: Int, column: Int, field: TicTacToeField) -> Unit

class TicTacToeView(
    context: Context,
    attributesSet: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
    ) : View(context, attributesSet, defStyleAttr, defStyleRes) {

}