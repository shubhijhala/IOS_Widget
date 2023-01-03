package com.practical.ioswidget.ui.common

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.practical.ioswidget.R
import com.practical.ioswidget.databinding.ItemBackgroundListBinding
import com.practical.ioswidget.databinding.ItemFontListBinding


class FontAdapter (
    private val dataSet: ArrayList<String>,
    private val onFontClick: (position: Int) -> Unit
    ) : RecyclerView.Adapter<FontAdapter.ViewHolder>() {

    lateinit var context: Context
    lateinit var binding: ItemFontListBinding

    class ViewHolder(var binding: ItemFontListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        context = viewGroup.context
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_font_list,
            viewGroup,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val tf = Typeface.createFromAsset(context.assets, dataSet[position])
        viewHolder.binding.tvFontStyle.typeface = tf
        viewHolder.itemView.setOnClickListener {
            onFontClick.invoke(position)
        }
    }

    override fun getItemCount() = dataSet.size

}