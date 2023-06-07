package com.example.p014_customviews2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.p014_customviews2.databinding.ActivityMainBinding
import kotlin.properties.Delegates

import com.example.p014_customviews2.TicTacToeField.Memento
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isFirstPlayer by Delegates.notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setup UI
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        // Restore or init field and variables
        val field = savedInstanceState?.getParcelable<Memento>(KEY_FIELD)?.restoreField() ?:
        TicTacToeField(10, 10)
        binding.ticTacToeField.field = field
        isFirstPlayer = savedInstanceState?.getBoolean(KEY_IS_FIRST_PLAYER, true) ?: true

        //listening user actions
        binding.ticTacToeField.actionListener = { row, column, currentField ->
            // user has pressed/chosen cell[row, column]

            // get current cell value
            val cell = currentField.getCell(row, column)
            if (cell == Cell.EMPTY) {
                // cell is empty, changing it to X or O
                if (isFirstPlayer) {
                    currentField.setCell(row, column, Cell.PLAYER_1)
                } else {
                    currentField.setCell(row, column, Cell.PLAYER_2)
                }
                isFirstPlayer = !isFirstPlayer
            }
        }

        binding.randomFieldButton.setOnClickListener {
            // generate random empty field
            binding.ticTacToeField.field = TicTacToeField(
                Random.nextInt(3, 10),
                Random.nextInt(3, 10)
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val field = binding.ticTacToeField.field
        outState.putParcelable(KEY_FIELD, field!!.saveState())
        outState.putBoolean(KEY_IS_FIRST_PLAYER, isFirstPlayer)
    }

    companion object {
        private const val KEY_FIELD = "KEY_FIELD"
        private const val KEY_IS_FIRST_PLAYER = "KEY_IS_FIRST_PLAYER"
    }
}