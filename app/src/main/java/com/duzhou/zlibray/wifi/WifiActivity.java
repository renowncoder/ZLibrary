package com.duzhou.zlibray.wifi;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.duzhou.zlibray.BaseActivity;
import com.duzhou.zlibray.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnItemLongClick;

/**
 * Created by zhou on 16-4-22.
 */
public class WifiActivity extends BaseActivity {

    @Bind(R.id.sw_wifi_status)
    Switch status;
    @Bind(R.id.tv_info)
    TextView wifiInfo;
    @Bind(R.id.list_wifi_scan)
    ListView wifiScanList;


    WifiUtil wifiUtil;
    WifiScanAdapter scanAdapter ;
    List<ScanResult> scanResults = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_demo);
        ButterKnife.bind(this);
        wifiUtil = new WifiUtil();

        TextView textView = new TextView(this);
        textView.setText("WIFI扫描结果：");
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(18);
        wifiScanList.addHeaderView(textView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiReceiver, filter);
        initview();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(wifiReceiver);
    }

    private void initview() {
        status.setChecked(wifiUtil.getWIFIStatus());
        wifiInfo.setText(wifiUtil.getWIFIInfo());
        showWifiAdapter();
    }

    @OnCheckedChanged(R.id.sw_wifi_status)
    void statusChange(Switch sw, boolean newstatus) {
        if (isDialogShowing() == false){
            wifiUtil.changeStatus(newstatus);
            showWaitDialog();
        }
    }

    @OnItemLongClick(R.id.list_wifi_scan)
    boolean longClickScanList(AdapterView<?> parent, View view, int position, long id){
        Toast.makeText(this,"点击了第几个:"+scanResults.get(position).toString(),Toast.LENGTH_SHORT).show();
        return false;
    }


    BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
                switch (wifiState) {
                    case WifiManager.WIFI_STATE_ENABLED:
                        cancelWaitDialog();
                        initview();
                        break;
                    case WifiManager.WIFI_STATE_ENABLING:
                        break;
                    case WifiManager.WIFI_STATE_DISABLED:
                        cancelWaitDialog();
                        initview();
                        break;
                    case WifiManager.WIFI_STATE_DISABLING:
                        break;
                    case WifiManager.WIFI_STATE_UNKNOWN:
                        break;
                }
            }
        }
    };


    ProgressDialog progressDialog;

    private void showWaitDialog() {
        progressDialog = ProgressDialog.show(this, null, "waiting",true,true);
    }

    private void cancelWaitDialog() {
        if (progressDialog != null) {
            progressDialog.cancel();
            progressDialog = null;
        }
    }

    private boolean isDialogShowing(){
        if (progressDialog != null)
            return progressDialog.isShowing();
        return false;
    }


    private void showWifiAdapter(){
        if (scanAdapter == null){
            scanResults.clear();
            scanResults.addAll(wifiUtil.getScanReslut()) ;
            scanAdapter = new WifiScanAdapter(this,scanResults);
            wifiScanList.setAdapter(scanAdapter);
        } else {
            scanResults.addAll(wifiUtil.getScanReslut()) ;
            scanAdapter.notifyDataSetChanged();
        }
    }

}
