package com.duzhou.zlibray.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by zhou on 16-5-9.
 */
public class BlueSocketConntectThread extends Thread {

    private String TAG = "BlueSocketConntectedThread";
    BluetoothSocket socket = null;
    Handler mHandler = null;
    BluetoothDevice device = null;
    BlueSocketConntectedThread conntectedThread;

    // Unique UUID for this application
    private static final UUID MY_UUID_SECURE = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");

    public BlueSocketConntectThread(BluetoothDevice device, Handler mHandler) {
        this.mHandler = mHandler;
        this.device = device;
    }

    @Override
    public void run() {
        super.run();
        try {
            if (socket == null) {
                socket = device.createRfcommSocketToServiceRecord(MY_UUID_SECURE);
            }
            if (!socket.isConnected()) {
                socket.connect();
            }
            conntectedThread = new BlueSocketConntectedThread(socket,mHandler);
            conntectedThread.start();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException e2) {
                Log.e(TAG, "unable to close() " + " socket during connection failure", e2);
            }
        }

    }

    public void cancel() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
