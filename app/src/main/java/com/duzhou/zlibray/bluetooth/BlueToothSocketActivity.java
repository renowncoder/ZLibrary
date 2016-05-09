package com.duzhou.zlibray.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.UUID;

import com.duzhou.zlibray.BaseActivity;
import com.duzhou.zlibray.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhou on 16-5-9.
 */
public class BlueToothSocketActivity extends BaseActivity {
    private String TAG = "BlueToothSocketActivity";
    private Context mContext = BlueToothSocketActivity.this;
    BluetoothDevice device;

    // Unique UUID for this application
    private static final UUID MY_UUID_SECURE =
            UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    @Bind(R.id.tv_content)
    TextView textView;

    @Bind(R.id.btn_con)
    Button conButton;
    @Bind(R.id.btn_send)
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_socket);
        ButterKnife.bind(this);
        device = getIntent().getParcelableExtra("device");
        Log.d(TAG, device.getName() + " --- " + device.toString());

    }

    @OnClick(R.id.btn_send)
    public void sendAction(View view) {
        btThread.start();
    }

    Thread btThread = new Thread() {
        @Override
        public void run() {
            super.run();
            try {
                BluetoothSocket socket = device.createRfcommSocketToServiceRecord(MY_UUID_SECURE);
                socket.connect();
                OutputStream os = socket.getOutputStream();
                os.write("hello world".getBytes());
            } catch (IOException e) {

            } finally {

            }
        }
    };


}
