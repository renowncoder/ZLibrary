package com.duzhou.zlibray.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by zhou on 16-5-9.
 */
public class BlueSocketConntectedThread extends Thread {

    private String TAG = "BlueSocketConntectedThread";
    BluetoothSocket socket = null;
    Handler mHandler = null;
    InputStream tmpIn = null;
    OutputStream tmpOut = null;

    public BlueSocketConntectedThread(BluetoothSocket socket, Handler mHandler) {
        this.mHandler = mHandler;
        this.socket = socket;
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();

        byte[] buffer = new byte[1024];
        int bytes;

        // Keep listening to the InputStream while connected
        while (true) {
            try {
                // Read from the InputStream
                bytes = tmpIn.read(buffer);
                // Send the obtained bytes to the UI Activity
                mHandler.obtainMessage(Constants.MESSAGE_READ, bytes, -1, buffer).sendToTarget();
            } catch (IOException e) {
                Log.e(TAG, "disconnected", e);
//                connectionLost();
//                // Start the service over to restart listening mode
//                BluetoothChatService.this.start();
                break;
            }
        }

    }


    /**
     * Write to the connected OutStream.
     *
     * @param buffer The bytes to write
     */
    public void write(byte[] buffer) {
        try {
            tmpOut.write(buffer);

            // Share the sent message back to the UI Activity
            mHandler.obtainMessage(Constants.MESSAGE_WRITE, -1, -1, buffer)
                    .sendToTarget();
        } catch (IOException e) {
            Log.e(TAG, "Exception during write", e);
        }
    }

    public void cancel() {

    }


}
