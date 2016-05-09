package com.duzhou.zlibray;

import android.app.Activity;
import android.view.MenuItem;

import butterknife.ButterKnife;

/**
 * Created by zhou on 16-5-6.
 */
public class BaseActivity extends Activity {

    @Override
    protected void onResume() {
        super.onResume();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
