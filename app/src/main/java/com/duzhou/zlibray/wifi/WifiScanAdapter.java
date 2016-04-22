package com.duzhou.zlibray.wifi;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.duzhou.zlibray.R;

import java.util.List;

/**
 * Created by zhou on 16-4-22.
 */
public class WifiScanAdapter extends BaseAdapter {
    Context context ;
    List<ScanResult> scanResults;
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    WifiUtil wifiUtil;
    public WifiScanAdapter(Context context , List<ScanResult> scanResults){
        this.context = context;
        this.scanResults = scanResults;
        mInflater = LayoutInflater.from(context);
        wifiUtil = new WifiUtil();
    }

    @Override
    public int getCount() {
        return scanResults.size();
    }

    @Override
    public ScanResult getItem(int position) {
        return scanResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.item_wifi_scan,null);
            viewHolder = new ViewHolder();
            viewHolder.ssid = (TextView) convertView.findViewById(R.id.tv_ssid);
            viewHolder.conntected = (TextView) convertView.findViewById(R.id.tv_contected);
            viewHolder.lock = (TextView) convertView.findViewById(R.id.tv_lock);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ScanResult scanResult = scanResults.get(position);
        viewHolder.ssid.setText(scanResult.SSID);
        viewHolder.lock.setText(scanResult.capabilities);
        viewHolder.conntected.setText(isConnected(scanResult)? "conntected":"unConnected");
//        viewHolder.conntected.setText(scanResult.SSID + "--" + wifiUtil.getCurrentSSID() + "--" + isConnected(scanResult));
        return convertView;
    }

    class ViewHolder {
        TextView ssid , conntected , lock;
    }

    private boolean isConnected(ScanResult scanResult){
        String ssid = wifiUtil.getCurrentSSID();
        if (ssid != null && scanResult != null){
            if (ssid.equals("\""+scanResult.SSID+"\""))
                return true;
        }
        return false;
    }

    private String getLockType(ScanResult scanResult){
        if (scanResult.capabilities.contains(WifiConfiguration.KeyMgmt.strings[WifiConfiguration.KeyMgmt.WPA_PSK])){
            return WifiConfiguration.KeyMgmt.strings[WifiConfiguration.KeyMgmt.WPA_PSK] ;
        }else if(scanResult.capabilities.contains(WifiConfiguration.KeyMgmt.strings[WifiConfiguration.KeyMgmt.WPA_EAP])){
            return WifiConfiguration.KeyMgmt.strings[WifiConfiguration.KeyMgmt.WPA_EAP] ;
        }else if(scanResult.capabilities.contains(WifiConfiguration.KeyMgmt.strings[WifiConfiguration.KeyMgmt.IEEE8021X])){
            return WifiConfiguration.KeyMgmt.strings[WifiConfiguration.KeyMgmt.IEEE8021X] ;
        }else {
            return "";
        }
    }
}
