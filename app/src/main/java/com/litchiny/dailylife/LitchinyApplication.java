package com.litchiny.dailylife;

import android.app.Application;

import org.litepal.LitePal;

/**
 * Created by ll on 2018/8/4.
 */

public class LitchinyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
    }
}
