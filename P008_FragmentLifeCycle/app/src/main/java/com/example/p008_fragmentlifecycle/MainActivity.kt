package com.example.p008_fragmentlifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import com.example.p008_fragmentlifecycle.databinding.ActivityMainBinding
import java.util.UUID

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding

    private val fragmentListener: FragmentLifecycleCallbacks = object : FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            update()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, createFragment())
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
    }

    override fun launchNext() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
//            .add(R.id.fragmentContainer, createFragment()) // фрагменты накладываются один на другой,
//            не уничтожая View, что сильно накладно по ресурсам. А также, если на первом фрагменте,
//            например, кнопка располагается где-нибудь в том месте, где у второго фрагмента нет ничего,
//            и пользователь нажмет на это место, то может сработать эта кнопка первого фрагмента,
//            хоть она и не видна из под второго фрагмента.
            .replace(R.id.fragmentContainer, createFragment())
            .commit()
    }

    override fun generateUuid() : String = UUID.randomUUID().toString()

    override fun update() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (currentFragment is HasUuid) {
            binding.currentFragmentUuidTextView.text = currentFragment.getUuid()
        } else {
            binding.currentFragmentUuidTextView.text = ""
        }

        if (currentFragment is NumberListener) {
            currentFragment.onNewScreenNumber(1 + supportFragmentManager.backStackEntryCount)
        }
    }

    private fun createFragment() : RandomFragment {
        return RandomFragment.newInstance(generateUuid())
    }
}