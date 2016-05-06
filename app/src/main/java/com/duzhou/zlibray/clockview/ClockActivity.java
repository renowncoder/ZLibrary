package com.duzhou.zlibray.clockview;

import android.app.Activity;
import android.os.Bundle;

import com.duzhou.zlibray.BaseActivity;

/**
 * Created by zhou on 16-5-6.
 */
public class ClockActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ClockView(this));
    }
}
