package com.practical.ioswidget.ui.common

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.practical.ioswidget.R
import com.practical.ioswidget.databinding.ItemBackgroundListBinding
import com.practical.ioswidget.databinding.ItemWidgetListBinding

class BackgroundAdapter (
    private val dataSet: ArrayList<Int>,
    private val onClickBg : (position: Int) -> Unit,
    private val onClickPos : (position: Int) -> Unit
    ) : RecyclerView.Adapter<BackgroundAdapter.ViewHolder>() {

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
        Log.e("dataSet", "onBindViewHolder: "
        +position)
        if (position == 0){
            val colors = intArrayOf(dataSet[0],dataSet[0])

            viewHolder.binding.ivBackground.background = context.getDrawable(R.drawable.ic_baseline_add_24)
        }else{
        viewHolder.binding.ivBackground.setImageResource(dataSet[position])}
        viewHolder.itemView.setOnClickListener {
            if (position == 0){
                onClickPos.invoke(position)
            }else{
                onClickBg.invoke(position)

            }
        }

    }

    override fun getItemCount() = dataSet.size

}