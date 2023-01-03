package com.practical.ioswidget.ui.common

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.practical.ioswidget.R
import com.practical.ioswidget.databinding.ItemLocationBinding
import com.practical.ioswidget.databinding.ItemTempBinding
import com.practical.ioswidget.model.CalendarData

class LargeLocationAdapter(
    private val font:String,
    private val textcolor:Int,
    private val click:() -> Unit
) : RecyclerView.Adapter<LargeLocationAdapter.ViewHolder>() {

    lateinit var context: Context
    lateinit var binding: ItemTempBinding

    class ViewHolder(var binding: ItemTempBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        context = viewGroup.context
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_temp,
            viewGroup,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        if (font != "" && font != "null") {
            binding.tvDay.typeface = Typeface.createFromAsset(context.assets, font)
            binding.tvtemp1.typeface = Typeface.createFromAsset(context.assets, font)
            binding.tvtemp2.typeface = Typeface.createFromAsset(context.assets, font)
        }
        if (textcolor != 0) {
            binding.tvDay.setTextColor(textcolor)
            binding.tvtemp1.setTextColor(textcolor)
            binding.tvtemp2.setTextColor(textcolor)
            binding.ivCloud.setColorFilter(textcolor, android.graphics.PorterDuff.Mode.MULTIPLY);


        }
        viewHolder.itemView.setOnClickListener {
            click.invoke()
        }

    }

    override fun getItemCount() = 5
}
