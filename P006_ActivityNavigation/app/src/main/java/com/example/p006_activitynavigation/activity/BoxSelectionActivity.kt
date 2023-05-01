package com.example.p006_activitynavigation.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.p006_activitynavigation.R
import com.example.p006_activitynavigation.activity.model.Options
import com.example.p006_activitynavigation.databinding.ActivityBoxSelectionBinding
import com.example.p006_activitynavigation.databinding.ItemBoxBinding
import java.lang.IllegalArgumentException
import java.lang.Long.max
import kotlin.properties.Delegates

class BoxSelectionActivity : BaseActivity() {

    private lateinit var binding: ActivityBoxSelectionBinding

    private lateinit var options: Options

    private lateinit var timer: CountDownTimer

    private var timerStartTimestamp by Delegates.notNull<Long>()
    private val boxIndex by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoxSelectionBinding.inflate(layoutInflater).also { setContentView(it.root) }

        options = intent.getParcelableExtra(EXTRA_OPTIONS) ?:
                throw IllegalArgumentException("Can't launch BoxSelectionActivity without options")
        boxIndex = savedInstanceState?.getInt(KEY_INDEX) ?: Random.nextInt(options.boxCount)

        if (options.isTimerEnabled) {
            timerStartTimestamp = savedInstanceState?.getLong(KEY_START_TIMESTAMP)
                ?: System.currentTimeMillis()
            setupTimer()
            updateTimerUi()
        }

        createBoxes()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX, boxIndex)
        if (options.isTimerEnabled) {
            outState.putLong(KEY_START_TIMESTAMP, timerStartTimestamp)
        }
    }

    override fun onStart() {
        super.onStart()
        if (options.isTimerEnabled) {
            timer.start()
        }
    }

    override fun onStop() {
        super.onStop()
        if (options.isTimerEnabled) {
            timer.cancel()
        }
    }

    private fun createBoxes() {
        val boxBindings: List<ItemBoxBinding> = (0 until options.boxCount).map { index ->
            val boxBinding: ItemBoxBinding = ItemBoxBinding.inflate(layoutInflater)
            boxBinding.root.id = View.generateViewId()
            boxBinding.root.tag = index
            boxBinding.root.setOnClickListener { view -> onBoxSelected(view) }
            boxBinding.boxTitleTextView.text = "Box ${index + 1}"
            boxBinding
        }

        binding.flow.referencedIds = boxBindings.map { it.root.id }.toIntArray()
    }

    private fun onBoxSelected(view: View) {
        if (view.tag as Int == boxIndex) {
            val intent = Intent(this, BoxActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, getString(R.string.box_is_empty), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupTimer() {
        timer = object : CountDownTimer(getRemainingSeconds() * 1000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                updateTimerUi()
            }

            override fun onFinish() {
                updateTimerUi()
                showTimerEndDialog()
            }
        }
    }

    private fun updateTimerUi() {
        if (getRemainingSeconds() >= 0) {
            binding.timerTextView.visibility = View.VISIBLE
            binding.timerTextView.text = "Timer: ${getRemainingSeconds} sec."
        } else {
            binding.timerTextView.visibility = View.GONE
        }
    }

    private fun showTimerEndDialog() {
        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.the_end))
            .setMessage(getString(R.string.no_enough_time))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.ok)) { _, _ -> finish() }
            .create()
        dialog.show()
    }

    private fun getRemainingSeconds() : Long {
        val finishedAt: Long = timerStartTimestamp + TIMER_DURATION
        return max(0, (finishedAt - System.currentTimeMillis()) / 1000)
    }

    companion object {
        @JvmStatic val EXTRA_OPTIONS = "EXTRA_OPTIONS"
        @JvmStatic private val KEY_INDEX = "KEY_INDEX"
        @JvmStatic private val KEY_START_TIMESTAMP = "KEY_START_TIMESTAMP"

        @JvmStatic private val TIMER_DURATION = 10_000L
    }
}