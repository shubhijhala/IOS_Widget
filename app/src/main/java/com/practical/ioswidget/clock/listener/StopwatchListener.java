package com.practical.ioswidget.clock.listener;

import com.practical.ioswidget.clock.enumeration.StopwatchState;
import com.practical.ioswidget.clock.model.StopwatchSavedItem;

public interface StopwatchListener {

    void onStopwatchStateChanged(StopwatchState stopwatchState);

    void onStopwatchSaveValue(StopwatchSavedItem stopwatchSavedItem);
}
