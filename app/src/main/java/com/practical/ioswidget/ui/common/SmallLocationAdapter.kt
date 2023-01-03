package com.practical.ioswidget.ui.common

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.practical.ioswidget.R
import com.practical.ioswidget.databinding.ItemLocationBinding
import com.practical.ioswidget.databinding.ItemSmallCalenderBinding
import com.practical.ioswidget.model.CalendarData

class SmallLocationAdapter (
    private val font:String,
    private val textcolor:Int,
    private val click:() -> Unit
) : RecyclerView.Adapter<SmallLocationAdapter.ViewHolder>() {

    lateinit var context: Context
    lateinit var binding: ItemLocationBinding

    class ViewHolder(var binding: ItemLocationBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        context = viewGroup.context
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_location,
            viewGroup,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        if (font != "" && font != "null" ) {
            binding.tvTime.typeface = Typeface.createFromAsset(context.assets, font)
            binding.tvTemp.typeface = Typeface.createFromAsset(context.assets, font)
        }
        if (textcolor != 0) {
            binding.tvTemp.setTextColor(textcolor)
            binding.tvTime.setTextColor(textcolor)
            binding.ivCloud.setColorFilter(textcolor, android.graphics.PorterDuff.Mode.MULTIPLY);

        }
        viewHolder.itemView.setOnClickListener {
            click.invoke()
        }

    }

    override fun getItemCount() = 6
}
