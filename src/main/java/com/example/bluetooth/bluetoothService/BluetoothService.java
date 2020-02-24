package com.example.bluetooth.bluetoothService;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class BluetoothService extends Service {
    private static final String TAG = "com.example.bluetooth.bluetoothService.BluetoothService.";
    public static final String BT_CONNECT = TAG +"bt.connect";
    public static final String BT_SEND = TAG + "bt.send";
    public static final String BT_CLOSE = TAG + "bt.close";
    public static final String BT_CONNECTED = TAG + "bt.connected";
    public static final String BT_RECEIVE_CMD = TAG + "bt.receiveDataTask.cmd";

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action != null){
                if(action.equals(BT_CONNECT)){
                    String mac = intent.getStringExtra(BT_CONNECT);
                    connect(mac);
                }
                if(action.equals(BT_SEND)){
                    String msg = intent.getStringExtra(BT_SEND);
                    System.out.println("htr: msg = " + msg);
                    try {
                        os.write(hex2byte(msg.getBytes()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(action.equals(BT_CLOSE)){
                    if(is != null && os != null && socket != null) {
                        try {
                            receiveDataTask.setEnd(true);
                            receiveDataTask.init();
                            is.close();
                            os.close();
                            socket.close();
                            is = null;
                            os = null;
                            socket = null;
                            device = null;
                            connected = false;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    };

    private final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";
    private final static String BT_UUID = "00001101-0000-1000-8000-00805F9B34FB";

    /**
     * 接受蓝牙数据
     */
    private InputStream is;

    /**
     * 发送蓝牙数据
     */
    private OutputStream os;

    /**
     * 蓝牙通信socket
     */
    private BluetoothSocket socket;

    /**
     * 通信蓝牙设备
     */
    private BluetoothDevice device;

    /**
     * 获取本地蓝牙适配器
     */
    private final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

    private volatile boolean isConnecting = false;

    public static boolean connected = false;

    public BluetoothService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerBroadcast();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
        if(is != null && os != null && socket != null) {
            try {
                receiveDataTask.setEnd(true);
                receiveDataTask.init();
                is.close();
                os.close();
                socket.close();
                is = null;
                os = null;
                socket = null;
                device = null;
                connected = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void registerBroadcast(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(BT_CONNECT);
        filter.addAction(BT_SEND);
        filter.addAction(BT_CLOSE);
        registerReceiver(mBroadcastReceiver, filter);
    }

    private void connect(final String address){
        if(!isConnecting){
            isConnecting = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if(is != null && os != null && socket != null) {
                        try {
                            receiveDataTask.setEnd(true);
                            receiveDataTask.init();
                            is.close();
                            os.close();
                            socket.close();
                            is = null;
                            os = null;
                            socket = null;
                            device = null;
                            connected = false;
                            Thread.sleep(333);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    device = adapter.getRemoteDevice(address);
                    if(device == null) return;
                    try {
                        socket = device.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
                    } catch (IOException e) {
                        e.printStackTrace();
                        Method m;
                        try {
                            m = device.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                        } catch (NoSuchMethodException ex) {
                            isConnecting = false;
                            ex.printStackTrace();
                            return;
                        }
                        try{
                            socket = (BluetoothSocket) Objects.requireNonNull(m).invoke(device, 1);
                        }catch (IllegalAccessException | InvocationTargetException ei) {
                            isConnecting = false;
                            ei.printStackTrace();
                            return;
                        }
                    }
                    try {
                        socket.connect();
                        os = socket.getOutputStream();
                        is = socket.getInputStream();
                        connected = true;
                        Intent intent = new Intent(BT_CONNECTED);
                        sendBroadcast(intent);
                        receiveDataTask.init();
                        receiveDataTask.setEnd(false);
                        Thread thread = new Thread(receiveDataTask);
                        thread.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        isConnecting = false;
                    }
                }
            }).start();
        }
    }

    public byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0) {
            throw new IllegalArgumentException("长度不是偶数");
        }
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    public String byte2hex(byte[] bytes) {
        if(bytes == null || bytes.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for(byte b : bytes) {
            if(b >= 0) {
                String str = Integer.toHexString(b).toUpperCase();
                if(b <= 15) {
                    str = "0" + str;
                }
                builder.append(str);
            } else {
                int tmp = 256 + b;
                String str = Integer.toHexString(tmp).toUpperCase();
                if(tmp <= 15) {
                    str = "0" + str;
                }
                builder.append(str);
            }
        }
        return builder.toString();
    }

    private Intent intent = new Intent(BT_RECEIVE_CMD);

    private void sendMsg(String msg){
        intent.putExtra(BT_RECEIVE_CMD, msg);
        sendBroadcast(intent);
    }
    private final ReceiveDataTask receiveDataTask = new ReceiveDataTask();

    private class ReceiveDataTask implements Runnable{
        int num = 0;
        int destPos = 0;
        byte[] receive = new byte[256];
        byte[] buf = new byte[10];
        volatile boolean end = true;

        ReceiveDataTask(){
            init();
        }

        @Override
        public void run() {
            while (true){
                try{
                    if(end){
                        init();
                        break;
                    }

                    num = is.read(receive);

                   if(num + destPos == 10){
                       System.arraycopy(receive, 0, buf, destPos, 10 - destPos);
                       // buf 满 处理data
                       String msg = byte2hex(buf);
                       sendMsg(msg);
                       //清理缓存
                       Arrays.fill(buf, (byte) 0);
                       destPos = 0;
                   } else if(num + destPos < 10){
                       System.arraycopy(receive, 0, buf, destPos, num);
                       destPos += num;
                   } else if(num + destPos > 10){
                       System.arraycopy(receive, 0, buf, destPos, 10 - destPos);
                       // buf 满 处理data
                       String msg = byte2hex(buf);
                       sendMsg(msg);
                       Arrays.fill(buf, (byte) 0);
                       System.arraycopy(receive, 10 - destPos, buf, 0, num + destPos - 10);
                       destPos = num + destPos - 10;
                   }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        void init(){
            num = 0;
            destPos = 0;
            end = true;
            Arrays.fill(receive, (byte) 0);
            Arrays.fill(buf, (byte) 0);
        }

        void setEnd(boolean end) {
            this.end = end;
        }
    }
}
