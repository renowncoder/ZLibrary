package com.duzhou.zlibray;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by zhou on 16/4/19.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction().add(R.id.layout, new MainFragment()).commit();

    }
}
