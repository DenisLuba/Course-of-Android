package com.example.p009_alertdialog

import android.content.DialogInterface
import android.content.res.TypedArray
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.p009_alertdialog.databinding.ActivityLevel1Binding
import com.example.p009_alertdialog.entities.AvailableVolumeValues
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
                showAlertDialog()
            }
            showSingleChoiceAlertDialogButton.setOnClickListener {
                showSingleChoiceAlertDialog()
            }
            showSingleChoiceWithConfirmationAlertDialogButton.setOnClickListener {
                showSingleChoiceWithConfirmationAlertDialog()
            }
            showMultipleChoiceAlertDialogButton.setOnClickListener {
                showMultipleChoiceAlertDialog()
            }
            showMultipleChoiceWithConfirmationAlertDialogButton.setOnClickListener {
                showMultipleChoiceWithConfirmationAlertDialog()
            }
        }

        volume = savedInstanceState?.getInt(KEY_VOLUME) ?: 50
        color = savedInstanceState?.getInt(KEY_COLOR) ?: Color.RED

        updateUi()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_VOLUME, volume)
        outState.putInt(KEY_COLOR, color)
    }

//    *****************************************************************************

    private fun showAlertDialog() {
        val listener = DialogInterface.OnClickListener { _dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> showToast(R.string.uninstall_confirmed)
                DialogInterface.BUTTON_NEGATIVE -> showToast(R.string.uninstall_rejected)
                DialogInterface.BUTTON_NEUTRAL -> showToast(R.string.uninstall_ignored)
            }
        }

        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setTitle(R.string.default_alert_title)
            .setCancelable(true)
            .setIcon(R.mipmap.ic_launcher_round)
            .setMessage(R.string.default_alert_message)
            .setPositiveButton(R.string.action_yes, listener)
            .setNegativeButton(R.string.action_no, listener)
            .setNeutralButton(R.string.action_ignore, listener)
            .setOnCancelListener {
                showToast(R.string.dialog_cancelled)
            }
            .setOnDismissListener {
                Log.d(TAG, "Dialog dismissed")
            }
            .create()
        dialog.show()
    }

    private fun showToast(@StringRes messageRes: Int) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
    }

//    *****************************************************************************

    private fun showSingleChoiceAlertDialog() {
        val volumeItems: AvailableVolumeValues = AvailableVolumeValues.createVolumeValues(volume)
        val volumeTextItems: Array<String> = volumeItems.values
            .map { getString(R.string.volume_description, it) }
            .toTypedArray()

        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setTitle(R.string.volume_setup)
            .setSingleChoiceItems(volumeTextItems, volumeItems.currentIndex) { dialog, which ->
                volume = volumeItems.values[which]
                updateUi()
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }

//    *****************************************************************************

    private fun showSingleChoiceWithConfirmationAlertDialog() {
        val volumeItems: AvailableVolumeValues = AvailableVolumeValues.createVolumeValues(volume)
        val volumeTextItems: Array<String> = volumeItems.values
            .map { getString(R.string.volume_description, it) }
            .toTypedArray() // возвращает типизированный массив объектов
                            // (в данном случае - массив объектов типа String)

        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setTitle(R.string.volume_setup)
            .setSingleChoiceItems(volumeTextItems, volumeItems.currentIndex, null)
            .setPositiveButton(R.string.action_confirm) { dialog, _which ->
                val index = (dialog as AlertDialog).listView.checkedItemPosition
                volume = volumeItems.values[index]
                updateUi()
            }
            .create()
        dialog.show()
    }

//    *****************************************************************************

    private fun showMultipleChoiceAlertDialog() {
        val colorItems = resources.getStringArray(R.array.colors)
        val colorComponents: MutableList<Int> = mutableListOf(
            Color.red(this.color),
            Color.green(this.color),
            Color.blue(this.color)
        )
        val checkboxes: BooleanArray = colorComponents
            .map { it > 0 }
            .toBooleanArray()

        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setTitle(R.string.volume_setup)
            .setMultiChoiceItems(colorItems, checkboxes) { _dialog, which, isChecked ->
                colorComponents[which] = if (isChecked) 255 else 0
                this.color = Color.rgb(
                    colorComponents[0],
                    colorComponents[1],
                    colorComponents[2]
                )
                updateUi()
            }
            .setPositiveButton(R.string.action_close, null)
            .create()
        dialog.show()
    }

//    *****************************************************************************

    private fun showMultipleChoiceWithConfirmationAlertDialog() {
        val colorItems: Array<String> = resources.getStringArray(R.array.colors)
        val colorComponents: MutableList<Int> = mutableListOf(
            Color.red(this.color),
            Color.green(this.color),
            Color.blue(this.color)
        )
        val checkboxes: BooleanArray = colorComponents
            .map { it > 0 }
            .toBooleanArray()

        var color: Int = this.color
        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setTitle(R.string.volume_setup)
            .setMultiChoiceItems(colorItems, checkboxes) { _dialog, which, isChecked ->
                colorComponents[which] = if (isChecked) 255 else 0
                color = Color.rgb(
                    colorComponents[0],
                    colorComponents[1],
                    colorComponents[2]
                )
            }
            .setPositiveButton(R.string.action_confirm) { _, _ ->
                this.color = color
                updateUi()
            }
            .create()
        dialog.show()
    }

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