package com.duzhou.zlibray.bluetooth;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.duzhou.zlibray.BaseActivity;
import com.duzhou.zlibray.R;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by zhou on 16-5-6.
 */
public class BlueToothActivity extends BaseActivity {

    private String TAG = "BlueToothActivity";
    private Context mContext = BlueToothActivity.this;

    @Bind(R.id.sw_status)
    Switch status;

    @Bind(R.id.tv_name)
    TextView nameTextView;
    @Bind(R.id.paired_devices) ListView pairedListView;
    @Bind(R.id.new_devices) ListView newListView;

    /**
     * Paired discovered devices
     */
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;

    /**
     * Newly discovered devices
     */
    private ArrayAdapter<String> mNewDevicesArrayAdapter;

    List<BluetoothDevice> pairedDeviceList = new ArrayList<>();
    List<BluetoothDevice> scanDeviceList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        ButterKnife.bind(this);
        if (!BluetoothUtil.get().isAvailable()) {
            Toast.makeText(this, "不支持蓝牙", Toast.LENGTH_SHORT).show();
        }
        initView();
    }

    private void initView() {
        setTitle("蓝牙操作");
        status.setChecked(BluetoothUtil.get().isEnabled());
        status.setOnCheckedChangeListener(checkedChangeListener);
        nameTextView.setText(BluetoothUtil.get().getBtName());
        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(mContext, R.layout.device_name);
        mNewDevicesArrayAdapter = new ArrayAdapter<String>(mContext, R.layout.device_name);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
        newListView.setAdapter(mNewDevicesArrayAdapter);
        loadPairedDevice();
        newListView.setOnItemClickListener(itemClickListener);
        pairedListView.setOnItemClickListener(itemClickListener);
    }

    Switch.OnCheckedChangeListener checkedChangeListener = new Switch.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked && BluetoothUtil.get().isEnabled() == false) {
                BluetoothUtil.get().enableAction();
            } else if (isChecked == false && BluetoothUtil.get().isEnabled() == true) {
                BluetoothUtil.get().disableAction();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        BluetoothUtil.get().enableAction();
        BluetoothUtil.get().registerBroadCast(mContext,mReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        BluetoothUtil.get().startDiscovery();
    }

    @Override
    protected void onStop() {
        super.onStop();
        BluetoothUtil.get().stopDiscovery();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BluetoothUtil.get().unRegisterBroadCast(mContext,mReceiver);
    }

    @OnClick(R.id.button_scan)
    public void showAroundDevice(View view) {
        BluetoothUtil.get().startDiscovery();
    }

    @OnClick(R.id.tv_name)
    public void modifyBTname(){
        final EditText editText = new EditText(this);
        editText.setPadding(10,0,0,0);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("输入新的蓝牙名称:").setView(editText).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = editText.getText().toString().trim() ;
                        Toast.makeText(mContext,"新的蓝牙名称:"+name,Toast.LENGTH_SHORT).show();
                        BluetoothUtil.get().changeBtName(name);
                        nameTextView.setText(name);
                    }
                }).setNegativeButton("取消",null);
        builder.create().show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh){
            if (BluetoothUtil.get().isDiscovery()){
                BluetoothUtil.get().startDiscovery();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())){
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                loadScanDevice(device);
            }else if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(intent.getAction())){
                mNewDevicesArrayAdapter.clear();
                scanDeviceList.clear();
            }else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(intent.getAction())){
                Toast.makeText(mContext,"扫描结束",Toast.LENGTH_SHORT).show();
                loadPairedDevice();
            }
        }
    };


    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            BluetoothUtil.get().stopDiscovery();
            BluetoothDevice device = null;
            if (parent.getId() == newListView.getId()){
                device = scanDeviceList.get(position);
            }else if(parent.getId() == pairedListView.getId()){
                device = pairedDeviceList.get(position);
            }
            Log.d(TAG,device.toString());
            Intent intent = new Intent(mContext,BlueToothSocketActivity.class);
            intent.putExtra("device",device);
            startActivity(intent);
        }
    };

    /**
     * load all the paired devices
     */
    private void loadPairedDevice(){
        mPairedDevicesArrayAdapter.clear();
        pairedDeviceList.clear();
        Set<BluetoothDevice> pairedDeviceSet = BluetoothUtil.get().getPairedDevices();
        List<String> strings = new ArrayList<>();
        for (BluetoothDevice device:pairedDeviceSet) {
            strings.add(getDisplayStringByDevice(device));
            pairedDeviceList.add(device);
        }
        mPairedDevicesArrayAdapter.addAll(strings);
    }

    private void loadScanDevice(BluetoothDevice device){
        scanDeviceList.add(device);
        mNewDevicesArrayAdapter.add(getDisplayStringByDevice(device));
    }

    private String getDisplayStringByDevice(BluetoothDevice device){
        StringBuffer sb = new StringBuffer();
        if (device != null){
            sb = new StringBuffer(device.getName() + "\n" + device.getAddress());
            if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                sb.append("\t" + "已配对");
            }else if (device.getBondState() == BluetoothDevice.BOND_BONDING){
                sb.append("\t" + "正在配对");
            }else if(device.getBondState() == BluetoothDevice.BOND_NONE){

            }
        }
        if (sb.length() == 0){
            sb.append("\t some error");
        }
        return sb.toString();
    }

    /**
     * 设置listview高度，注意listview子项必须为LinearLayout才能调用该方法
     * @param listview listview
     *
     */
    public static void  setListViewHeight(ListView listview){
        int totalHeight = 0;
        ListAdapter adapter= listview.getAdapter();
        if(null != adapter){
            for (int i = 0; i <adapter.getCount(); i++) {
                View listItem = adapter.getView(i, null, listview);
                if (null != listItem) {
                    listItem.measure(0, 0);//注意listview子项必须为LinearLayout才能调用该方法
                    totalHeight += listItem.getMeasuredHeight();
                }
            }

            ViewGroup.LayoutParams params = listview.getLayoutParams();
            params.height = totalHeight + (listview.getDividerHeight() * (listview.getCount() - 1));
            listview.setLayoutParams(params);
        }
    }

}
