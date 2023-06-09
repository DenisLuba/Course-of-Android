package com.example.p005_listview_spinner.baseadapter

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.p005_listview_spinner.Character
import com.example.p005_listview_spinner.R
import com.example.p005_listview_spinner.databinding.ActivityBaseListviewBinding
import com.example.p005_listview_spinner.databinding.DialogAddCharacterBinding
import kotlin.random.Random

class BaseAdapterActivity : AppCompatActivity() {

    private val binding: ActivityBaseListviewBinding by lazy {
        ActivityBaseListviewBinding.inflate(layoutInflater)
    }

    private val data = mutableListOf(
        Character(id = 1, name = "Reptile"),
        Character(id = 2, name = "Subzero"),
        Character(id = 3, name = "Scorpion"),
        Character(id = 4, name = "Raiden"),
        Character(id = 5, name = "Smoke")
    )

    private lateinit var adapter: CharacterAdapterOfBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupList()
        binding.addButton.setOnClickListener { onAddPressed() }
    }

    private fun setupList() {
        adapter = CharacterAdapterOfBase(data) {
            deleteCharacter(it)
        }
        binding.listView.adapter = adapter

        binding.listView.setOnItemClickListener { parent, view, position, id ->
            showCharacterInfo(adapter.getItem(position))
        }
    }

    private fun onAddPressed() {
        val dialogBinding: DialogAddCharacterBinding = DialogAddCharacterBinding.inflate(layoutInflater)
        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setTitle(R.string.create_character)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.add) { dialog, which ->
                val name: String = dialogBinding.characterNameEditText.text.toString()
                if (name.isNotBlank()) createCharacter(name)
            }
            .create()
        dialog.show()
    }

    private fun createCharacter(name: String) {
        val character = Character(
            id = Random.nextLong(),
            name = name,
            isCustom = true
        )
        data.add(character)
        adapter.notifyDataSetChanged()
    }

    private fun showCharacterInfo(character: Character) {
        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setTitle(character.name)
            .setMessage(getString(R.string.character_info, character.name, character.id))
            .setPositiveButton(R.string.ok) { _, _ -> }
            .create()
        dialog.show()
    }

    private fun deleteCharacter(character: Character) {
        val listener = DialogInterface.OnClickListener { dialog, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
                data.remove(character)
                adapter.notifyDataSetChanged()
            }
        }
        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setTitle(R.string.delete_character)
            .setMessage(getString(R.string.message_delete, character.name))
            .setPositiveButton(R.string.delete, listener)
            .setNegativeButton(R.string.cancel, listener)
            .create()
        dialog.show()
    }
}