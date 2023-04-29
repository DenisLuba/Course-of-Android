package com.example.p005_listview_spinner.baseadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.p005_listview_spinner.databinding.ItemCharacterBinding

typealias OnDeletePressedListener = (Character) -> Unit

class CharacterAdapter(
    private val characters: List<Character>,
    private val onDeletePressedListener: OnDeletePressedListener
) : BaseAdapter(), View.OnClickListener {

    override fun getItem(position: Int) : Character {
        return characters[position]
    }

    override fun getItemId(position: Int): Long {
        return characters[position].id
    }

//    если нет идентификатора id, то hasStableIds не должен возвращать true
    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getCount(): Int {
        return characters.size
    }

//    convertView пуст, пока при скроле списка не исчезнет вьюшка.
//    После этого освободится вьюшка, которую можно использовать для новых данных.
//    Например, пролистали Петю, он скрылся с экрана, можно использовать его вьюшку и записать
//    в нее Васю.
//    Если convertView пуст, то создаем новый биндинг, и из него достаем новую вьюшку.
//    Таким образом мы используем всегда одни и те же биндинги и одни и те же вьюшки,
//    и не создаем миллионы вью для миллионов элементов списка
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemCharacterBinding =
            convertView?.tag as ItemCharacterBinding? ?: // if convertView.tag != null
            createBinding(parent.context)  // if convertView.tag == null

        val character: Character = getItem(position)

        binding.titleTextView.text = character.name
        binding.deleteImageView.tag = character
        binding.deleteImageView.visibility = if (character.isCustom) View.VISIBLE else View.GONE

        return binding.root
    }

    override fun onClick(view: View) {
        val character: Character = view.tag as Character
        onDeletePressedListener.invoke(character)
    }

    private fun createBinding(context: Context) : ItemCharacterBinding {
        val binding: ItemCharacterBinding = ItemCharacterBinding.inflate(LayoutInflater.from(context))
        binding.deleteImageView.setOnClickListener(this)
        binding.root.tag = binding
        return binding
    }
}