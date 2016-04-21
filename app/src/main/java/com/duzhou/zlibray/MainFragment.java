package com.duzhou.zlibray;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by zhou on 16/4/19.
 */
public class MainFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_main);
    }
}
