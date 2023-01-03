package com.practical.ioswidget.ui.common

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.practical.ioswidget.R
import com.practical.ioswidget.databinding.ItemSmallCalenderBinding
import com.practical.ioswidget.model.CalendarData

class SmallCalenderAdapter (
    private val dataSet: ArrayList<CalendarData>,
    private val onclick:()->Unit
) : RecyclerView.Adapter<SmallCalenderAdapter.ViewHolder>() {

    lateinit var context: Context
    lateinit var binding: ItemSmallCalenderBinding

    class ViewHolder(var binding: ItemSmallCalenderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        context = viewGroup.context
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_small_calender,
            viewGroup,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        var data = dataSet[position]
        if (data.isSelected){
            binding.cctop.setBackgroundColor(ContextCompat.getColor(context,R.color.black))
            binding.tvDate.setTextColor(ContextCompat.getColor(context,R.color.white))
        }
        binding.tvDate.text =dataSet[position]!!.strDate.toString()
        if (data.font != ""){
            binding.tvDate.typeface = Typeface.createFromAsset(context.assets, data.font)

        }
        if (data.textColor != 0){
            binding.tvDate.setTextColor(data.textColor)
            if (data.isSelected){
                binding.cctop.setBackgroundColor(data.textColor)
                binding.tvDate.setTextColor(ContextCompat.getColor(context,R.color.white))
            }
        }
        viewHolder.itemView.setOnClickListener {
            onclick.invoke()
        }
    }

    override fun getItemCount() = dataSet.size

}