package com.practical.ioswidget.ui.editWidget

import android.app.Application
import android.content.Context
import android.content.Context.BATTERY_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.BatteryManager
import android.os.Build
import android.util.Log
import com.practical.ioswidget.databinding.*
import com.practical.ioswidget.model.CalendarData
import com.practical.ioswidget.ui.common.BaseViewModel
import com.practical.ioswidget.ui.common.LargeLocationAdapter
import com.practical.ioswidget.ui.common.SmallCalenderAdapter
import com.practical.ioswidget.ui.common.SmallLocationAdapter
import com.practical.ioswidget.utils.PrefHelper
import com.practical.ioswidget.utils.TYPE_LAYOUT
import com.practical.ioswidget.utils.TYPE_WIDGET
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.YearMonth
import java.util.*


class EditWidgetModel  constructor(
    application: Application
) : BaseViewModel(application) {
    var calendarData : ArrayList<CalendarData> = ArrayList()

    fun getCurrentDate():String{
        val rightNow: Calendar = Calendar.getInstance()
        var strDate = java.lang.String.valueOf(rightNow.time.date)
        Log.e("getCurrentDate", "getCurrentDate: "+strDate )
        return strDate
    }
    fun getCurrentTime():String{
        val currentTime = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date())
        return currentTime

    }
    fun getDayOfWeek():String{
        val sdf = SimpleDateFormat("EEEE")
        val d = Date()
        val dayOfTheWeek = sdf.format(d)
        return dayOfTheWeek

    }
    fun setSmallMusicData(
        background: String,
        font: String,
        textColor: Int,
        binding: LayoutForMusicBinding,
        context: Context,isbackground:Boolean
    ) {
        if (textColor != 0) {
            binding.text1.setTextColor(textColor!!)
            binding.ivbavkword.setColorFilter(
                textColor!!,
                android.graphics.PorterDuff.Mode.MULTIPLY
            );
            binding.ivpause.setColorFilter(textColor, android.graphics.PorterDuff.Mode.MULTIPLY);
            binding.ivforward.setColorFilter(textColor, android.graphics.PorterDuff.Mode.MULTIPLY)
        }
        if (background != "") {
            if (isbackground){
                val colors = intArrayOf(background.toInt(),background.toInt())
                //create a new gradient color
                val gd = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM, colors
                )

                gd.cornerRadius = 20f
                binding.cvWidgetBg.background =gd
            }else{
                binding.cvWidgetBg.setBackgroundResource(background.toInt())

            }
        }
        if (font != "") {
            binding.text1.typeface = Typeface.createFromAsset(context.assets, font)
        }
    }

    fun setMediumMusicData(
        background: String,
        font: String,
        textColor: Int,
        binding: MediumMusicLayoutBinding,
        context: Context,isbackground:Boolean
    ) {
        if (textColor != 0) {
            binding.text1.setTextColor(textColor!!)
            binding.text2.setTextColor(textColor!!)
            binding.ivbavkword.setColorFilter(
                textColor!!,
                android.graphics.PorterDuff.Mode.MULTIPLY
            );
            binding.ivpause.setColorFilter(textColor, android.graphics.PorterDuff.Mode.MULTIPLY);
            binding.ivforward.setColorFilter(textColor, android.graphics.PorterDuff.Mode.MULTIPLY)
        }
        if (background != "" && background != "null") {
            if (isbackground){
                val colors = intArrayOf(background.toInt(),background.toInt())
                //create a new gradient color
                val gd = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM, colors
                )

                gd.cornerRadius = 20f
                binding.cvWidgetMusic.background =gd
            }else{
                binding.cvWidgetMusic.setBackgroundResource(background.toInt())

            }
        }
        if (font != "" && font != "null") {
            binding.text1.typeface = Typeface.createFromAsset(context.assets, font)
            binding.text2.typeface = Typeface.createFromAsset(context.assets, font)

        }
    }

    fun setSmallCalender(
        background: String,
        font: String,
        textColor: Int,
        binding: SmallCalendarLayoutBinding,
        context: Context,isbackground:Boolean
    ){
        val rightNow: Calendar = Calendar.getInstance()
        val y: String = java.lang.String.valueOf(rightNow.time.year)
        val month: String = java.lang.String.valueOf(rightNow.time.month)
        val yd: String = java.lang.String.valueOf(rightNow.get(Calendar.DAY_OF_WEEK_IN_MONTH))
        val date: String = java.lang.String.valueOf(rightNow.get(Calendar.DATE))
        val yearMonthObject: YearMonth = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            YearMonth.of(y.toInt(), month.toInt())
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val daysInMonth: Int = yearMonthObject.lengthOfMonth() //28

        Log.e("setSmallCalender", "setSmallCalender: "+month +" ,," +daysInMonth + " ,, "+date )
        binding.tvMonth.text = PrefHelper.monthData(month.toInt()).toString().toUpperCase()
        calendarData.clear()
        calendarData = PrefHelper.calenderData(daysInMonth.toInt())
        calendarData.forEach {
            it.isSelected = false
            if (it.strDate == date){
                it.isSelected = true
            }
        }
        if (textColor != 0){
            calendarData.forEach {
                it.textColor = textColor
            }
            binding.tvMonth.setTextColor(textColor)

        }
        if (font != "null" && font != ""){
            calendarData.forEach {
                it.font = font
            }
            binding.tvMonth.typeface = Typeface.createFromAsset(context.assets, font)

        }
        if (background != "") {
            if (isbackground){
                val colors = intArrayOf(background.toInt(),background.toInt())
                //create a new gradient color
                val gd = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM, colors
                )

                gd.cornerRadius = 20f
                binding.cvWidget.background =gd
            }else{
                binding.cvWidget.setBackgroundResource(background.toInt())

            }
        }
        val adapter : SmallCalenderAdapter =SmallCalenderAdapter(calendarData) {
            context.startActivity(
                Intent(context, EditWidgetActivity::class.java)
                    .putExtra(TYPE_LAYOUT, 11).putExtra(TYPE_WIDGET, 0)
            )
        }
        binding.rvCalender.adapter = adapter

    }


    fun setMediumCalender(
        background: String,
        font: String,
        textColor: Int,
        binding: MediumCalenderLayoutBinding,
        context: Context,isbackground:Boolean
    ){
        val rightNow: Calendar = Calendar.getInstance()
        val y: String = java.lang.String.valueOf(rightNow.time.year)
        val month: String = java.lang.String.valueOf(rightNow.time.month)
        val yd: String = java.lang.String.valueOf(rightNow.get(Calendar.DAY_OF_WEEK_IN_MONTH))
        val date: String = java.lang.String.valueOf(rightNow.get(Calendar.DATE))
        val yearMonthObject: YearMonth = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            YearMonth.of(y.toInt(), month.toInt())
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val daysInMonth: Int = yearMonthObject.lengthOfMonth() //28

        Log.e("setSmallCalender", "setSmallCalender: "+month +" ,," +daysInMonth + " ,, "+date )
        binding.tvMonth.text = PrefHelper.monthData(month.toInt()).toString().toUpperCase()
        binding.text1.text = date.toString()
        calendarData = PrefHelper.calenderData(daysInMonth.toInt())
        calendarData.forEach {
            it.isSelected = false
            if (it.strDate == date){
                it.isSelected = true
            }
        }
        if (textColor != 0){
            calendarData.forEach {
                it.textColor = textColor
            }
            binding.tvMonth.setTextColor(textColor)
            binding.text1.setTextColor(textColor)

        }
        if (font != "null" && font != ""){
            calendarData.forEach {
                it.font = font
            }
            binding.tvMonth.typeface = Typeface.createFromAsset(context.assets, font)
            binding.text1.typeface = Typeface.createFromAsset(context.assets, font)

        }
        if (background != "" && background !="null") {
            if (isbackground){
                val colors = intArrayOf(background.toInt(),background.toInt())
                //create a new gradient color
                val gd = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM, colors
                )

                gd.cornerRadius = 20f
                binding.cvWidget2.background =gd
            }else{
                binding.cvWidget2.setBackgroundResource(background.toInt())

            }
        }
        val adapter : SmallCalenderAdapter =SmallCalenderAdapter(calendarData) {
            context.startActivity(
                Intent(context, EditWidgetActivity::class.java)
                    .putExtra(TYPE_LAYOUT, 7).putExtra(TYPE_WIDGET, 1)
            )
        }
        binding.rvCalender.adapter = adapter

    }

    fun setLargeCalender(
        background: String,
        font: String,
        textColor: Int,
        binding: LargeCalenderLayoutBinding,
        context: Context,isbackground:Boolean


    ){
        val rightNow: Calendar = Calendar.getInstance()
        val df: DateFormat = SimpleDateFormat("h:mm a")
        val time: String = df.format(Calendar.getInstance().time)
        val y: String = java.lang.String.valueOf(rightNow.time.year)
        val month: String = java.lang.String.valueOf(rightNow.time.month)
        val yd: String = java.lang.String.valueOf(rightNow.get(Calendar.DAY_OF_WEEK_IN_MONTH))
        val date: String = java.lang.String.valueOf(rightNow.get(Calendar.DATE))
        val yearMonthObject: YearMonth = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            YearMonth.of(y.toInt(), month.toInt())
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val daysInMonth: Int = yearMonthObject.lengthOfMonth() //28

        Log.e("setSmallCalender", "setSmallCalender: "+month +" ,," +daysInMonth + " ,, "+date )
        binding.tvMonth.text = PrefHelper.monthData(month.toInt()).toString().toUpperCase()
        binding.tvTime.text = time.toString()
        calendarData = PrefHelper.calenderData(daysInMonth.toInt())
        calendarData.forEach {
            it.isSelected = false
            if (it.strDate == date){
                it.isSelected = true
            }
        }
        if (textColor != 0){
            calendarData.forEach {
                it.textColor = textColor
            }
            binding.tvMonth.setTextColor(textColor)
            binding.tvTime.setTextColor(textColor)

        }
        if (font != "null" && font != ""){
            calendarData.forEach {
                it.font = font
            }
            binding.tvMonth.typeface = Typeface.createFromAsset(context.assets, font)
            binding.tvTime.typeface = Typeface.createFromAsset(context.assets, font)

        }
        if (background != "" && background !="null") {
            if (isbackground){
                val colors = intArrayOf(background.toInt(),background.toInt())
                //create a new gradient color
                val gd = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM, colors
                )

                gd.cornerRadius = 20f
                binding.cvWidget3.background =gd
            }else{
                binding.cvWidget3.setBackgroundResource(background.toInt())

            }
        }
        val adapter : SmallCalenderAdapter =SmallCalenderAdapter(calendarData) {
            context.startActivity(
                Intent(context, EditWidgetActivity::class.java)
                    .putExtra(TYPE_LAYOUT, 12).putExtra(TYPE_WIDGET, 2)
            )
        }
        binding.rvCalender.adapter = adapter

    }

    fun setSmallLocation( background: String,
                          font: String,
                          textColor: Int,
                          binding: SimpleLocationLayoutBinding,
                          context: Context,isbackground:Boolean){

        if (font != "") {
            binding.text2.typeface = Typeface.createFromAsset(context.assets, font)
            binding.text1.typeface = Typeface.createFromAsset(context.assets, font)
            binding.text3.typeface = Typeface.createFromAsset(context.assets, font)
        }
        if (textColor != 0) {
            binding.text2.setTextColor(textColor)
            binding.text1.setTextColor(textColor)
            binding.text3.setTextColor(textColor)
            binding.ivCloud.setColorFilter(textColor, android.graphics.PorterDuff.Mode.MULTIPLY);

        }

        if (background != "" && background !="null") {
            if (isbackground){
                val colors = intArrayOf(background.toInt(),background.toInt())
                //create a new gradient color
                val gd = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM, colors
                )

                gd.cornerRadius = 20f
                binding.cvWidgetLoc.background =gd
            }else{
                binding.cvWidgetLoc.setBackgroundResource(background.toInt())

            }        }


    }

    fun setMediumLocation( background: String,
                           font: String,
                           textColor: Int,
                           binding: MediumLocationLayoutBinding,
                           context: Context,isbackground:Boolean){

        if (font != "") {
            binding.tv1.typeface = Typeface.createFromAsset(context.assets, font)
            binding.tv2.typeface = Typeface.createFromAsset(context.assets, font)
            binding.tvcloudy.typeface = Typeface.createFromAsset(context.assets, font)
        }
        if (textColor != 0) {
            binding.tv1.setTextColor(textColor)
            binding.tv2.setTextColor(textColor)
            binding.tvcloudy.setTextColor(textColor)
            binding.ivCloud.setColorFilter(textColor, android.graphics.PorterDuff.Mode.MULTIPLY);

        }
        if (background != "" && background !="null") {
            if (isbackground){
                val colors = intArrayOf(background.toInt(),background.toInt())
                //create a new gradient color
                val gd = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM, colors
                )

                gd.cornerRadius = 20f
                binding.cvWidgetl1.background =gd
            }else{
                binding.cvWidgetl1.setBackgroundResource(background.toInt())

            }        }

        val adapter : SmallLocationAdapter =SmallLocationAdapter(font,textColor) {

            context.startActivity(
                Intent(context, EditWidgetActivity::class.java)
                    .putExtra(TYPE_LAYOUT, 14).putExtra(TYPE_WIDGET, 1)
            )
        }
        binding.rvloc.adapter = adapter

    }

    fun setLargeLocation( background: String,
                          font: String,
                          textColor: Int,
                          binding: LargeLocationLayoutBinding,
                          context: Context,isbackground:Boolean){
        if (font != "null" && font != "" ) {
            binding.tv1.typeface = Typeface.createFromAsset(context.assets, font)
            binding.tv2.typeface = Typeface.createFromAsset(context.assets, font)
            binding.tvcloudy.typeface = Typeface.createFromAsset(context.assets, font)
        }
        if (textColor != 0) {
            binding.tv1.setTextColor(textColor)
            binding.tv2.setTextColor(textColor)
            binding.tvcloudy.setTextColor(textColor)
            binding.ivCloud.setColorFilter(textColor, android.graphics.PorterDuff.Mode.MULTIPLY);

        }
        if (background != "" && background !="null") {
            if (isbackground){
                val colors = intArrayOf(background.toInt(),background.toInt())
                //create a new gradient color
                val gd = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM, colors
                )

                gd.cornerRadius = 20f
                binding.cvWidget31.background =gd
            }else{
                binding.cvWidget31.setBackgroundResource(background.toInt())

            }        }

        val adapter : SmallLocationAdapter =SmallLocationAdapter(font,textColor) {
            context.startActivity(
                Intent(context, EditWidgetActivity::class.java)
                    .putExtra(TYPE_LAYOUT, 15).putExtra(TYPE_WIDGET, 2)
            )
        }
        binding.rvTemp.adapter = adapter

        val adapter2 : LargeLocationAdapter = LargeLocationAdapter(font,textColor) {
            context.startActivity(
                Intent(context, EditWidgetActivity::class.java)
                    .putExtra(TYPE_LAYOUT, 15).putExtra(TYPE_WIDGET, 2)
            )
        }
        binding.rvloc.adapter = adapter2
    }

    fun setSmallClock( background: String,
                       font: String,
                       textColor: Int,
                       binding: LayoutClock2Binding,
                       context: Context,isbackground:Boolean){

        if (font != "null" && font != "" ) {
            if (font == "")
                binding.analogClock.setValuesFont(font)
        }
        if (textColor != 0) {
            binding.analogClock.setValuesColor(textColor)
            binding.analogClock.setHoursNeedleColor(textColor)
            binding.analogClock.setMinutesNeedleColor(textColor)
            binding.analogClock.setSecondsNeedleColor(textColor)

        }
        if (background != "" && background !="null") {
            if (isbackground){
                val colors = intArrayOf(background.toInt(),background.toInt())
                //create a new gradient color
                val gd = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM, colors
                )

                gd.cornerRadius = 20f
                binding.cvWidgetClock.background =gd
            }else{
                binding.cvWidgetClock.setBackgroundResource(background.toInt())

            }        }
    }

    fun setMediumClock( background: String,
                        font: String,
                        textColor: Int,
                        binding: MediumWatchLayoutBinding,
                        context: Context,isbackground:Boolean){


        if (font != "null" && font != "" ) {
            if (font == "")
                binding.analogClock.setValuesFont(font)
        }
        if (textColor != 0) {
            binding.analogClock.setValuesColor(textColor)
            binding.analogClock.setHoursNeedleColor(textColor)
            binding.analogClock.setMinutesNeedleColor(textColor)
            binding.analogClock.setSecondsNeedleColor(textColor)

        }
        if (background != "" && background !="null") {
            if (isbackground){
                val colors = intArrayOf(background.toInt(),background.toInt())
                //create a new gradient color
                val gd = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM, colors
                )

                gd.cornerRadius = 20f
                binding.cvWidgetMClock.background =gd
            }else{
                binding.cvWidgetMClock.setBackgroundResource(background.toInt())

            }        }
    }

    fun  dayofMonth():String{
        val rightNow: Calendar = Calendar.getInstance()
        val y: String = java.lang.String.valueOf(rightNow.time.year)
        val month: String = java.lang.String.valueOf(rightNow.time.month)

        return  PrefHelper.monthData2(month.toInt()).toString().toUpperCase()
    }
    fun getBatteryPercentage(context: Context): Int {
        return if (Build.VERSION.SDK_INT >= 21) {
            val bm: BatteryManager = context.getSystemService(BATTERY_SERVICE) as BatteryManager
            bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        } else {
            val iFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            val batteryStatus = context.registerReceiver(null, iFilter)
            val level = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
            val scale = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
            val batteryPct = level / scale.toDouble()
            (batteryPct * 100).toInt()
        }
    }

}