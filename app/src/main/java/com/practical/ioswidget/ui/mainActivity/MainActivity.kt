package com.app.customwidget.ui.mainActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.app.customwidget.base.BaseActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.practical.ioswidget.R
import com.practical.ioswidget.databinding.ActivityMainBinding
import com.practical.ioswidget.databinding.ViewBottomSheetWidgetCategoryBinding
import com.practical.ioswidget.model.ApplyData
import com.practical.ioswidget.model.Widget
import com.practical.ioswidget.ui.categoryWidgetList.CategoryWidgetListActivity
import com.practical.ioswidget.ui.common.WidgetItemAdapter
import com.practical.ioswidget.ui.editWidget.EditWidgetActivity
import com.practical.ioswidget.ui.editWidget.EditWidgetModel
import com.practical.ioswidget.ui.mainActivity.ViewPagerAdapter
import com.practical.ioswidget.ui.mainActivity.ViewPagerDataModel
import com.practical.ioswidget.utils.*

class MainActivity : BaseActivity(), View.OnClickListener {


    private lateinit var binding: ActivityMainBinding
    private val widgetList: ArrayList<Widget> = ArrayList()
    private var data: ApplyData? = null
    private var isEdit:Int =-1
    private val viewModel: EditWidgetModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (intent.extras != null && intent.hasExtra(ISEDIT)){
            isEdit = intent.getIntExtra(ISEDIT,0)
        }
        setupWidgetList()
        clickListener()
        redirectActivity()
    }

    private fun setupWidgetList() {
        var list : ArrayList<Widget> = ArrayList()
        list.addAll(PrefHelper.getArrayList(LIST,this))
        if (intent.extras != null && intent.extras!!.containsKey(DATALIST)){
            data = intent.getParcelableExtra<ApplyData>(DATALIST)
            Log.e("setupWidgetList", "setupWidgetList: "+data )
           if (isEdit == -1){
            if (data!!.typeWidget == 0){
                list.forEach {
                    if (it.typeName == SMALLWIDGET){
                    it.widget!!.add(ApplyData(
                        "$SMALLWIDGET # ${it.widget!!.size+1}",
                        data!!.typeWidget,
                        data!!.layoutToInflate,
                        data!!.textColor,
                        data!!.fontColor,
                        data!!.backgroundColor,
                        data!!.text,
                        data!!.uri,
                        data!!.isbackground
                    ))
                }}
            }
            else if (data!!.typeWidget == 1){
                list.forEach {
                    if (it.typeName == MEDIUMWIDGET){
                        it.widget!!.add(ApplyData(
                            "$MEDIUMWIDGET # ${it.widget!!.size+1}",
                            data!!.typeWidget,
                            data!!.layoutToInflate,
                            data!!.textColor,
                            data!!.fontColor,
                            data!!.backgroundColor,
                            data!!.text,
                            data!!.uri,
                            data!!.isbackground
                        ))
                    }}
            }
            else  if (data!!.typeWidget == 2){
                list.forEach {
                    if (it.typeName == LARGEWIDGET){
                        it.widget!!.add(ApplyData(
                            "$LARGEWIDGET # ${it.widget!!.size+1}",
                            data!!.typeWidget,
                            data!!.layoutToInflate,
                            data!!.textColor,
                            data!!.fontColor,
                            data!!.backgroundColor,
                            data!!.text,
                            data!!.uri,
                            data!!.isbackground
                        ))
                    }}
            }
           }else{
               list.forEach {
                   it.widget!!.forEachIndexed { index, applyData ->
                       if (data!!.typeWidget == applyData.typeWidget){
                           if (index == isEdit){
                               applyData.typeWidget = data!!.typeWidget
                               applyData.textColor = data!!.textColor
                               applyData.fontColor = data!!.fontColor
                               applyData.backgroundColor = data!!.backgroundColor
                               applyData.text = data!!.text
                               applyData.uri = data!!.uri
                               applyData.isbackground = data!!.isbackground
                           }
                       }
                   }
               }
           }
            PrefHelper.saveArrayList(list, LIST,this)
        }
        widgetList.addAll(PrefHelper.getArrayList(LIST,this))
        binding.rvWidgetList.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.HORIZONTAL
            )
        )
        binding.rvWidgetList.adapter = WidgetItemAdapter(widgetList,viewModel) {
            it, pos ->
            startActivity(Intent(this, EditWidgetActivity::class.java)
                .putExtra(TYPE_LAYOUT, widgetList[pos].widget!![it].layoutToInflate)
                .putExtra(ITEMLIST,widgetList[pos].widget!![it])
                .putExtra(ISEDIT,it))
        }
    }

    private fun openBottomSheet() {
        val bottomSheet = BottomSheetDialog(this, R.style.SheetDialog)
        val bottomSheetBinding = ViewBottomSheetWidgetCategoryBinding.inflate(LayoutInflater.from(this))
        bottomSheet.setContentView(bottomSheetBinding.root)
        bottomSheet.setCancelable(true)
        bottomSheet.show()

        var pagePosition = 0

        val viewPagerData : ArrayList<ViewPagerDataModel> = ArrayList()

        viewPagerData.clear()
        viewPagerData.add(ViewPagerDataModel(title = getString(R.string.small_widget),
            desc = getString(R.string.small_widget_desc),
        img = R.drawable.ic_small))
        viewPagerData.add(ViewPagerDataModel(title = getString(R.string.medium_widget),
            desc = getString(R.string.medium_widget_desc),
        img = R.drawable.ic_medium))
        viewPagerData.add(ViewPagerDataModel(title = getString(R.string.large_widget),
            desc = getString(R.string.large_widget_desc),
        img = R.drawable.ic_large))

        val viewPagerAdapter = ViewPagerAdapter(viewPagerData)
        bottomSheetBinding.introPager.adapter = viewPagerAdapter

        TabLayoutMediator(bottomSheetBinding.tabLayout, bottomSheetBinding.introPager)
        { tab, position ->}.attach()

        bottomSheetBinding.introPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                pagePosition = position

                when(position) {
                    0 -> {
                        bottomSheetBinding.tvAddWidget.text = getString(R.string.add_small_widget)
                    }
                    1 -> {
                        bottomSheetBinding.tvAddWidget.text = getString(R.string.add_medium_widget)
                    }
                    2 -> {
                        bottomSheetBinding.tvAddWidget.text = getString(R.string.add_large_widget)
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(position: Int,
                                        positionOffset: Float,
                                        positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                //Toast.makeText(this@MainActivity, position.toString(), Toast.LENGTH_SHORT).show()

            }
        })

        bottomSheetBinding.ivClose.setOnClickListener {
            bottomSheet.dismiss()
        }

        bottomSheetBinding.llAddWidget.setOnClickListener {
            startActivity(Intent(this, CategoryWidgetListActivity::class.java).putExtra(TYPE_WIDGET, pagePosition))
            //startActivity(Intent(this, EditWidgetActivity::class.java).putExtra(TYPE_WIDGET, pagePosition))
            bottomSheet.dismiss()
        }

    }

    private fun redirectActivity() {

    }

    private fun clickListener() {
        binding.fbOpenBottomSheet.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.fbOpenBottomSheet -> {
                openBottomSheet()
            }
        }
    }
}