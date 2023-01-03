package com.practical.ioswidget.ui.common

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.customwidget.ui.mainActivity.WidgetListAdapter
import com.practical.ioswidget.R
import com.practical.ioswidget.databinding.ItemWidgetItemBinding
import com.practical.ioswidget.databinding.ItemWidgetListBinding
import com.practical.ioswidget.model.Widget
import com.practical.ioswidget.ui.editWidget.EditWidgetModel

class WidgetItemAdapter(
    val widgetList: ArrayList<Widget>,
   val viewModel: EditWidgetModel,
    val widgetClick: (position: Int, itempos: Int) -> Unit
) : RecyclerView.Adapter<WidgetItemAdapter.MenuHolder>() {

    lateinit var context: Context
    lateinit var binding: ItemWidgetItemBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuHolder {
        context = parent.context
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_widget_item,
            parent,
            false
        )
        return MenuHolder(binding)
    }

    class MenuHolder(var binding: ItemWidgetItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        val data = widgetList[position]

        holder.binding.tvWidgetName.text = data.typeName

        binding.rvWidgetList.adapter = WidgetListAdapter(data.widget!!,viewModel) {
            widgetClick.invoke(it,position)
        }


    }

    override fun getItemCount(): Int {
        return widgetList.size
    }


}