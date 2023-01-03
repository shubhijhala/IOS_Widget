package com.practical.ioswidget.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practical.ioswidget.model.ApplyData
import com.practical.ioswidget.model.CalendarData
import com.practical.ioswidget.model.Widget
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.RandomAccessFile
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList


object PrefHelper {

    fun setisFirst(isSet:Boolean,context: Context){
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putBoolean("setisFirst", isSet)
        editor.commit()
    }
    fun getisFirst(context: Context):Boolean{
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getBoolean("setisFirst",false)
    }

    fun saveArrayList(list: ArrayList<Widget>, key: String?, context: Context) {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    fun getArrayList(key: String?,context: Context): ArrayList<Widget> {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val gson = Gson()
        val json: String? = prefs.getString(key, null)
        val type: Type = object : TypeToken<ArrayList<Widget?>?>() {}.type
        return gson.fromJson(json, type) as ArrayList<Widget>
    }

    fun setData(context:Context){
         val widgetList: ArrayList<Widget> = ArrayList()
        var smallwidget : ArrayList<ApplyData> = ArrayList()
        var midiumWidget : ArrayList<ApplyData> = ArrayList()
        var largeWidget : ArrayList<ApplyData> = ArrayList()
        smallwidget.add( ApplyData("$SMALLWIDGET #1",0,1))
        smallwidget.add( ApplyData("$SMALLWIDGET #2",0,2))
        smallwidget.add( ApplyData("$SMALLWIDGET #3",0,3))
        smallwidget.add( ApplyData("$SMALLWIDGET #4",0,4))
        smallwidget.add( ApplyData("$SMALLWIDGET #5",0,5))
        midiumWidget.add( ApplyData("$MEDIUMWIDGET #1",1,6))
        midiumWidget.add( ApplyData("$MEDIUMWIDGET #2",1,7))
        largeWidget.add( ApplyData("$LARGEWIDGET #1",2,8))
        midiumWidget.add( ApplyData("$MEDIUMWIDGET #3",1,10))
        widgetList.add(Widget(SMALLWIDGET,smallwidget))
        widgetList.add(Widget(MEDIUMWIDGET,midiumWidget))
        widgetList.add(Widget(LARGEWIDGET,largeWidget))
        saveArrayList(widgetList,LIST,context)
    }


    private const val INSTALLATION = "INSTALLATION"

    @Synchronized
    fun isFirstLaunch(context: Context): Boolean {
        var sID: String? = null
        var launchFlag = false
        if (sID == null) {
            val installation = File(context.filesDir, INSTALLATION)
            try {
                if (!installation.exists()) {
                    launchFlag = true
                    writeInstallationFile(installation)
                }
                sID = readInstallationFile(installation)
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
        setisFirst(launchFlag,context)
        return launchFlag
    }

    @Throws(IOException::class)
    private fun readInstallationFile(installation: File): String? {
        val f = RandomAccessFile(installation, "r") // read only mode
        val bytes = ByteArray(f.length().toInt())
        f.readFully(bytes)
        f.close()
        return String(bytes)
    }

    @Throws(IOException::class)
    private fun writeInstallationFile(installation: File) {
        val out = FileOutputStream(installation)
        val id: String = UUID.randomUUID().toString()
        out.write(id.toByteArray())
        out.close()
    }

     fun calenderData( monthDate : Int):ArrayList<CalendarData>{
        var list : ArrayList<CalendarData> = ArrayList()
         list.add(CalendarData(""))
         list.add(CalendarData(""))
        for (i in 1 until monthDate){
            list.add(CalendarData(i.toString()))
        }
        return list
    }

    fun monthData(month : Int):String{
        var strMonth =""
        when(month){
            1->strMonth = "January"
            2->strMonth ="February"
            3->strMonth ="March"
            4->strMonth ="April"
            5->strMonth ="May"
            6->strMonth="June"
            7->strMonth="July"
            8->strMonth="August"
            9->strMonth="September"
            10->strMonth="October"
            11->strMonth ="November "
            12->strMonth ="December"
        }
        return strMonth
    }

    fun monthData2(month : Int):String{
        var strMonth =""
        when(month){
            1->strMonth = "Jan"
            2->strMonth ="Feb"
            3->strMonth ="Mar"
            4->strMonth ="Apr"
            5->strMonth ="May"
            6->strMonth="Jun"
            7->strMonth="July"
            8->strMonth="Aug"
            9->strMonth="Sept"
            10->strMonth="Oct"
            11->strMonth ="Nov "
            12->strMonth ="Dec"
        }
        return strMonth
    }
}