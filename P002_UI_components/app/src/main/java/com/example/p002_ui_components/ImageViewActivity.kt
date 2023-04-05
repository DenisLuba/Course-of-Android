package com.example.p002_ui_components

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.p002_ui_components.databinding.ImageViewActivityBinding
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import kotlin.random.Random

class ImageViewActivity : AppCompatActivity() {

    lateinit var binding: ImageViewActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ImageViewActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.imageView.setBackgroundResource(R.drawable.ic_launcher_background)
//        binding.imageView.setImageResource(R.drawable.ic_launcher_foreground)
//
//        binding.imageView.layoutParams.width = resources.getDimensionPixelSize(R.dimen.image_width)
//        binding.imageView.layoutParams.height = resources.getDimensionPixelSize(R.dimen.image_height)
//        binding.imageView.requestLayout()
//        Glide.with(this)
//            .load("https://source.unsplash.com/random/500x300")
//            .into(binding.imageView)

        binding.getRandomImageButton.setOnClickListener {
            onGetRandomImagePressed()
        }
//
        binding.getRandomImageButton.setOnLongClickListener {
            showToastWithRandomNumber()
        }

//        вызывается при нажатии на иконку поиска в правом нижнем углу клавиатуры
//        Вызывает метод onGetRandomImagePressed при нажатии на иконку поиска
        binding.etKeyWord.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                return@setOnEditorActionListener onGetRandomImagePressed()
            }
            return@setOnEditorActionListener false
        }
    }

    private fun showToastWithRandomNumber(): Boolean {
        val number = Random.nextInt(100)
        val message = getString(R.string.random_number, number)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        return true // если вернуть false, то вызовется setOnClickListener{ onGetRandomImagePressed() }
    }

    private fun onGetRandomImagePressed(): Boolean {
        val keyword = binding.etKeyWord.text.toString()
        if (keyword.isBlank()) {
            binding.etKeyWord.error = "Keyword is empty"
            return true // return true, если не хотим, чтобы клавиатура была скрыта для etKeyWord.setOnEditorActionListener{}
        }

        val encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8.name())
        Glide.with(this)
            .load("https://source.unsplash.com/random/600x400?$encodedKeyword")
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.imageView)

        return false // return false , если хотим, чтобы клавиатура была скрыта для etKeyWord.setOnEditorActionListener{}
    }
}