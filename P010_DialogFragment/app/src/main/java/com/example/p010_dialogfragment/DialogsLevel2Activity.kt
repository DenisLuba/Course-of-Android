package com.example.p010_dialogfragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.p010_dialogfragment.databinding.ActivityLevel2Binding
import com.example.p010_dialogfragment.level2.CustomDialogFragment
import com.example.p010_dialogfragment.level2.CustomInputDialogFragment
import com.example.p010_dialogfragment.level2.CustomInputDialogListener
import com.example.p010_dialogfragment.level2.CustomSingleChoiceDialogFragment
import kotlin.properties.Delegates.notNull

class DialogsLevel2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityLevel2Binding
    private var firstVolume by notNull<Int>()
    private var secondVolume by notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLevel2Binding.inflate(layoutInflater).also { setContentView(it.root) }

        with (binding) {
            showCustomAlertDialogButton.setOnClickListener {
                showCustomDialogFragment()
            }
            showCustomSingleChoiceAlertDialogButton.setOnClickListener {
                showCustomSingleChoiceAlertDialog()
            }
            showInputAlertDialogButton.setOnClickListener {
                showCustomInputDialogFragment(KEY_FIRST_REQUEST_KEY, firstVolume)
            }
            showInputAlertDialog2Button.setOnClickListener {
                showCustomInputDialogFragment(KEY_SECOND_REQUEST_KEY, secondVolume)
            }
        }

        firstVolume = savedInstanceState?.getInt(KEY_VOLUME_FIRST) ?: 50
        secondVolume = savedInstanceState?.getInt(KEY_VOLUME_SECOND) ?: 50
        updateUi()

        setupCustomDialogFragmentListener()
        setupCustomSingleChoiceDialogFragmentListener()
        setupCustomInputDialogFragmentListener()
    }

    override fun onSupportNavigateUp() : Boolean {
        onBackPressed()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_VOLUME_FIRST, firstVolume)
        outState.putInt(KEY_VOLUME_SECOND, secondVolume)
    }

//    ------------------------------------------------------

    private fun showCustomDialogFragment() {
        CustomDialogFragment.showDialog(supportFragmentManager, firstVolume)
    }

    private fun setupCustomDialogFragmentListener() {
        CustomDialogFragment.setupListener(supportFragmentManager, this) {
            this.firstVolume = it
            updateUi()
        }
    }

//    ------------------------------------------------------

    private fun showCustomSingleChoiceAlertDialog() {
        CustomSingleChoiceDialogFragment.showDialog(supportFragmentManager, firstVolume)
    }

    private fun setupCustomSingleChoiceDialogFragmentListener() {
        CustomSingleChoiceDialogFragment.setupListener(supportFragmentManager, this) {
            this.firstVolume = it
            updateUi()
        }
    }

//    ------------------------------------------------------

    private fun showCustomInputDialogFragment(requestKey: String, volume: Int) {
        CustomInputDialogFragment.showDialog(supportFragmentManager, volume, requestKey)
    }

    private fun setupCustomInputDialogFragmentListener() {
        val listener: CustomInputDialogListener = { requestKey, volume ->
            when (requestKey) {
                KEY_FIRST_REQUEST_KEY -> this.firstVolume = volume
                KEY_SECOND_REQUEST_KEY -> this.secondVolume = volume
            }
            updateUi()
        }
        CustomInputDialogFragment.setupListener(supportFragmentManager, this, KEY_FIRST_REQUEST_KEY, listener)
        CustomInputDialogFragment.setupListener(supportFragmentManager, this, KEY_SECOND_REQUEST_KEY, listener)
    }

//    ------------------------------------------------------

    private fun updateUi() {
        binding.currentVolume1TextView.text = getString(R.string.current_volume_1, firstVolume)
        binding.currentVolume2TextView.text = getString(R.string.current_volume_2, secondVolume)
    }

    companion object {
        @JvmStatic private val KEY_VOLUME_FIRST = "KEY_VOLUME_FIRST"
        @JvmStatic private val KEY_VOLUME_SECOND = "KEY_VOLUME_SECOND"

        @JvmStatic private val KEY_FIRST_REQUEST_KEY = "KEY_VOLUME_FIRST_REQUEST_KEY"
        @JvmStatic private val KEY_SECOND_REQUEST_KEY = "KEY_SECOND_REQUEST_KEY"
    }
}