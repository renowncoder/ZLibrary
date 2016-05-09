package com.duzhou.zlibray.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

import java.util.Set;

/**
 * Created by zhou on 16-5-6.
 */
public class BluetoothUtil {
    BluetoothAdapter bluetoothAdapter = null;

    /**
     * single instance
     */
    private static BluetoothUtil instance;

    private BluetoothUtil() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public static BluetoothUtil get() {
        if (instance == null) {
            instance = new BluetoothUtil();
        }
        return instance;
    }

    /**
     * is bluetooth is available
     */
    public boolean isAvailable(){
        return bluetoothAdapter != null ;
    }

    public boolean isEnabled(){
        return bluetoothAdapter.isEnabled();
    }

    /**
     * open the BT
     * @return TRUE TO HAS BEGAN OR FALSE TO SOME ERROR
     */
    public boolean enableAction(){
        if (!bluetoothAdapter.isEnabled())
            return bluetoothAdapter.enable();
        return  true;
    }

    /**
     * close the BT
     * @return
     */
    public boolean disableAction(){
        if (bluetoothAdapter.isEnabled())
            return bluetoothAdapter.disable();
        return true;
    }

    /**
     * get the name of device's name
     * @return
     */
    public String getBtName(){
        return bluetoothAdapter.getName();
    }

    /**
     * change the device's name
     * @param newName
     * @return
     */
    public boolean changeBtName(String newName){
        enableAction();
        return bluetoothAdapter.setName(newName);
    }

    /**
     *
     * @return true if discovering
     */
    public boolean isDiscovery(){
        return bluetoothAdapter.isDiscovering();
    }

    /**
     * start discovery
     */
    public void startDiscovery() {
        enableAction();
        bluetoothAdapter.startDiscovery();
    }

    public void stopDiscovery() {
        bluetoothAdapter.cancelDiscovery();
    }

    public void registerBroadCast(Context context, BroadcastReceiver mReceiver) {
        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        // Register for broadcasts when discovery has finished
        context.registerReceiver(mReceiver, filter);
    }

    public void unRegisterBroadCast(Context context, BroadcastReceiver mReceiver) {
        context.unregisterReceiver(mReceiver);
    }

    public Set<BluetoothDevice> getPairedDevices(){
        return bluetoothAdapter.getBondedDevices();
    }





}
