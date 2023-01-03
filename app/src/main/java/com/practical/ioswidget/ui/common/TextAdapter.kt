package com.practical.ioswidget.ui.common

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.practical.ioswidget.R
import com.practical.ioswidget.databinding.ItemBackgroundListBinding
import com.practical.ioswidget.databinding.ItemWidgetListBinding

class TextAdapter (
    private val dataSet: ArrayList<Int>,
    private val onTextColorClick: (position: Int) -> Unit
    ) : RecyclerView.Adapter<TextAdapter.ViewHolder>() {

    lateinit var context: Context
    lateinit var binding: ItemBackgroundListBinding

    class ViewHolder(var binding: ItemBackgroundListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        context = viewGroup.context
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_background_list,
            viewGroup,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.ivBackground.setBackgroundColor(dataSet[position])
        viewHolder.itemView.setOnClickListener {
            onTextColorClick.invoke(position)
        }
    }

    override fun getItemCount() = dataSet.size

}