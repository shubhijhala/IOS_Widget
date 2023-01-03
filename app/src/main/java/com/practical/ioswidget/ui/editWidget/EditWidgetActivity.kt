package com.practical.ioswidget.ui.editWidget

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.*
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableInt
import com.app.customwidget.base.BaseActivity
import com.app.customwidget.ui.mainActivity.MainActivity
import com.github.antonpopoff.colorwheel.gradientseekbar.setBlackToColor
import com.github.antonpopoff.colorwheel.gradientseekbar.setTransparentToColor
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.material.snackbar.Snackbar
import com.nguyenhoanglam.imagepicker.helper.GlideHelper
import com.practical.ioswidget.R
import com.practical.ioswidget.databinding.*
import com.practical.ioswidget.model.ApplyData
import com.practical.ioswidget.ui.common.BackgroundAdapter
import com.practical.ioswidget.ui.common.FontAdapter
import com.practical.ioswidget.ui.common.TextAdapter
import com.practical.ioswidget.utils.*
import java.io.File


class EditWidgetActivity : BaseActivity() {
    private val PROFILE_IMAGE_REQ_CODE = 101

    private lateinit var binding: ActivityEditWidgetBinding
    private lateinit var smallmusicBinding: LayoutForMusicBinding
    private lateinit var mediummusicBinding: MediumMusicLayoutBinding
    private lateinit var mediumCalenderBinding: MediumCalenderLayoutBinding
    private lateinit var smallCalBinding: SmallCalendarLayoutBinding
    private lateinit var largeCalenderLayoutBinding: LargeCalenderLayoutBinding
    private lateinit var simpleLocationLayoutBinding: SimpleLocationLayoutBinding
    private lateinit var mediumLocationLayoutBinding: MediumLocationLayoutBinding
    private lateinit var largeLocationLayoutBinding: LargeLocationLayoutBinding
    private lateinit var clock2Binding: LayoutClock2Binding
    private lateinit var mediumWatchLayoutBinding: MediumWatchLayoutBinding
    private var uri :String? = null
    private val viewModel: EditWidgetModel by viewModels()
    val indicatorColor = ObservableInt()

    private var typeWidget: Int = 0
    private val backgroundList: ArrayList<Int> = ArrayList()
    private val textColorList: ArrayList<Int> = ArrayList()
    private val fontStyleList: ArrayList<String> = ArrayList()

    private lateinit var text: TextView
    private lateinit var taptoEdit: TextView
    private lateinit var text2: TextView
    private lateinit var text4: CalendarView
    private lateinit var text3: TextView
    private lateinit var ivImage: ImageView
    private lateinit var bgWidget: LinearLayout
    private var fontData :String = ""
    private var backData :String =""
    private var isBackground : Boolean = false
    private var textColor :Int = 0
    private var isEdit :Int = -1

