package com.example.p006_activitynavigation.activity

import android.os.Bundle
import com.example.p006_activitynavigation.databinding.ActivityAboutBinding

class AboutActivity : BaseActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater).also { setContentView(it.root) }

        with (binding) {
            versionNameTextView.text = BuildConfig.VERSION_NAME
            versionCodeTextView.text = BuildConfig.VERSION_CODE.toString()
            okButton.setOnClickListener { onOkPressed() }
        }
    }

    private fun onOkPressed() {
        finish()
    }
}