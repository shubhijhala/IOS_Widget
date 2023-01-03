package com.practical.ioswidget.ui.mainActivity

import android.annotation.SuppressLint
import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.practical.ioswidget.R
import com.practical.ioswidget.databinding.ViewViewPagerBinding

class ViewPagerAdapter(
    private val dataList: ArrayList<ViewPagerDataModel>): RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    lateinit var context: Context
    lateinit var binding: ViewViewPagerBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.view_view_pager,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.binding.tvTitle.text = dataList[position].title
        holder.binding.tvDesc.text = dataList[position].desc
        holder.binding.image.setImageResource(dataList[position].img)


    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolder(var binding: ViewViewPagerBinding) : RecyclerView.ViewHolder(binding.root)
}