package com.example.p008_fragmentlifecycle

import android.Manifest
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

import com.example.p008_fragmentlifecycle.databinding.FragmentRandomBinding
import com.github.javafaker.Faker
import kotlin.properties.Delegates
import kotlin.random.Random

class RandomFragment : Fragment(), HasUuid, NumberListener {

    private lateinit var binding: FragmentRandomBinding

    //    fragment's state
    private var backgroundColor by Delegates.notNull<Int>()
    private lateinit var chuckNorrisFact: String

    //    getters
    private val textColor: Int
        get() = if (Color.luminance(backgroundColor) > 0.3)
            Color.BLACK
        else
            Color.WHITE

    private var uuidArgument: String
        get() = requireArguments().getString(ARG_UUID)!!
        set(value) = requireArguments().putString(ARG_UUID, value)

//    запрос разрешения (permission) при нажатии на кнопку changeBackgroundButton (метод updateUi())
    private val permissionRequestLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
//        some callback function
}

//    *************************************************************

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getUuid()
        log("$uuidArgument: onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("$uuidArgument: onCreate")

        retainInstance = true

        //        init|restore state
        backgroundColor = savedInstanceState?.getInt(KEY_BACKGROUND_COLOR) ?: getRandomColor()
        chuckNorrisFact = savedInstanceState?.getString(KEY_CHUCK_NORRIS_FACT) ?: getNextChuckNorrisFact()
}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRandomBinding.inflate(inflater, container, false)

        setupUi() // setup button listeners
        updateUi() // render state

        log("$uuidArgument: onCreateView")

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        log("$uuidArgument: onViewCreated")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        log("$uuidArgument: onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        log("$uuidArgument: onStart")
    }

    override fun onResume() {
        super.onResume()
        log("$uuidArgument: onResume")
    }

    override fun onPause() {
        super.onPause()
        log("$uuidArgument: onPause")
    }

    override fun onStop() {
        super.onStop()
        log("$uuidArgument: onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        log("$uuidArgument: onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("$uuidArgument: onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        log("$uuidArgument: onDetach")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_CHUCK_NORRIS_FACT, chuckNorrisFact)
        outState.putInt(KEY_BACKGROUND_COLOR, backgroundColor)
        log("$uuidArgument: onSaveInstanceState")
    }

//    *************************************************************

    override fun getUuid() = uuidArgument

    override fun onNewScreenNumber(number: Int) {
        binding.numberTextView.text = getString(R.string.number, number)
    }

    private fun setupUi() {
        with (binding) {
            changeUuidButton.setOnClickListener {
                uuidArgument = navigator().generateUuid()
                navigator().update()
                updateUi()
            }
            changeBackgroundButton.setOnClickListener {
                backgroundColor = getRandomColor()
                updateUi()

                permissionRequestLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                permissionRequestLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
            changeChuckNorrisFactButton.setOnClickListener {
                chuckNorrisFact = getNextChuckNorrisFact()
                updateUi()
            }
            launchNextButton.setOnClickListener {
                navigator().launchNext()
            }
        }
    }

    private fun updateUi() {
        with (binding) {
            root.setBackgroundColor(backgroundColor)

            chuckNorrisFactTextView.text = chuckNorrisFact
            chuckNorrisFactTextView.setTextColor(textColor)

            uuidTextView.text = uuidArgument
            uuidTextView.setTextColor(textColor)

            numberTextView.setTextColor(textColor)
        }
    }

    private fun getRandomColor(): Int = -Random.nextInt(0xFFFFFF)

    private fun getNextChuckNorrisFact(): String = Faker.instance().chuckNorris().fact()

    companion object {
        private const val ARG_UUID = "ARG_UUID"

        private const val KEY_BACKGROUND_COLOR = "KEY_BACKGROUND_COLOR"
        private const val KEY_CHUCK_NORRIS_FACT = "KEY_CHUCK_NORRIS_FACT"

        @JvmStatic private val TAG = RandomFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(uuid: String) = RandomFragment().apply {
            arguments = bundleOf(ARG_UUID to uuid)
        }

        @JvmStatic
        fun log(message: String) = Log.d(TAG, message)
    }
}