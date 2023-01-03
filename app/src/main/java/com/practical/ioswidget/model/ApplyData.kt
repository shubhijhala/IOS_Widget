package com.practical.ioswidget.model

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Widget(
    var typeName:String,
    var widget : ArrayList<ApplyData>? = null
): Parcelable

@Parcelize
data class ApplyData(
    var widetname:String? = null,
    var typeWidget:Int? = null,
    var layoutToInflate : Int? = null,
    var textColor:Int? = 0,
    var fontColor : String? = null,
    var backgroundColor :String? = null,
    var text :String? = null,
    var uri: String ? = null,
    var isbackground:Boolean = false

    ): Parcelable
