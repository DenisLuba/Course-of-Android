package com.example.p002_ui_components

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.p002_ui_components.databinding.ActivityRadioButtonProgressBarBinding
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class RadioButtonProgressBarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRadioButtonProgressBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRadioButtonProgressBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener { onGetRandomImagePressed() }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            onGetRandomImagePressed()
        }
    }

    private fun onGetRandomImagePressed() : Boolean {
        val checkedId = binding.radioGroup.checkedRadioButtonId
        val keyword = binding.radioGroup.findViewById<RadioButton>(checkedId).text.toString()
        val encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8.name())
        binding.progressBar.visibility = View.VISIBLE

        Glide.with(this)
            .load("https://source.unsplash.com/random/800x600$?$encodedKeyword")
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
//            .placeholder(R.drawable.ic_launcher_background)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed( e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean ): Boolean {
                    binding.progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady( resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean ): Boolean {
                    binding.progressBar.visibility = View.GONE
                    return false
                }
            })
            .into(binding.imageView)

        return false
    }
}