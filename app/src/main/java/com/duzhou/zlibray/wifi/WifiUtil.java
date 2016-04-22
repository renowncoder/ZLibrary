package com.duzhou.zlibray.wifi;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.duzhou.zlibray.App;

import java.util.List;

/**
 * Created by zhou on 16-4-22.
 */
public class WifiUtil {

    WifiManager wifiManager;
    public WifiUtil(){
        wifiManager = (WifiManager) App.getAppContext().getSystemService(Context.WIFI_SERVICE);// 获取Wifi服务
    }


    /**
     * 获取wifi的状态
     * @param context
     * @return
     */
    public boolean getWIFIStatus(){
        if (wifiManager != null){
            return wifiManager.isWifiEnabled();
        }
        return false;
    }

    /**
     * 修改WIFI的状态
     * @param status
     */
    public void changeStatus(boolean status){
        if (wifiManager != null && wifiManager.getWifiState() != WifiManager.WIFI_STATE_DISABLING
                && wifiManager.getWifiState() != WifiManager.WIFI_STATE_ENABLING)
            wifiManager.setWifiEnabled(status);
    }

    public String getWIFIInfo(){
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.toString().replace(",","\n");
    }

    public String getCurrentSSID(){
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo != null)
            return  wifiInfo.getSSID();
        return  null;
    }

    /**
     * 得到扫描的结果
     * @return
     */
    public List<ScanResult> getScanReslut(){
        return wifiManager.getScanResults();
    }

}
