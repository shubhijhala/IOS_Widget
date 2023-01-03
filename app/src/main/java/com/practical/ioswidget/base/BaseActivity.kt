package com.app.customwidget.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.practical.ioswidget.R

abstract class BaseActivity: AppCompatActivity() {


    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
    }


    fun showSnackBarRed(context: Context?, message: String?) {
        try {
            val toast = Toast.makeText(context,message, Toast.LENGTH_SHORT)

            //val view: View = toast.view!!
            //view.setBackgroundColor(Color.parseColor("#219760"))
            val inflater : LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view: View = inflater.inflate(R.layout.item_toast_red, null);
            toast.view = view
            //view.setBackgroundResource(R.drawable.toast_background)
            val textView: TextView = view.findViewById(R.id.tvToast)
            textView.text = message
            textView.setTextColor(ContextCompat.getColor(context!!,R.color.black))
            toast.setGravity(Gravity.BOTTOM,0,0)
            toast.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}