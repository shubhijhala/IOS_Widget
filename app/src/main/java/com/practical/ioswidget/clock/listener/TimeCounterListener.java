package com.practical.ioswidget.clock.listener;


import com.practical.ioswidget.clock.enumeration.TimeCounterState;

public interface TimeCounterListener {

    void onTimeCounterCompleted();

    void onTimeCounterStateChanged(TimeCounterState timeCounterState);

}