    private var data: ApplyData? = null
    private var layoutPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_widget)

        if (intent.hasExtra(TYPE_WIDGET)) {
            typeWidget = intent.getIntExtra(TYPE_WIDGET, 0) // 0-small, 1-medium, 2-large
        }
        if (intent.hasExtra(ISEDIT)) {
            isEdit = intent.getIntExtra(ISEDIT, 0)
        }

        if (intent.hasExtra(TYPE_LAYOUT)) {
            layoutPosition = intent.getIntExtra(TYPE_LAYOUT, 0)
        }


        setHeader()
        setBackgroundRecyclerView()
        seTextRecyclerView()
        setFontRecyclerView()
        setRadioClickEvent()
        addView()
        if (intent.hasExtra(ITEMLIST)) {
            data = intent.getParcelableExtra<ApplyData>(ITEMLIST)
            typeWidget = data!!.typeWidget!!
            setHeader()
            if (data!!.backgroundColor != null && data!!.backgroundColor != "") {
                backData = data!!.backgroundColor.toString()
                if (data!!.isbackground){
                    val colors = intArrayOf(data!!.backgroundColor!!.toString().toInt(),
                        data!!.backgroundColor!!.toString().toInt())
                    //create a new gradient color
                    val gd = GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM, colors
                    )

                    gd.cornerRadius = 20f
                    bgWidget.background =gd
                }else{
                bgWidget.setBackgroundResource(data!!.backgroundColor.toString().toInt())}
            }
            if (data!!.textColor != 0 && layoutPosition != 18) {
                textColor = data!!.textColor!!.toInt()
                text.setTextColor(data!!.textColor!!)
            }
            if (data!!.text != null  && layoutPosition != 18) {
                text.text = data!!.text!!
            }
            if (data!!.fontColor != null && data!!.fontColor != "") {
                fontData = data!!.fontColor.toString()
                text.typeface = Typeface.createFromAsset(assets, data!!.fontColor)
            }

            when (layoutPosition) {
               7->{
                    viewModel.setMediumCalender(
                        data!!.backgroundColor.toString(),
                        data!!.fontColor.toString(),
                        data!!.textColor!!,
                        mediumCalenderBinding,
                    this,false)
                }
                9 -> {
                    viewModel.setSmallMusicData(
                        data!!.backgroundColor.toString(),
                        data!!.fontColor.toString(),
                        data!!.textColor!!,
                        smallmusicBinding,
                    this,false)
                }
                10 -> {
                    viewModel.setMediumMusicData(
                        data!!.backgroundColor.toString(),
                        data!!.fontColor.toString(),
                        data!!.textColor!!,
                        mediummusicBinding,
                    this,false)
                }
                11->{
                    viewModel.setSmallCalender(
                        data!!.backgroundColor.toString(),
                        data!!.fontColor.toString(),
                        data!!.textColor!!,
                        smallCalBinding,
                    this,false)
                }
                12->{
                    viewModel.setLargeCalender(
                        data!!.backgroundColor.toString(),
                        data!!.fontColor.toString(),
                        data!!.textColor!!,
                        largeCalenderLayoutBinding,
                    this,false)
                }
                13->{
                    viewModel.setSmallLocation(  data!!.backgroundColor.toString(),
                        data!!.fontColor.toString(),
                        data!!.textColor!!,simpleLocationLayoutBinding,this,false)

                }
                14->{
                    viewModel.setMediumLocation(  data!!.backgroundColor.toString(),
                        data!!.fontColor.toString(),
                        data!!.textColor!!,mediumLocationLayoutBinding,this,false)

                }
                15->{
                    viewModel.setLargeLocation(  data!!.backgroundColor.toString(),
                        data!!.fontColor.toString(),
                        data!!.textColor!!,largeLocationLayoutBinding,this,false)

                }
                16->{
                    viewModel.setSmallClock(  data!!.backgroundColor.toString(),
                        data!!.fontColor.toString(),
                        data!!.textColor!!,clock2Binding,this,false)

                }
                18->{
                    if (data!!.uri != null && data!!.uri != ""){
                    val file : File = File(data!!.uri.toString())
                    val bmImg = BitmapFactory.decodeFile(data!!.uri)
                    ivImage.setImageBitmap(bmImg)}
                }
                19->{
                  viewModel.setMediumClock(  data!!.backgroundColor.toString(),
                            data!!.fontColor.toString(),
                            data!!.textColor!!,mediumWatchLayoutBinding,this,false)


                }
            }

        }

        if (!InterstitialAds.mIsLoading && InterstitialAds.mInterstitialAd == null){
            InterstitialAds.loadInterstitial(this)

        }
        binding.btApply.setOnClickListener {
         var strtext = ""
            if (layoutPosition != 18){
                strtext =  text.text.toString()
            }
            val data = ApplyData(
                "",
                typeWidget,
                layoutPosition,
                textColor,
                fontData,
                backData,
                strtext,
                uri.toString(),
                isBackground
            )

            showFullScreenInterstitial2(this,data)








        }
    }

    private fun setHeader() {
        Log.e("setHeader", "setHeader: "+typeWidget )
        when (typeWidget) {
            0 -> {
                binding.header.tvTitle.text = getString(R.string.small_widget)
            }
            1 -> {
                binding.header.tvTitle.text = getString(R.string.medium_widget)
            }
            2 -> {
                binding.header.tvTitle.text = getString(R.string.large_widget)
            }
        }

        binding.header.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setRadioClickEvent() {
        binding.rgEditSelection.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.rbBackground -> {
                    binding.rvBackground.visibility = View.VISIBLE
                    binding.rvText.visibility = View.GONE
                    binding.rvFont.visibility = View.GONE
                }

                R.id.rbText -> {
                    binding.rvBackground.visibility = View.GONE
                    binding.rvText.visibility = View.VISIBLE
                    binding.rvFont.visibility = View.GONE
                }

                R.id.rbFont -> {
                    binding.rvBackground.visibility = View.GONE
                    binding.rvText.visibility = View.GONE
                    binding.rvFont.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun addView() {
        var layoutToInflate = this.layoutInflater.inflate(R.layout.layout_battery, null)
        Log.e("layoutPosition", "addView: "+layoutPosition )

        when(layoutPosition) {
            1 -> {
                layoutToInflate = this.layoutInflater.inflate(R.layout.layout_battery, null)
            }
            2 -> {
                layoutToInflate = this.layoutInflater.inflate(R.layout.layout_time, null)
            }
            3 -> {
                layoutToInflate = this.layoutInflater.inflate(R.layout.layout_date, null)
            }
            4 -> {
                layoutToInflate = this.layoutInflater.inflate(R.layout.layout_event, null)
            }
            5 -> {
                layoutToInflate = this.layoutInflater.inflate(R.layout.layout_your_text, null)
            }
            6 -> {
                layoutToInflate = this.layoutInflater.inflate(R.layout.layout_medium_time, null)
            }
            7 -> {

                mediumCalenderBinding = MediumCalenderLayoutBinding.inflate(layoutInflater)
                binding.llEditWidget.addView(mediumCalenderBinding.root)
                viewModel.setMediumCalender(backData.toString(),fontData.toString(), textColor!!,mediumCalenderBinding,this,false)

            }
            8 -> {
                layoutToInflate = this.layoutInflater.inflate(R.layout.layout_large_date_time, null)
            }
            9 -> {
                smallmusicBinding = LayoutForMusicBinding.inflate(layoutInflater)
                binding.llEditWidget.addView(smallmusicBinding.root)
//                layoutToInflate = this.layoutInflater.inflate(R.layout.layout_for_music, null)
            }
            10 ->{
                mediummusicBinding = MediumMusicLayoutBinding.inflate(layoutInflater)
                binding.llEditWidget.addView(mediummusicBinding.root)
            }
            11->{
                smallCalBinding = SmallCalendarLayoutBinding.inflate(layoutInflater)
                binding.llEditWidget.addView(smallCalBinding.root)
                viewModel.setSmallCalender(backData.toString(),fontData.toString(), textColor!!,smallCalBinding,this,false)

            }
            12->{
                largeCalenderLayoutBinding = LargeCalenderLayoutBinding.inflate(layoutInflater)
                binding.llEditWidget.addView(largeCalenderLayoutBinding.root)

                viewModel.setLargeCalender(backData.toString(),fontData.toString(), textColor!!,largeCalenderLayoutBinding,this,false)

            }
            13->{
                simpleLocationLayoutBinding = SimpleLocationLayoutBinding.inflate(layoutInflater)
                binding.llEditWidget.addView(simpleLocationLayoutBinding.root)
                viewModel.setSmallLocation(backData.toString(),fontData.toString(), textColor!!,simpleLocationLayoutBinding,this,false)

            }
            14->{
                mediumLocationLayoutBinding = MediumLocationLayoutBinding.inflate(layoutInflater)
                binding.llEditWidget.addView(mediumLocationLayoutBinding.root)
                viewModel.setMediumLocation(backData.toString(),fontData.toString(), textColor!!,mediumLocationLayoutBinding,this,false)

            }
            15->{
                largeLocationLayoutBinding = LargeLocationLayoutBinding.inflate(layoutInflater)
                binding.llEditWidget.addView(largeLocationLayoutBinding.root)
                viewModel.setLargeLocation(backData.toString(),fontData.toString(), textColor!!,largeLocationLayoutBinding,this,false)

            }
            16->{
                clock2Binding = LayoutClock2Binding.inflate(layoutInflater)
                binding.llEditWidget.addView(clock2Binding.root)
                viewModel.setSmallClock(backData.toString(),fontData.toString(), textColor!!,clock2Binding,this,false)

            }
            17->{
                layoutToInflate = this.layoutInflater.inflate(R.layout.layout_quote, null)

            }
            18->{
                layoutToInflate = this.layoutInflater.inflate(R.layout.layout_image, null)
            }
            19->{
                mediumWatchLayoutBinding = MediumWatchLayoutBinding.inflate(layoutInflater)
                binding.llEditWidget.addView(mediumWatchLayoutBinding.root)
                viewModel.setMediumClock(backData.toString(),fontData.toString(), textColor!!,mediumWatchLayoutBinding,this,false)

            }
        }

        if (layoutPosition != 9 &&layoutPosition != 10 && layoutPosition != 11 && layoutPosition != 7 && layoutPosition != 12
            && layoutPosition != 14  && layoutPosition != 15 && layoutPosition != 13 && layoutPosition != 16 && layoutPosition != 19){
            binding.llEditWidget.addView(layoutToInflate)}
      if (layoutPosition != 18){
        text = layoutToInflate.findViewById(R.id.text1)}
        if (layoutPosition == 5 || layoutPosition == 17){
        taptoEdit =  layoutToInflate.findViewById(R.id.taptoEdit)
        taptoEdit.visibility = View.VISIBLE
        text.setOnClickListener {
            editDialog()
        }
        }
        when(layoutPosition) {
            1->{
                text.text =  viewModel.getBatteryPercentage(this).toString() + "  %"
            }
            8 -> {
                text2 = layoutToInflate.findViewById(R.id.text2)
                text.text =  viewModel.getCurrentDate().toString() + " "+viewModel.dayofMonth()
                text2.text =  viewModel.getCurrentTime().toString()
            }
            3->{
                text.text =  viewModel.getCurrentDate().toString() + "  "+viewModel.dayofMonth()
            }
            2->{
                text.text =  viewModel.getCurrentTime().toString()
            }
            4 -> {

                text2 = layoutToInflate.findViewById(R.id.text2)
                text3 = layoutToInflate.findViewById(R.id.text3)
                text2.text =  viewModel.getCurrentDate().toString() + " "+viewModel.dayofMonth()
                text.text = viewModel.getDayOfWeek().toString()

            }
            6->{
                text.text =  viewModel.getCurrentTime().toString()

            }
            18->{
                ivImage = findViewById(R.id.ivimage)
               var taptoSelect:TextView =  layoutToInflate.findViewById(R.id.taptoSelect)
                taptoSelect.visibility = View.VISIBLE
                ivImage.setOnClickListener {
                    ImagePicker.with(this)
                        .cropSquare()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(
                            512,
                            512
                        )    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start(PROFILE_IMAGE_REQ_CODE)
                }
            }

        }
        bgWidget = layoutToInflate.findViewById(R.id.cvWidgetBg)
    }

    private fun editDialog(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@EditWidgetActivity)
        builder.setTitle("Edit Text")

// Set up the input
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        input.setText(text.text.toString())
        builder.setView(input)

// Set up the buttons
        builder.setPositiveButton("OK",
            DialogInterface.OnClickListener { dialog, which ->
                text.text = input.text.toString() })
        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }

    private fun setBackgroundRecyclerView() {
        backgroundList.clear()
        backgroundList.add(R.drawable.bg_11)
        backgroundList.add(R.drawable.bg_1)
        backgroundList.add(R.drawable.bg_2)
        backgroundList.add(R.drawable.bg_3)
        backgroundList.add(R.drawable.bg_4)
        backgroundList.add(R.drawable.bg_5)
        backgroundList.add(R.drawable.bg_6)
        backgroundList.add(R.drawable.bg_7)
        backgroundList.add(R.drawable.bg_8)
        backgroundList.add(R.drawable.bg_9)
        backgroundList.add(R.drawable.bg_10)
        binding.rvBackground.adapter = BackgroundAdapter(backgroundList,
        {
           backData = backgroundList[it].toString()
            bgWidget.setBackgroundResource(backgroundList[it])
            isBackground = false
            when(layoutPosition){
                   9->{
                       viewModel.setSmallMusicData(backData.toString(),fontData.toString(), textColor!!,smallmusicBinding,this,false)

                   }
                   10->{
                       viewModel.setMediumMusicData(backData.toString(),fontData.toString(), textColor!!,mediummusicBinding,this,false)
                   }
                   11->{
                       viewModel.setSmallCalender(backData.toString(),fontData.toString(), textColor!!,smallCalBinding,this,false)

                   }
                   7->{
                       viewModel.setMediumCalender(backData.toString(),fontData.toString(), textColor!!,mediumCalenderBinding,this,false)

                   }
                   12->{
                       viewModel.setLargeCalender(backData.toString(),fontData.toString(), textColor!!,largeCalenderLayoutBinding,this,false)
                   }
                   13->{
                       viewModel.setSmallLocation(backData.toString(),fontData.toString(), textColor!!,simpleLocationLayoutBinding,this,false)

                   }
                   14->{
                       viewModel.setMediumLocation(backData.toString(),fontData.toString(), textColor!!,mediumLocationLayoutBinding,this,false)

                   }
                   15->{
                       viewModel.setLargeLocation(backData.toString(),fontData.toString(), textColor!!,largeLocationLayoutBinding,this,false)

                   }
                   16->{
                       viewModel.setSmallClock(backData.toString(),fontData.toString(), textColor!!,clock2Binding,this,false)

                   }
                 19->{
                     viewModel.setMediumClock(backData.toString(),fontData.toString(), textColor!!,mediumWatchLayoutBinding,this,false)

                 }
               }
        },{
             colorPickerDialog()
            })

    }

    private fun seTextRecyclerView() {
        textColorList.clear()
        textColorList.add(ContextCompat.getColor(this, R.color.bg_color_1))
        textColorList.add(ContextCompat.getColor(this, R.color.bg_color_2))
        textColorList.add(ContextCompat.getColor(this, R.color.bg_color_3))
        textColorList.add(ContextCompat.getColor(this, R.color.bg_color_4))
        textColorList.add(ContextCompat.getColor(this, R.color.bg_color_5))
        textColorList.add(ContextCompat.getColor(this, R.color.bg_color_6))
        textColorList.add(ContextCompat.getColor(this, R.color.bg_color_7))
        textColorList.add(ContextCompat.getColor(this, R.color.bg_color_8))
        textColorList.add(ContextCompat.getColor(this, R.color.bg_color_9))
        textColorList.add(ContextCompat.getColor(this, R.color.bg_color_10))
        textColorList.add(ContextCompat.getColor(this, R.color.bg_color_11))
        textColorList.add(ContextCompat.getColor(this, R.color.bg_color_12))
        textColorList.add(ContextCompat.getColor(this, R.color.bg_color_13))
        textColorList.add(ContextCompat.getColor(this, R.color.bg_color_14))
        textColorList.add(ContextCompat.getColor(this, R.color.bg_color_15))
        textColorList.add(ContextCompat.getColor(this, R.color.bg_color_16))
        binding.rvText.adapter = TextAdapter(textColorList) {
                  textColor = textColorList[it]
                  if (layoutPosition != 18){
                    text.setTextColor(textColor!!)}
                    when(layoutPosition) {
                        8 -> {
                            text2.setTextColor(textColor!!)
                        }
                        4 -> {
                            text2.setTextColor(textColor!!)
                            text3.setTextColor(textColor!!)
                        }
                        7->{
                            viewModel.setMediumCalender(backData.toString(),fontData.toString(), textColor!!,mediumCalenderBinding,this,false)

                        }
                        9->{
                           viewModel.setSmallMusicData(backData.toString(),fontData.toString(), textColor!!,smallmusicBinding,this,false)
                        }
                        10->{
                            viewModel.setMediumMusicData(backData.toString(),fontData.toString(), textColor!!,mediummusicBinding,this,false)
                        }
                         11->{
                        viewModel.setSmallCalender(backData.toString(),fontData.toString(), textColor!!,smallCalBinding,this,false)
                         }
                        12->{
                            viewModel.setLargeCalender(backData.toString(),fontData.toString(), textColor!!,largeCalenderLayoutBinding,this,false)
                        }
                        13->{
                            viewModel.setSmallLocation(backData.toString(),fontData.toString(), textColor!!,simpleLocationLayoutBinding,this,false)

                        }
                        14->{
                            viewModel.setMediumLocation(backData.toString(),fontData.toString(), textColor!!,mediumLocationLayoutBinding,this,false)

                        }
                        15->{
                            viewModel.setLargeLocation(backData.toString(),fontData.toString(), textColor!!,largeLocationLayoutBinding,this,false)

                        }
                        16->{
                            viewModel.setSmallClock(backData.toString(),fontData.toString(), textColor!!,clock2Binding,this,false)

                        }
                        19->{
                            viewModel.setMediumClock(backData.toString(),fontData.toString(), textColor!!,mediumWatchLayoutBinding,this,false)

                        }

                    }

        }
    }

    private fun setFontRecyclerView() {
        fontStyleList.clear()
        fontStyleList.add(getString(R.string.font_1))
        fontStyleList.add(getString(R.string.font_2))
        fontStyleList.add(getString(R.string.font_3))
        fontStyleList.add(getString(R.string.font_4))
        fontStyleList.add(getString(R.string.font_5))
        fontStyleList.add(getString(R.string.font_6))
        fontStyleList.add(getString(R.string.font_7))
        fontStyleList.add(getString(R.string.font_8))
        fontStyleList.add(getString(R.string.font_9))
        binding.rvFont.adapter = FontAdapter(fontStyleList) {
            fontData = fontStyleList[it]
            if (layoutPosition != 18){
                text.typeface = Typeface.createFromAsset(assets, fontStyleList[it])}

                    when(layoutPosition) {
                        8 -> {
                            text2.typeface = Typeface.createFromAsset(assets, fontStyleList[it])
                        }
                        4 -> {
                            text2.typeface = Typeface.createFromAsset(assets, fontStyleList[it])
                            text3.typeface = Typeface.createFromAsset(assets, fontStyleList[it])
                        }
                        7->{
                            viewModel.setMediumCalender(backData.toString(),fontData.toString(), textColor!!,mediumCalenderBinding,this,false)

                        }
                        9->{
                            viewModel.setSmallMusicData(backData.toString(),fontData.toString(), textColor!!,smallmusicBinding,this,false)
                        }
                        10->{
                            viewModel.setMediumMusicData(backData.toString(),fontData.toString(), textColor!!,mediummusicBinding,this,false)
                        }
                        11->{
                            viewModel.setSmallCalender(backData.toString(),fontData.toString(), textColor!!,smallCalBinding,this,false)

                        }
                        12->{
                            viewModel.setLargeCalender(backData.toString(),fontData.toString(), textColor!!,largeCalenderLayoutBinding,this,false)
                        }
                        13->{
                            viewModel.setSmallLocation(backData.toString(),fontData.toString(), textColor!!,simpleLocationLayoutBinding,this,false)

                        }
                        14->{
                            viewModel.setMediumLocation(backData.toString(),fontData.toString(), textColor!!,mediumLocationLayoutBinding,this,false)

                        }
                        15->{
                            viewModel.setLargeLocation(backData.toString(),fontData.toString(), textColor!!,largeLocationLayoutBinding,this,false)

                        }
                        16->{
                            viewModel.setSmallClock(backData.toString(),fontData.toString(), textColor!!,clock2Binding,this,false)

                        }
                        19->{
                            viewModel.setMediumClock(backData.toString(),fontData.toString(), textColor!!,mediumWatchLayoutBinding,this,false)

                        }

                    }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            when (requestCode) {
                PROFILE_IMAGE_REQ_CODE -> {
                    val uri1: Uri = data?.data!!
                    uri = data.data!!.encodedPath.toString()
                    Log.e("onActivityResult", "onActivityResult1111 "+uri )
                    GlideHelper.loadImage(ivImage, uri1)

                }
            }
    }

    private fun colorPickerDialog() {
        val colorDialog = Dialog(this, R.style.DialogCustomTheme)
        colorDialog.setCancelable(false)

        val bindingcolor: ColorPickerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.color_picker,
            null,
            false
        )
        val window: Window? = colorDialog.window
        val layoutParams: WindowManager.LayoutParams = window!!.attributes
        layoutParams.gravity = Gravity.BOTTOM
        colorDialog.setCancelable(true)
        colorDialog.setContentView(bindingcolor.root)

        colorDialog.show()

        bindingcolor.colorWheel.colorChangeListener = { rgb: Int ->
            bindingcolor.gradientSeekBar.setTransparentToColor(rgb)
            bindingcolor.valueSeekBar.setBlackToColor(rgb)
        }

        bindingcolor.btnCancel.setOnClickListener {
            colorDialog.dismiss()
        }

        bindingcolor.btnDone.setOnClickListener {

            val colors = intArrayOf(bindingcolor.valueSeekBar.argb,bindingcolor.valueSeekBar.argb)
            //create a new gradient color
            val gd = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, colors
            )

            gd.cornerRadius = 20f
            isBackground = true
            backData =bindingcolor.valueSeekBar.argb.toString()

            when(layoutPosition){
                9->{
                    viewModel.setSmallMusicData(backData.toString(),fontData.toString(), textColor!!,smallmusicBinding,this,true)

                }
                10->{
                    viewModel.setMediumMusicData(backData.toString(),fontData.toString(), textColor!!,mediummusicBinding,this,true)
                }
                11->{
                    viewModel.setSmallCalender(backData.toString(),fontData.toString(), textColor!!,smallCalBinding,this,true)

                }
                7->{
                    viewModel.setMediumCalender(backData.toString(),fontData.toString(), textColor!!,mediumCalenderBinding,this,true)

                }
                12->{
                    viewModel.setLargeCalender(backData.toString(),fontData.toString(), textColor!!,largeCalenderLayoutBinding,this,true)
                }
                13->{
                    viewModel.setSmallLocation(backData.toString(),fontData.toString(), textColor!!,simpleLocationLayoutBinding,this,true)

                }
                14->{
                    viewModel.setMediumLocation(backData.toString(),fontData.toString(), textColor!!,mediumLocationLayoutBinding,this,true)

                }
                15->{
                    viewModel.setLargeLocation(backData.toString(),fontData.toString(), textColor!!,largeLocationLayoutBinding,this,true)

                }
                16->{
                    viewModel.setSmallClock(backData.toString(),fontData.toString(), textColor!!,clock2Binding,this,true)

                }
                19->{
                    viewModel.setMediumClock(backData.toString(),fontData.toString(), textColor!!,mediumWatchLayoutBinding,this,true)

                }else->{
                bgWidget.background =gd
                }
            }

            colorDialog.dismiss()
        }

    }

    fun onColorChanged(valueGradient: Int, alphaGradientColor: Int) {
        indicatorColor.set(setColorAlpha(valueGradient, alphaGradientColor))

    }

    fun setColorAlpha(argb: Int, alpha: Int) = Color.argb(
        alpha,
        Color.red(argb),
        Color.green(argb),
        Color.blue(argb)
    )

    fun showFullScreenInterstitial2(context: Activity,vardata:ApplyData) {
        InterstitialAds.mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                super.onAdClicked()
            }

            override fun onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent()
                Log.e("onAdClicked", "onAdClicked: ff122"+vardata )
                InterstitialAds.mInterstitialAd = null
//                InterstitialAds.loadInterstitial(context)
                startActivity(
                    Intent(this@EditWidgetActivity, MainActivity::class.java).putExtra(DATALIST, vardata)
                        .putExtra(ISEDIT, isEdit)
                )
                finish()

            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                super.onAdFailedToShowFullScreenContent(p0)
                InterstitialAds.mInterstitialAd = null
            }

            override fun onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent()
//                showSnackBarRed(this@EditWidgetActivity,getString(R.string.thanks_for_selecting_widget_n_please_wait))

            }
        }
        InterstitialAds.mInterstitialAd?.show(context)
    }

}