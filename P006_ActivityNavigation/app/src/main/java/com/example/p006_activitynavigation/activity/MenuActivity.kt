package com.example.p006_activitynavigation.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.p006_activitynavigation.activity.model.Options
import com.example.p006_activitynavigation.databinding.ActivityMenuBinding
import java.lang.IllegalArgumentException

class MenuActivity : BaseActivity() {

    private lateinit var binding: ActivityMenuBinding
    private lateinit var options: Options

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater).also { setContentView(it.root)}

        with (binding) {
            openBoxButton.setOnClickListener { onOpenBoxPressed() }
            optionsButton.setOnClickListener { onOptionsPressed() }
            aboutButton.setOnClickListener { onAboutPressed() }
            exitButton.setOnClickListener { onExitPressed() }
        }

        options = savedInstanceState?.getParcelable(KEY_OPTIONS) ?: Options.DEFAULT
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPTIONS, options)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OPTIONS_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            options = data?.getParcelableExtra(OptionsActivity.EXTRA_OPTIONS) ?:
            throw IllegalArgumentException("Can't get the updated data from options activity")
        }
    }

    private fun onOpenBoxPressed() {
        val intent = Intent(this, BoxSelectionActivity::class.java)
        intent.putExtra(BoxSelectionActivity.EXTRA_OPTIONS, options)
        startActivity(intent)
    }

    private fun onOptionsPressed() {
        val intent = Intent(this, OptionsActivity::class.java)
        intent.putExtra(OptionsActivity.EXTRA_OPTIONS, options)
        startActivityForResult(intent, OPTIONS_REQUEST_CODE)
    }

    private fun onAboutPressed() {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

    private fun onExitPressed() {
        finish()
    }

    companion object {
        @JvmStatic private val KEY_OPTIONS = "OPTIONS"
        @JvmStatic private val OPTIONS_REQUEST_CODE = 1
    }
}