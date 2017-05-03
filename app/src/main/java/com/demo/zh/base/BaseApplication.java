package com.demo.zh.base;

import android.app.Application;

/**
 * @author zhongzhen
 * @date 2017/5/3
 * @describe
 **/
public class BaseApplication extends Application {
    static BaseApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }
}
