package com.example.p009_alertdialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.p009_alertdialog.databinding.ItemVolumeSingleChoiceBinding

class VolumeAdapter(
    private val values: List<Int>
) : BaseAdapter() {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val context = parent.context
        val binding = view?.tag as ItemVolumeSingleChoiceBinding? ?:
            ItemVolumeSingleChoiceBinding.inflate(LayoutInflater.from(context)).also {
                it.root.tag = it
            }

        val volume = getItem(position)

        binding.volumeValueTextView.text = context.getString(R.string.volume_description, volume)
        binding.volumeValueProgressBar.progress = volume

        return binding.root
    }

    override fun getCount(): Int = values.size

    override fun getItem(positon: Int): Int = values[positon]

    override fun getItemId(positon: Int): Long = positon.toLong()

    override fun hasStableIds(): Boolean = true
}