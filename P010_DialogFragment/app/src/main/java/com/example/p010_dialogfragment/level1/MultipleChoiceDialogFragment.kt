package com.example.p010_dialogfragment.level1

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.SparseBooleanArray
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.p010_dialogfragment.R

class MultipleChoiceDialogFragment : DialogFragment() {

    private val color: Int
        get() = requireArguments().getInt(ARG_COLOR)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val colorItems: Array<String> = resources.getStringArray(R.array.colors)
        val colorComponents: MutableList<Int> = mutableListOf(
            Color.red(this.color),
            Color.green(this.color),
            Color.blue(this.color)
        )
        val checkboxes: BooleanArray = colorComponents
            .map { it > 0 && savedInstanceState == null }
            .toBooleanArray()

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.volume_setup)
            .setMultiChoiceItems(colorItems, checkboxes) { dialog, _wich, _isChecked ->
                // NEW:
                val checkedPosition: SparseBooleanArray = (dialog as AlertDialog).listView.checkedItemPositions
                val color = Color.rgb(
                    booleanToColorComponent(checkedPosition[0]),
                    booleanToColorComponent(checkedPosition[1]),
                    booleanToColorComponent(checkedPosition[2])
                )
                parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(KEY_COLOR_RESPONSE to color))
            }
            .setPositiveButton(R.string.action_close, null)
            .create()
    }

    private fun booleanToColorComponent(value: Boolean) : Int = if (value) 255 else 0


    companion object {
        @JvmStatic private val TAG: String = MultipleChoiceDialogFragment::class.java.simpleName
        @JvmStatic private val KEY_COLOR_RESPONSE = "KEY_COLOR_RESPONSE"
        @JvmStatic private val ARG_COLOR = "ARG_COLOR"

        @JvmStatic val REQUEST_KEY = "$TAG:defaultRequestKey"

        fun showDialog(manager: FragmentManager, color: Int) {
            val dialogFragment = MultipleChoiceDialogFragment()  // создаем фрагмент-диалог
            dialogFragment.arguments = bundleOf(ARG_COLOR to color) // добавляем ему аргумент
            dialogFragment.show(manager, TAG) // показываем фрагмент-диалог
//          public void show(
//              @NonNull androidx.fragment.app.FragmentManager manager,
//              @Nullable String tag
//              )
        }

        fun setupListener(manager: FragmentManager, lifecycleOwner: LifecycleOwner, listener: (Int) -> Unit) {
            manager.setFragmentResultListener(REQUEST_KEY, lifecycleOwner, FragmentResultListener { _requestKey, result ->
                listener.invoke(result.getInt(KEY_COLOR_RESPONSE))
            })
        }
    }
}