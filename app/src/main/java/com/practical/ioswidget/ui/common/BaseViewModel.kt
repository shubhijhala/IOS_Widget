package com.practical.ioswidget.ui.common

import android.app.Application
import androidx.lifecycle.AndroidViewModel

open class BaseViewModel  constructor(application: Application) :
    AndroidViewModel(application) {
    protected val context
        get() = getApplication<Application>()
}