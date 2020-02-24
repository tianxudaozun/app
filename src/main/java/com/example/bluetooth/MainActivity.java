package com.example.bluetooth;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bluetooth.activity_left.Left_MachineActivity;
import com.example.bluetooth.activity_mid.Mid_MachineActivity;
import com.example.bluetooth.activity_right.Right_MachineActivity;
import com.example.bluetooth.activity_scan.DeviceListActivity;
import com.example.bluetooth.bluetoothService.BluetoothService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();


    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button left = findViewById(R.id.main_left_machine);
        left.setOnClickListener(this);

        Button right = findViewById(R.id.main_right_machine);
        right.setOnClickListener(this);

        Button mid = findViewById(R.id.main_mid_machine);
        mid.setOnClickListener(this);

        Button scan = findViewById(R.id.main_device_scan);
        scan.setOnClickListener(this);

        Button not_connected = findViewById(R.id.main_device_not_connected);
        not_connected.setOnClickListener(this);

        /**
         *  先动态获取位置信息
         */
        final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
        final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(this.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSION_ACCESS_COARSE_LOCATION);
            }
            if(this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSION_ACCESS_FINE_LOCATION);
            }
        }

        //如果打开本地蓝牙设备不成功，提示信息，结束程序
        if (_bluetooth == null){
            Toast.makeText(this, "无法打开手机蓝牙，请确认手机是否有蓝牙功能！",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        /**
         * 当应用打开时, 自动打开蓝牙
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(!_bluetooth.isEnabled()){
                    _bluetooth.enable();
                }
            }
        }).start();

        /**
         * 开启蓝牙服务
         */
        Intent intent = new Intent(this, BluetoothService.class);
        startService(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        try{
            System.gc();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try{
            System.gc();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            System.gc();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_device_scan:
                //如果没打开蓝牙, 则先打开蓝牙
                if(!_bluetooth.isEnabled()){
                    _bluetooth.enable();
                    Toast.makeText(this, " 打开蓝牙中...", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Intent intent = new Intent(this, DeviceListActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.main_left_machine:
                Intent intent = new Intent(this, Left_MachineActivity.class);
                startActivity(intent);
                break;
            case R.id.main_right_machine:
                intent = new Intent(this, Right_MachineActivity.class);
                startActivity(intent);
                break;
            case R.id.main_mid_machine:
                intent = new Intent(this, Mid_MachineActivity.class);
                startActivity(intent);
                break;

            case R.id.main_device_not_connected:
                intent = new Intent(BluetoothService.BT_CLOSE);
                sendBroadcast(intent);
                break;
            default:
                break;

        }
    }
}
