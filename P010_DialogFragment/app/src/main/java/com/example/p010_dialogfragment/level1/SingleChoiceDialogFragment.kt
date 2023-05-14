package com.example.p010_dialogfragment.level1

import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner

class SingleChoiceDialogFragment : DialogFragment() {

    private val volume: Int
        get() = requireArguments().getInt(ARG_VOLUME)

    companion object {
        @JvmStatic private val TAG = SingleChoiceDialogFragment::class.java.simpleName
        @JvmStatic private val KEY_VOLUME_RESPONSE = "KEY_VOLUME_RESPONSE"
        @JvmStatic private val ARG_VOLUME = "ARG_VOLUME"

        @JvmStatic val REQUEST_KEY = "$TAG:defaultRequestKey"

        fun showDialog(manager: FragmentManager, volume: Int) {
            val dialogFragment = SingleChoiceDialogFragment()
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