package com.example.p005_listview_spinner.spinneradapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.p005_listview_spinner.Character
import com.example.p005_listview_spinner.databinding.ItemCharacterBinding

typealias OnDeletePressedListener = (Character) -> Unit

class CharacterAdapterOfSpinner(
    private val characters: List<Character>,
    private val onDeletePressedListener: OnDeletePressedListener
) : BaseAdapter(), View.OnClickListener {

    override fun getItem(position: Int) : Character {
        return characters[position]
    }

    override fun getItemId(position: Int): Long {
        return characters[position].id as Long
    }

//    если нет идентификатора id, то hasStableIds не должен возвращать true
    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getCount(): Int {
        return characters.size
    }
//        Если вью не в выпадающем списке, то isDropDown = false
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getDefaultView(position, convertView, parent, false)
    }
//        Если вью в выпадающем списке, то isDropDown = true
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getDefaultView(position, convertView, parent, true)
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

//    convertView пуст, пока при скроле списка не исчезнет вьюшка.
//    После этого освободится вьюшка, которую можно использовать для новых данных.
//    Например, пролистали Петю, он скрылся с экрана, можно использовать его вьюшку и записать
//    в нее Васю.
//    Если convertView пуст, то создаем новый биндинг, и из него достаем новую вьюшку.
//    Таким образом мы используем всегда одни и те же биндинги и одни и те же вьюшки,
//    и не создаем миллионы вью для миллионов элементов списка
    private fun getDefaultView(position: Int, convertView: View?, parent: ViewGroup, isDropDown: Boolean) : View {
        val binding: ItemCharacterBinding =
            convertView?.tag as ItemCharacterBinding? ?: // if convertView.tag != null
            createBinding(parent.context)  // if convertView.tag == null

        val character: Character = getItem(position)

        binding.titleTextView.text = character.name
        binding.deleteImageView.tag = character
        binding.deleteImageView.visibility = if (isDropDown) View.GONE else View.VISIBLE

        return binding.root
    }
}