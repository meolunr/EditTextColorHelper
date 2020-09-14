package com.meolunr.edittextcolorhelper.sample;

import android.app.Application;
import android.content.Context;

import me.weishu.reflection.Reflection;

/**
 * Created by Meolunr on 2020/9/14
 * Email meolunr@gmail.com
 */
public class SampleApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Reflection.unseal(base);
    }
}