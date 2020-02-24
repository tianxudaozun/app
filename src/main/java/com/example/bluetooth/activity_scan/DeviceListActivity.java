package com.example.bluetooth.activity_scan;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import com.example.bluetooth.R;
import com.example.bluetooth.bluetoothService.BluetoothService;

public class DeviceListActivity extends AppCompatActivity {

    private BluetoothAdapter mBluetoothAdapter;
    private Handler handler = new Handler();
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action != null){
                if(action.equals(BluetoothDevice.ACTION_FOUND)){
                    try{
                        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                        deviceRViewAdapter.addBleDevice(device);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if(action.equals(BluetoothService.BT_CONNECTED)){
                    finish();
                }
            }

        }
    };

    private DeviceRViewAdapter deviceRViewAdapter;

    private volatile boolean isScanning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFinishOnTouchOutside(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        RecyclerView recyclerView = findViewById(R.id.recycler);
        deviceRViewAdapter = new DeviceRViewAdapter(this);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int windowsHeight = metrics.heightPixels;
        int widowsWidth = metrics.widthPixels;
        ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
        layoutParams.height = windowsHeight * 2 / 3;
        layoutParams.width = widowsWidth * 5 / 6;
        recyclerView.setLayoutParams(layoutParams);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(deviceRViewAdapter);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(mBluetoothAdapter != null){
            if(!mBluetoothAdapter.isEnabled()){
                mBluetoothAdapter.enable();
            }
            startScan();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    stopScan();
                }
            }, 12000);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcast();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public void finish() {
        super.finish();
        stopScan();
    }

    public void startScan(){
        if(mBluetoothAdapter != null){
            if(!isScanning){
                isScanning = true;
                mBluetoothAdapter.startDiscovery();
            }
        }
    }

    public void stopScan(){
        try{
            if(mBluetoothAdapter !=null){
                if(isScanning){
                    isScanning = false;
                    mBluetoothAdapter.cancelDiscovery();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void registerBroadcast(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothService.BT_CONNECTED);
        registerReceiver(mBroadcastReceiver, filter);
    }

}
