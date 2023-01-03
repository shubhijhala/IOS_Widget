package com.practical.ioswidget.ui.categoryWidgetList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.practical.ioswidget.R
import com.practical.ioswidget.databinding.ActivityCategoryWidgetListBinding
import com.practical.ioswidget.ui.editWidget.EditWidgetActivity
import com.practical.ioswidget.ui.editWidget.EditWidgetModel
import com.practical.ioswidget.utils.TYPE_LAYOUT
import com.practical.ioswidget.utils.TYPE_WIDGET
import java.util.*


class CategoryWidgetListActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityCategoryWidgetListBinding
    private var widgetType = 0
    private var gridSpanList = 1
    private val viewModel: EditWidgetModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category_widget_list)

        widgetType = intent.getIntExtra(TYPE_WIDGET, 0)

        gridSpanList = if (widgetType == 0) 2 else 1

        var text = when (widgetType) {
            0 -> getString(R.string.small_widget)
            1 -> getString(R.string.medium_widget)
            else -> getString(R.string.large_widget)
        }
        binding.header.tvTitle.text = text.toString()

        when(widgetType) {
            0 -> {
                binding.llSmallWidget.visibility = View.VISIBLE
                binding.llMediumWidget.visibility = View.GONE
                binding.llLargeWidget.visibility = View.GONE
                viewModel.setSmallCalender("","",0,binding.smallcal,this,false)
                viewModel.setSmallLocation("","",0,binding.smallcal2,this,false)
                viewModel.setSmallClock("","",0,binding.smallclock,this,false)
                binding.wgtDate.text1.text = viewModel.getCurrentDate()  + " "+viewModel.dayofMonth()
                binding.wgtTime.text1.text = viewModel.getCurrentTime()
                binding.wgtEvent.text1.text = viewModel.getDayOfWeek().toString()
                binding.wgtEvent.text2.text = viewModel.getCurrentDate().toString() + " "+viewModel.dayofMonth()

            }
            1 -> {
                binding.llSmallWidget.visibility = View.GONE
                binding.llMediumWidget.visibility = View.VISIBLE
                binding.llLargeWidget.visibility = View.GONE
                viewModel.setMediumClock("","",0,binding.mediumclock,this,false)
                viewModel.setMediumCalender("","",0,binding.wgtMediumDate,this,false)
                viewModel.setMediumLocation("","",0,binding.mediumLoc,this,false)
               val text : TextView = binding.wgtMediumTime.findViewById(R.id.text1)
                text.text = viewModel.getCurrentTime()
            }
            2 -> {
                binding.llSmallWidget.visibility = View.GONE
                binding.llMediumWidget.visibility = View.GONE
                binding.llLargeWidget.visibility = View.VISIBLE
                viewModel.setLargeCalender("","",0,binding.wgtCalender,this,false)
                viewModel.setLargeLocation("","",0,binding.wgtLoc,this,false)
                var text : TextView = binding.wgtLargeDateTime.findViewById(R.id.text1)
                val text2 : TextView = binding.wgtLargeDateTime.findViewById(R.id.text2)
                text2.text = viewModel.getCurrentTime()
                text.text =  viewModel.getCurrentDate().toString() + " "+viewModel.dayofMonth()

            }
        }

        binding.header.ivBack.setOnClickListener { finish() }

        clickEvent()
    }

    private fun nextClick(position: Int) {
        startActivity(Intent(this, EditWidgetActivity::class.java)
            .putExtra(TYPE_LAYOUT, position).putExtra(TYPE_WIDGET,widgetType))

    }

    private fun clickEvent() {
        binding.wgtBattery.setOnClickListener(this)
        binding.wgtTime.cvWidgetBg.setOnClickListener {
            nextClick(2)
        }
        binding.wgtDate.cvWidgetBg.setOnClickListener {
            nextClick(3)
        }
        binding.wgtEvent.cvWidgetBg.setOnClickListener {
            nextClick(4)
        }

        binding.wgtYourText.setOnClickListener(this)
        binding.wgtMediumTime.setOnClickListener(this)
        binding.wgtMediumDate.cvWidget2.setOnClickListener(this)
        binding.wgtLargeDateTime.setOnClickListener(this)
        binding.musiclayout.cvWidgetBg.setOnClickListener(this)
        binding.mediumMusic.cvWidgetMusic.setOnClickListener(this)
        binding.smallcal.cvWidget.setOnClickListener(this)
        binding.wgtCalender.cvWidget3.setOnClickListener(this)
        binding.smallcal2.cvWidgetLoc.setOnClickListener(this)
        binding.mediumLoc.cvWidgetl1.setOnClickListener(this)
        binding.wgtLoc.cvWidget31.setOnClickListener(this)
        binding.smallclock.cvWidgetClock.setOnClickListener(this)
        binding.mediumclock.cvWidgetMClock.setOnClickListener(this)
        binding.smallquote.setOnClickListener(this)
        binding.smallPhoto.setOnClickListener(this)


    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.wgtBattery -> {
                nextClick(1)
            }

            R.id.wgtYourText -> {
                nextClick(5)
            }
            R.id.wgtMediumTime -> {
                nextClick(6)
            }
            R.id.cvWidget2 -> {
                nextClick(7)
            }
            R.id.wgtLargeDateTime -> {
                nextClick(8)
            }
            R.id.cvWidgetBg ->{
                nextClick(9)
            }
            R.id.cvWidgetMusic ->{
                nextClick(10)
            }
            R.id.cvWidget ->{
                Log.e("nextClick", "onClick: " )
                nextClick(11)
            }
            R.id.cvWidget3 ->{
                Log.e("nextClick", "onClick: " )
                nextClick(12)
            }
            R.id.cvWidgetLoc ->{
                nextClick(13)
            }
            R.id.cvWidgetl1 ->{
                nextClick(14)
            }
            R.id.cvWidget31 ->{
                nextClick(15)
            }
            R.id.cvWidgetClock ->{
                nextClick(16)
            }
            R.id.smallquote ->{
                nextClick(17)
            }
            R.id.smallPhoto ->{
                nextClick(18)
            }
            R.id.cvWidgetMClock ->{
                nextClick(19)
            }

        }

    }
}