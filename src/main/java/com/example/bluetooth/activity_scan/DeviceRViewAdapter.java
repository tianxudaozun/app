package com.example.bluetooth.activity_scan;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bluetooth.R;
import com.example.bluetooth.bluetoothService.BluetoothService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeviceRViewAdapter extends
        RecyclerView.Adapter<DeviceRViewAdapter.BluetoothViewHolder>{

    private List<BluetoothDevice> deviceList;
    private DeviceListActivity context;
    private Set<String> address;

    DeviceRViewAdapter(Context context) {
        this.context = (DeviceListActivity) context;
        this.deviceList = new ArrayList<>();
        this.address = new HashSet<>();
    }

    @NonNull
    @Override
    public BluetoothViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.device_list,
                parent, false);
        return new BluetoothViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final BluetoothViewHolder holder,
                                 @SuppressLint("RecyclerView") final int position) {
        final String mac = deviceList.get(holder.getAdapterPosition()).getAddress().trim();
        holder.bleDeviceMac.setText(mac);
        String name = deviceList.get(holder.getAdapterPosition()).getName();
        if(name == null || name.length() == 0){
            name = "未知设备";
        }
        holder.bleDeviceName.setText(name);
        holder.imageView.setImageResource(R.drawable.blue_ic_1);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.stopScan();
                Intent intent = new Intent(BluetoothService.BT_CONNECT);
                intent.putExtra(BluetoothService.BT_CONNECT, mac);
                context.sendBroadcast(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final BluetoothViewHolder holder,
                                 @SuppressLint("RecyclerView") final int position,
                                 @NonNull List<Object> payloads) {
        if(payloads.isEmpty()){
            onBindViewHolder(holder, position);
        } else {
            final String mac = deviceList.get(holder.getAdapterPosition()).getAddress().trim();
            holder.bleDeviceMac.setText(mac);
            String name = deviceList.get(holder.getAdapterPosition()).getName();
            if(name == null || name.length() == 0){
                name = "未知设备";
            }
            holder.bleDeviceName.setText(name);
            holder.imageView.setImageResource(R.drawable.blue_ic_1);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.stopScan();
                    Intent intent = new Intent(BluetoothService.BT_CONNECT);
                    intent.putExtra(BluetoothService.BT_CONNECT, mac);
                    context.sendBroadcast(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return deviceList == null ? 0 : deviceList.size();
    }

    /**
     * 增加设备
     * @param device 设备信息
     */
    void addBleDevice(BluetoothDevice device) {
        if((address.size() == 0) || (!address.contains(device.getAddress()))){
            deviceList.add(device);
            address.add(device.getAddress());
            notifyItemInserted(deviceList.size() - 1);
        }
    }


    class BluetoothViewHolder extends RecyclerView.ViewHolder {
        TextView bleDeviceName;
        TextView bleDeviceMac;
        ImageView imageView;

        BluetoothViewHolder(View itemView) {
            super(itemView);
            bleDeviceName = itemView.findViewById(R.id.BleDeviceName);
            bleDeviceMac = itemView.findViewById(R.id.BleDeviceMacAddress);
            imageView = itemView.findViewById(R.id.image_blue_re);
        }
    }
}
