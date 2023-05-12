package com.example.p009_alertdialog

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.p009_alertdialog.databinding.ActivityLevel2Binding
import com.example.p009_alertdialog.databinding.PartVolumeBinding
import com.example.p009_alertdialog.databinding.PartVolumeInputBinding
import com.example.p009_alertdialog.entities.AvailableVolumeValues
import kotlin.properties.Delegates

class DialogsLevel2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityLevel2Binding
    private var volume by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLevel2Binding.inflate(layoutInflater).also { setContentView(it.root) }

        with (binding) {
            showCustomAlertDialogButton.setOnClickListener { showCustomAlertDialog() }
            showCustomSingleChoiceAlertDialogButton.setOnClickListener { showCustomSingleChoiceAlertDialog() }
            showInputAlertDialogButton.setOnClickListener { showCustomInputAlertDialog() }

            volume = savedInstanceState?.getInt(KEY_VOLUME) ?: 50
            updateUi()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_VOLUME, volume)
    }

//    ****************************************************************************************

    private fun showCustomAlertDialog() {
        val dialogBinding: PartVolumeBinding = PartVolumeBinding.inflate(layoutInflater)
        dialogBinding.volumeSeekBar.progress = volume
        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setTitle(R.string.volume_setup)
            .setMessage(R.string.volume_setup_message)
            .setView(dialogBinding.root)
            .setCancelable(true)
            .setPositiveButton(R.string.action_confirm) { _dialog: DialogInterface, which: Int ->
                volume = dialogBinding.volumeSeekBar.progress
                updateUi()
            }
            .create()
        dialog.show()
    }

    private fun showCustomSingleChoiceAlertDialog() {
        val volumeItems: AvailableVolumeValues = AvailableVolumeValues.createVolumeValues(volume)
        val adapter: VolumeAdapter = VolumeAdapter(volumeItems.values)

        var volume = this.volume
        val dialog = AlertDialog.Builder(this)
            .setTitle(R.string.volume_setup)
            .setSingleChoiceItems(adapter, volumeItems.currentIndex) { _dialog: DialogInterface, which: Int ->
                volume = adapter.getItem(which)
                Toast.makeText(this, "Selected $volume - $which", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton(R.string.action_confirm) { _dialog: DialogInterface, which: Int ->
                this.volume = volume
                updateUi()
            }
            .create()
        dialog.show()
    }

    private fun showCustomInputAlertDialog() {
        val dialogBinding: PartVolumeInputBinding = PartVolumeInputBinding.inflate(layoutInflater)
        dialogBinding.volumeInputEditText.setText(volume.toString())

        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setTitle(R.string.volume_setup)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.action_confirm, null)
            .create()
        dialog.setOnShowListener {
            dialogBinding.volumeInputEditText.requestFocus()
//            showKeyboard(dialogBinding.volumeInputEditText)

            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                val enteredText: String = dialogBinding.volumeInputEditText.text.toString()
                if (enteredText.isBlank()) {
                    dialogBinding.volumeInputEditText.error = getString(R.string.empty_value)
                    return@setOnClickListener
                }
                val volume: Int? = enteredText.toIntOrNull()
                if (volume == null || volume > 100) {
                    dialogBinding.volumeInputEditText.error = getString(R.string.invalid_value)
                    return@setOnClickListener
                }
                this.volume = volume
                updateUi()
                dialog.dismiss()
            }
        }

//        dialog.setOnDismissListener { hideKeyboard(dialogBinding.volumeInputEditText) }
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        dialog.show()
    }

//    ****************************************************************************************

    private fun updateUi() {
        binding.currentVolumeTextView.text = getString(R.string.current_volume, volume)
    }

    private fun showKeyboard(view: View) {
        view.post { // view.post(action: Runnable)
        //Добавляет Runnable в очередь сообщений.
        // Runnable будет запущен в потоке пользовательского интерфейса.
            getInputMethodManager(view).showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun hideKeyboard(view: View) {
        getInputMethodManager(view).hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun getInputMethodManager(view: View): InputMethodManager {
        val context: Context = view.context
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    companion object {
        @JvmStatic private val TAG = DialogsLevel2Activity::class.java.simpleName
        @JvmStatic private val KEY_VOLUME = "KEY_VOLUME"
    }
}