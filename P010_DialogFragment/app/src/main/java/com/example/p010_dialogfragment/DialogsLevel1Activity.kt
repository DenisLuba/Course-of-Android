package com.example.p010_dialogfragment

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentResultListener
import com.example.p010_dialogfragment.databinding.ActivityLevel1Binding
import com.example.p010_dialogfragment.level1.MultipleChoiceDialogFragment
import com.example.p010_dialogfragment.level1.MultipleChoiceWithConfirmationDialogFragment
import com.example.p010_dialogfragment.level1.SimpleDialogFragment
import com.example.p010_dialogfragment.level1.SingleChoiceDialogFragment
import com.example.p010_dialogfragment.level1.SingleChoiceWithConfirmationDialogFragment
import kotlin.properties.Delegates.notNull

class DialogsLevel1Activity : AppCompatActivity() {

    private lateinit var binding: ActivityLevel1Binding
    private var volume by notNull<Int>()
    private var color by notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLevel1Binding.inflate(layoutInflater).also { setContentView(it.root) }

        with (binding) {
            showDefaultAlertDialogButton.setOnClickListener {
                showSimpleDialogFragment()
            }
            showSingleChoiceAlertDialogButton.setOnClickListener {
                showSingleChoiceDialogFragment()
            }
            showSingleChoiceWithConfirmationAlertDialogButton.setOnClickListener {
                showSingleChoiceWithConfirmationDialogFragment()
            }
            showMultipleChoiceAlertDialogButton.setOnClickListener {
                showMultipleChoiceDialogFragment()
            }
            showMultipleChoiceWithConfirmationAlertDialogButton.setOnClickListener {
                showMultipleChoiceWithConfirmationDialogFragment()
            }
        }

        volume = savedInstanceState?.getInt(KEY_VOLUME) ?: 50
        color = savedInstanceState?.getInt(KEY_COLOR) ?: Color.RED
        updateUi()

        setupSimpleDialogFragmentListener()
        setupSingleChoiceDialogFragmentListener()
        setupSingleChoiceWithConfirmationDialogFragmentListener()
        setupMultipleChoiceDialogFragmentListener()
        setupMultipleChoiceWithConfirmationDialogFragmentListener()
    }

    override fun onSupportNavigateUp() : Boolean {
        onBackPressed()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_VOLUME, volume)
        outState.putInt(KEY_COLOR, color)
    }

//    ----------------------------------------

    private fun showSimpleDialogFragment() {
        val dialogFragment = SimpleDialogFragment()
        dialogFragment.show(supportFragmentManager, SimpleDialogFragment.TAG)
    }

    private fun setupSimpleDialogFragmentListener() {
        supportFragmentManager.setFragmentResultListener(SimpleDialogFragment.REQUEST_KEY, this, FragmentResultListener { _requestKey, result ->
            val which = result.getInt(SimpleDialogFragment.KEY_RESPONSE)
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> showToast(R.string.uninstall_confirmed)
                DialogInterface.BUTTON_NEGATIVE -> showToast(R.string.uninstall_rejected)
                DialogInterface.BUTTON_NEUTRAL -> showToast(R.string.uninstall_ignored)
            }
        })
    }

//    ----------------------------------------

    private fun showSingleChoiceDialogFragment() {
        SingleChoiceDialogFragment.showDialog(supportFragmentManager, volume)
    }

    private fun setupSingleChoiceDialogFragmentListener() {
        SingleChoiceDialogFragment.setupListener(supportFragmentManager, this) {
            this.volume = it
            updateUi()
        }
    }

//    ----------------------------------------

    private fun showSingleChoiceWithConfirmationDialogFragment() {
        SingleChoiceWithConfirmationDialogFragment.showDialog(supportFragmentManager, volume)
    }

    private fun setupSingleChoiceWithConfirmationDialogFragmentListener() {
        SingleChoiceWithConfirmationDialogFragment.setupListener(supportFragmentManager, this) {
            this.volume = it
            updateUi()
        }
    }

//    ----------------------------------------

    private fun showMultipleChoiceDialogFragment() {
        MultipleChoiceDialogFragment.showDialog(supportFragmentManager, this.color)
    }

    private fun setupMultipleChoiceDialogFragmentListener() {
        MultipleChoiceDialogFragment.setupListener(supportFragmentManager, this) {
            this.color = it
            updateUi()
        }
    }

//    ----------------------------------------

    private fun showMultipleChoiceWithConfirmationDialogFragment() {
        MultipleChoiceWithConfirmationDialogFragment.showDialog(supportFragmentManager, this.color)
    }

    private fun setupMultipleChoiceWithConfirmationDialogFragmentListener() {
        MultipleChoiceWithConfirmationDialogFragment.setupListener(supportFragmentManager, this) {
            this.color = it
            updateUi()
        }
    }

//    ----------------------------------------

    private fun updateUi() {
        binding.currentVolumeTextView.text = getString(R.string.current_volume, volume)
        binding.colorView.setBackgroundColor(color)
    }

    companion object {
        @JvmStatic private val TAG = DialogsLevel1Activity::class.java.simpleName
        @JvmStatic private val KEY_VOLUME = "KEY_VOLUME"
        @JvmStatic private val KEY_COLOR = "KEY_COLOR"
    }
}