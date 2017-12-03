package com.qf58.androidnote;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * Created by linSir
 * date at 2017/12/2.
 * describe:
 */

public class MyApplication extends Application {

    /**
     * myApplication是 全局Application
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
