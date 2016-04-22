package com.duzhou.zlibray;

import android.app.Application;
import android.content.Context;

/**
 * Created by zhou on 16-4-22.
 */
public class App extends Application {
    static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getAppContext(){
        return context ;
    }
}
