package com.example.p005_listview_spinner

import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.p005_listview_spinner.databinding.ActivityArrayListviewBinding
import com.example.p005_listview_spinner.databinding.DialogAddCharacterBinding
import java.util.UUID

class ArrayAdapterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArrayListviewBinding
    private lateinit var adapter: ArrayAdapter<Character>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArrayListviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListArrayAdapter()
        binding.addButton.setOnClickListener { onAddPressed() }
    }

    private fun setupListArrayAdapter() {
        val data: MutableList<Character> = mutableListOf(
            Character(id = UUID.randomUUID().toString(), name = "Reptile"),
            Character(id = UUID.randomUUID().toString(), name = "Subzero"),
            Character(id = UUID.randomUUID().toString(), name = "Scorpion"),
            Character(id = UUID.randomUUID().toString(), name = "Raiden"),
            Character(id = UUID.randomUUID().toString(), name = "Smoke")
        )

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            data
        )

        binding.listView.adapter = adapter

        binding.listView.setOnItemClickListener { parent, view, position, id ->
            adapter.getItem(position)?.let {
                deleteCharacter(it)
            }
        }
    }

    private fun onAddPressed() {
        val dialogBinding: DialogAddCharacterBinding = DialogAddCharacterBinding.inflate(layoutInflater)
        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setTitle(R.string.create_character)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.add) { dialog, wish ->
                val name: String = dialogBinding.characterNameEditText.text.toString()
                if (name.isNotBlank()) {
                    createCharacter(name)
                }
            }
            .create()
        dialog.show()
    }

    private fun createCharacter(name: String) {
        val character = Character(
            id = UUID.randomUUID().toString(),
            name = name
        )
        adapter.add(character)
    }

    private fun deleteCharacter(item: Character) {
        val listener = DialogInterface.OnClickListener { dialog, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
                adapter.remove(item)
            }
        }
        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setTitle(R.string.delete_character)
            .setMessage(getString(R.string.message_delete, item))
            .setPositiveButton(R.string.delete, listener)
            .setNegativeButton(R.string.cancel, listener)
            .create()
        dialog.show()
    }
}