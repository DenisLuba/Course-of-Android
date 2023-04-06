package com.example.p002_ui_components

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.p002_ui_components.databinding.ActivityImageButtonCheckEditBinding
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import kotlin.random.Random

class ImageButtonCheckEditActivity : AppCompatActivity() {

    lateinit var binding: ActivityImageButtonCheckEditBinding

    private var useKeyword: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageButtonCheckEditBinding.inflate(layoutInflater)
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

        binding.button.setOnClickListener {
            onClick()
        }
//
        binding.button.setOnLongClickListener {
            onLongClick()
        }

//        вызывается при нажатии на иконку поиска в правом нижнем углу клавиатуры
//        Вызывает метод onGetRandomImagePressed при нажатии на иконку поиска
        binding.editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                return@setOnEditorActionListener onClick()
            }
            return@setOnEditorActionListener false
        }

//        срабатывает, когда лично пользователь нажимает checkbox,
//        но не срабатывает, когда checkbox изменен программно
        binding.checkbox.setOnClickListener {
            this.useKeyword = binding.checkbox.isChecked
            updateUi()
        }

//        срабатывает, когда лично пользователь нажимает checkbox или
//        когда checkbox изменен программно
        binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            // todo
        }

        updateUi()
    }

//
    private fun onLongClick(): Boolean {
        val number = Random.nextInt(100)
        val message = getString(R.string.random_number, number)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        return true // если вернуть false, то вызовется setOnClickListener{ onClick() }
    }

    private fun onClick(): Boolean {
        val keyword = binding.editText.text.toString()
        if (keyword.isBlank() && useKeyword) {
            binding.editText.error = "Keyword is empty"
            return true // return true, если не хотим, чтобы клавиатура была скрыта для etKeyWord.setOnEditorActionListener{}
        }

        val encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8.name())
        Glide.with(this)
            .load("https://source.unsplash.com/random/600x400?${ if (useKeyword) encodedKeyword else ""}")
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.imageView)

        return false // return false , если хотим, чтобы клавиатура была скрыта для etKeyWord.setOnEditorActionListener{}
    }

    private fun updateUi() = with(binding) {
        checkbox.isChecked = useKeyword
        if (useKeyword) {
            editText.visibility = View.VISIBLE
        } else {
            editText.visibility = View.GONE
        }
    }
}