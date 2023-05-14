package com.example.p010_dialogfragment.level1

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.p010_dialogfragment.R
import com.example.p010_dialogfragment.entities.AvailableVolumeValues

class SingleChoiceWithConfirmationDialogFragment : DialogFragment() {

    private val volume: Int
        get() = requireArguments().getInt(ARG_VOLUME)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val volumeItems: AvailableVolumeValues = AvailableVolumeValues.createVolumeValues(volume)
        val volumeTextItems: Array<String> = volumeItems.values
            .map { getString(R.string.volume_description, it) }
            .toTypedArray()

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.volume_setup)
            .setSingleChoiceItems(volumeTextItems, volumeItems.currentIndex, null)
            .setPositiveButton(R.string.action_confirm) { dialog, _wich ->
                val intex = (dialog as AlertDialog).listView.checkedItemPosition
                val volume = volumeItems.values[intex]
                parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(KEY_VOLUME_RESPONSE to volume))
            }
            .create()
    }

    companion object {
        @JvmStatic private val TAG = SingleChoiceWithConfirmationDialogFragment::class.java.simpleName
        @JvmStatic private val KEY_VOLUME_RESPONSE = "KEY_VOLUME_RESPONSE"
        @JvmStatic private val ARG_VOLUME = "ARG_VOLUME"

        @JvmStatic val REQUEST_KEY = "$TAG:defaultRequestKey"

        fun showDialog(manager: FragmentManager, volume: Int) {
            val dialogFragment = SingleChoiceWithConfirmationDialogFragment()
            dialogFragment.arguments = bundleOf(ARG_VOLUME to volume)
            dialogFragment.show(manager, TAG)
        }

        fun setupListener(manager: FragmentManager, lifecycleOwner: LifecycleOwner, listener: (Int) -> Unit) {
            manager.setFragmentResultListener(REQUEST_KEY, lifecycleOwner, FragmentResultListener { _requestKey, result ->
                listener.invoke(result.getInt(KEY_VOLUME_RESPONSE))
            })
        }
    }
}