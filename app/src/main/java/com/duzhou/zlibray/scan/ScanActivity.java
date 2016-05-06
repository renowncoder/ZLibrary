package com.duzhou.zlibray.scan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;

import com.duzhou.zlibray.BaseActivity;
import com.duzhou.zlibray.R;

/**
 * Created by zhou on 16-5-6.
 */
public class ScanActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
    }

    public void clickToZXing(View view) {
        startActivity(new Intent(this,ZXingActivity.class));
    }

    public void clickToZBar(View view) {
        startActivity(new Intent(this,ZBarActivity.class));
    }
}
