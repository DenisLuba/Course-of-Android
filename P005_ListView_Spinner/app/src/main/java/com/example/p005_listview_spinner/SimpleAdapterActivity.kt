package com.example.p005_listview_spinner

import android.os.Bundle
import android.widget.AdapterView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.p005_listview_spinner.databinding.ActivitySimpleListviewBinding

/**
 *     SimpleAdapter показывает только:
 *     текст,
 *     Checkable views (CheckBox, Switch, ...),
 *     изображения из локальных файлов по URL
 */

class SimpleAdapterActivity : AppCompatActivity() {

    private val binding: ActivitySimpleListviewBinding by lazy {
        ActivitySimpleListviewBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        setupListViewSimple()
        setupListViewWithSimpleGeneratedData()
    }

    private fun setupListViewSimple() {

        val data: List<Map<String, String>> = listOf(
            mapOf(
                KEY_TITLE to "First title 111",
                KEY_DESCRIPTION to "The first some description"
            ),
            mapOf(
                KEY_TITLE to "Second title 222",
                KEY_DESCRIPTION to "The second some description"
            ),
            mapOf(
                KEY_TITLE to "Third title 333",
                KEY_DESCRIPTION to "The third some description"
            )
        )

        val adapter = SimpleAdapter(
            this,
            data,
            android.R.layout.simple_list_item_2,
            arrayOf(KEY_TITLE, KEY_DESCRIPTION),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )

        binding.listView.adapter = adapter

        binding.listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedItemTitle: String? = data[position][KEY_TITLE]
            val selectedItemDescription: String? = data[position][KEY_DESCRIPTION]

            val dialog: AlertDialog = AlertDialog.Builder(this)
                .setTitle(selectedItemTitle)
                .setMessage(getString(R.string.item_selected_message, selectedItemDescription))
                .setPositiveButton("OK") { dialog, wich -> }
                .create()
            dialog.show()
        }
    }

    private fun setupListViewWithSimpleGeneratedData() {
        val data: List<Map<String, String>> = (1..100).map {
            mapOf(
                KEY_TITLE to "Item #$it",
                KEY_DESCRIPTION to "Description #$it"
            )
        }

//        // +---------------------------------------------------------+
//        // | KEY_TITLE = item 1, KEY_DESCRIPTION = description 1     |
//        // +---------------------------------------------------------+
//        // | KEY_TITLE = item 2, KEY_DESCRIPTION = description 2     |
//        // +---------------------------------------------------------+
//        // | KEY_TITLE = item 3, KEY_DESCRIPTION = description 3     |
//        // +---------------------------------------------------------+
//        //                   ....
//        // +---------------------------------------------------------+
//        // | KEY_TITLE = item 100, KEY_DESCRIPTION = description 100 |
//        // +---------------------------------------------------------+

        val adapter = SimpleAdapter(
            this,
            data,
            R.layout.item_custom,
            arrayOf(KEY_TITLE, KEY_DESCRIPTION),
            intArrayOf(R.id.titleTextView, R.id.descriptionTextView)
        )

        binding.listView.adapter = adapter
    }

    companion object {
        @JvmStatic val KEY_TITLE = "KEY TITLE"
        @JvmStatic val KEY_DESCRIPTION = "KEY DESCRIPTION"
    }
}