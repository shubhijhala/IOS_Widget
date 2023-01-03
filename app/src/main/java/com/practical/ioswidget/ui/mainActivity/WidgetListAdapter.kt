package com.app.customwidget.ui.mainActivity

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nguyenhoanglam.imagepicker.helper.GlideHelper
import com.practical.ioswidget.R
import com.practical.ioswidget.databinding.*
import com.practical.ioswidget.model.ApplyData
import com.practical.ioswidget.ui.editWidget.EditWidgetModel
import java.io.File


class WidgetListAdapter(
    val widgetList: ArrayList<ApplyData>,
    val viewModel: EditWidgetModel,
    val widgetClick: (position: Int) -> (Unit)
) :
    RecyclerView.Adapter<WidgetListAdapter.MenuHolder>() {
    private lateinit var smallmusicBinding: LayoutForMusicBinding
    private lateinit var mediumMusicLayoutBinding: MediumMusicLayoutBinding
    private lateinit var smallCalendarLayoutBinding: SmallCalendarLayoutBinding
    private lateinit var mediumCalenderLayoutBinding: MediumCalenderLayoutBinding
    private lateinit var largeCalenderLayoutBinding: LargeCalenderLayoutBinding
    private lateinit var simpleLocationLayoutBinding: SimpleLocationLayoutBinding
    private lateinit var mediumLocationLayoutBinding: MediumLocationLayoutBinding
    private lateinit var largeLocationLayoutBinding: LargeLocationLayoutBinding
    private lateinit var clock2Binding: LayoutClock2Binding
    private lateinit var mediumWatchLayoutBinding: MediumWatchLayoutBinding


    lateinit var context: Context
    lateinit var binding: ItemWidgetListBinding
    lateinit var layoutInflater: LayoutInflater
    lateinit var  text :TextView

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuHolder {
        context = parent.context
        layoutInflater =  LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_widget_list,
            parent,
            false
        )
        return MenuHolder(binding)
    }

    class MenuHolder(var binding: ItemWidgetListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
       val data = widgetList[position]
        if (data.typeWidget ==0){
            binding.tvWidgetName3.visibility = View.VISIBLE
            binding.tvWidgetName.visibility = View.GONE
        }else{
            binding.tvWidgetName3.visibility = View.GONE
            binding.tvWidgetName.visibility = View.VISIBLE

        }
        var layoutToInflate = LayoutInflater.from(context).inflate(R.layout.layout_battery, null)
        when(data.layoutToInflate){
           1 -> {
              layoutToInflate = LayoutInflater.from(context).inflate(R.layout.layout_battery, null)
               holder.binding.cvWidget.addView(layoutToInflate)
          }
            2 -> {
                layoutToInflate = LayoutInflater.from(context).inflate(R.layout.layout_time, null)
                holder.binding.cvWidget.addView(layoutToInflate)
                val text :TextView= layoutToInflate.findViewById(R.id.text1)
                text.text = viewModel.getCurrentTime().toString()
            }
            3 -> {
                layoutToInflate = LayoutInflater.from(context).inflate(R.layout.layout_date, null)
                holder.binding.cvWidget.addView(layoutToInflate)

            }
            4 -> {
                layoutToInflate = LayoutInflater.from(context).inflate(R.layout.layout_event, null)
                holder.binding.cvWidget.addView(layoutToInflate)
            }
            5 -> {
                layoutToInflate = LayoutInflater.from(context).inflate(R.layout.layout_your_text, null)
                holder.binding.cvWidget.addView(layoutToInflate)
            }
            6 -> {
                layoutToInflate = LayoutInflater.from(context).inflate(R.layout.layout_medium_time, null)
                holder.binding.cvWidget.addView(layoutToInflate)
            }
            7 -> {
                mediumCalenderLayoutBinding = MediumCalenderLayoutBinding.inflate(layoutInflater)
                binding.cvWidget.addView(mediumCalenderLayoutBinding.root)
                viewModel.setMediumCalender(data.backgroundColor.toString(),data.fontColor.toString(), data.textColor!!,mediumCalenderLayoutBinding,context,data.isbackground)

            }
            8 -> {
                layoutToInflate = LayoutInflater.from(context).inflate(R.layout.layout_large_date_time, null)
                holder.binding.cvWidget.addView(layoutToInflate)
            }
            9 -> {
                smallmusicBinding = LayoutForMusicBinding.inflate(layoutInflater)
                binding.cvWidget.addView(smallmusicBinding.root)
                viewModel.setSmallMusicData(data.backgroundColor.toString(),data.fontColor.toString(), data.textColor!!,smallmusicBinding,context,data.isbackground)

            }
            10->{
                mediumMusicLayoutBinding = MediumMusicLayoutBinding.inflate(layoutInflater)
                binding.cvWidget.addView(mediumMusicLayoutBinding.root)
                viewModel.setMediumMusicData(data.backgroundColor.toString(),data.fontColor.toString(), data.textColor!!,mediumMusicLayoutBinding,context,data.isbackground)
            }
            11->{
                smallCalendarLayoutBinding = SmallCalendarLayoutBinding.inflate(layoutInflater)
                binding.cvWidget.addView(smallCalendarLayoutBinding.root)
                viewModel.setSmallCalender(data.backgroundColor.toString(),data.fontColor.toString(), data.textColor!!,smallCalendarLayoutBinding,context,data.isbackground)
            }
            12->{
                largeCalenderLayoutBinding = LargeCalenderLayoutBinding.inflate(layoutInflater)
                binding.cvWidget.addView(largeCalenderLayoutBinding.root)
                viewModel.setLargeCalender(data.backgroundColor.toString(),data.fontColor.toString(), data.textColor!!,largeCalenderLayoutBinding,context,data.isbackground)
            }
            13->{
                simpleLocationLayoutBinding = SimpleLocationLayoutBinding.inflate(layoutInflater)
                binding.cvWidget.addView(simpleLocationLayoutBinding.root)
                viewModel.setSmallLocation(data.backgroundColor.toString(),data.fontColor.toString(), data.textColor!!,simpleLocationLayoutBinding,context,data.isbackground)

            }
            14->{
                mediumLocationLayoutBinding = MediumLocationLayoutBinding.inflate(layoutInflater)
                binding.cvWidget.addView(mediumLocationLayoutBinding.root)
                viewModel.setMediumLocation(data.backgroundColor.toString(),data.fontColor.toString(), data.textColor!!,mediumLocationLayoutBinding,context,data.isbackground)

            }
            15->{
                largeLocationLayoutBinding = LargeLocationLayoutBinding.inflate(layoutInflater)
                binding.cvWidget.addView(largeLocationLayoutBinding.root)
                viewModel.setLargeLocation(data.backgroundColor.toString(),data.fontColor.toString(), data.textColor!!,largeLocationLayoutBinding,context,data.isbackground)

            }
            16->{
                clock2Binding = LayoutClock2Binding.inflate(layoutInflater)
                binding.cvWidget.addView(clock2Binding.root)
                viewModel.setSmallClock(data.backgroundColor.toString(),data.fontColor.toString(), data.textColor!!,clock2Binding,context,data.isbackground)

            }
            19->{
                mediumWatchLayoutBinding = MediumWatchLayoutBinding.inflate(layoutInflater)
                binding.cvWidget.addView(mediumWatchLayoutBinding.root)
                viewModel.setMediumClock(data.backgroundColor.toString(),data.fontColor.toString(), data.textColor!!,mediumWatchLayoutBinding,context,data.isbackground)

            }
            17->{
                layoutToInflate = LayoutInflater.from(context).inflate(R.layout.layout_quote, null)
                holder.binding.cvWidget.addView(layoutToInflate)
            }
            18->{
                layoutToInflate = LayoutInflater.from(context).inflate(R.layout.layout_image, null)
                holder.binding.cvWidget.addView(layoutToInflate)
            }

      }
        if (data.layoutToInflate != 18){
             text = layoutToInflate.findViewById(R.id.text1)
        }

        if (data.text != null && data.text != ""){
            text.text = data.text.toString()
        }
        when (data.layoutToInflate){
            1->{
                text.text =  viewModel.getBatteryPercentage(context).toString() + "  %"
            }
            8 -> {
                val text2 :TextView= layoutToInflate.findViewById(R.id.text2)
                if (data.textColor != 0){
                    text2.setTextColor( data.textColor!!)
                }
                if (data.fontColor != "" && data.fontColor != null){
                    text2.typeface = Typeface.createFromAsset(context.assets, data.fontColor)
                }
            }
            4->{
                val text2 :TextView= layoutToInflate.findViewById(R.id.text2)
                val text3 :TextView= layoutToInflate.findViewById(R.id.text3)
                if (data.textColor != 0){
                    text2.setTextColor( data.textColor!!)
                    text3.setTextColor( data.textColor!!)
                }
                if (data.fontColor != "" && data.fontColor != null){
                    text2.typeface = Typeface.createFromAsset(context.assets, data.fontColor)
                    text3.typeface = Typeface.createFromAsset(context.assets, data.fontColor)
                }
                    text2.text =  viewModel.getCurrentDate().toString() + " "+viewModel.dayofMonth()
                     text.text = viewModel.getDayOfWeek().toString()
            }

            8->{
                val text2 :TextView= layoutToInflate.findViewById(R.id.text2)
                if (data.textColor != 0){
                    text2.setTextColor( data.textColor!!)
                }
                if (data.fontColor != "" && data.fontColor != null){
                    text2.typeface = Typeface.createFromAsset(context.assets, data.fontColor)
                }
                text2.text =  viewModel.getCurrentTime().toString()
            }
            18->{
                val ivImage :ImageView= layoutToInflate.findViewById(R.id.ivimage)
                if (data!!.uri != null && data.uri != ""){
                    val file : File = File(data.uri.toString())
                    val bmImg = BitmapFactory.decodeFile(data!!.uri)
                    ivImage.setImageBitmap(bmImg)
                }
            }

        }

        if (data.textColor != 0){
        text.setTextColor( data.textColor!!)
        }
        if (data.fontColor != "" && data.fontColor != null){
            text.typeface = Typeface.createFromAsset(context.assets, data.fontColor)
        }
        if (data.backgroundColor != null && data.backgroundColor !=""){
            val  bgWidget:LinearLayout = layoutToInflate.findViewById(R.id.cvWidgetBg)
            if (data.isbackground){
                val colors = intArrayOf(data.backgroundColor!!.toString().toInt(),data.backgroundColor!!.toString().toInt())
                //create a new gradient color
                val gd = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM, colors
                )

                gd.cornerRadius = 20f
                bgWidget.background =gd
            }else{
                bgWidget.setBackgroundResource(data.backgroundColor!!.toString().toInt())

            }
        }

        if (data.layoutToInflate == 3 || data.layoutToInflate == 8  ){
            text.text =  viewModel.getCurrentDate().toString() + " "+viewModel.dayofMonth()
        }
        if (data.layoutToInflate == 6){
            text.text =  viewModel.getCurrentTime().toString()
        }

        holder.binding.tvWidgetName.text = data.widetname
        holder.binding.tvWidgetName3.text = data.widetname


        holder.itemView.setOnClickListener {
            widgetClick.invoke(position)
        }
    }

    override fun getItemCount(): Int {
        return widgetList.size
    }
}