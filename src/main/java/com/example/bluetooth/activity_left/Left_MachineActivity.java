package com.example.bluetooth.activity_left;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluetooth.R;
import com.example.bluetooth.Util;
import com.example.bluetooth.bluetoothService.BluetoothService;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Left_MachineActivity extends AppCompatActivity implements View.OnTouchListener{

    /**
     * 根据收到的信号确认信号灯id
     */
    private final static Map<String, Integer> checkLight = new HashMap<>();

    /**
     * 开启子线程， 更新UI
     */
    private Handler handler = new Handler();

    /**
     * 广播接受器
     */
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {
            String action = intent.getAction();
            if(action != null){
                if(action.equals(BluetoothService.BT_RECEIVE_CMD)){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String cmd = intent.getStringExtra(BluetoothService.BT_RECEIVE_CMD);
                            Integer id = checkLight.get(cmd);
                            if(id != null){
                                switch (id){
                                    case R.id.left_Upper_limit_light:
                                        left_Upper_limit_light.setBackgroundResource(R.drawable.indicator_light_green);
                                        break;
                                    case R.id.left_lower_limit_light:
                                        left_lower_limit_light.setBackgroundResource(R.drawable.indicator_light_green);
                                        break;
                                    case R.id.left_no_out_light:
                                        left_no_out_light.setBackgroundResource(R.drawable.indicator_light_green);
                                        break;
                                    case R.id.left_Shipment_light:
                                        left_Shipment_light.setBackgroundResource(R.drawable.indicator_light_green);
                                        break;
                                    case R.id.left_reset_light:
                                        left_reset_light.setBackgroundResource(R.drawable.indicator_light_green);
                                        break;
                                    case R.id.left_open_dore_light:
                                        left_open_dore_light.setBackgroundResource(R.drawable.indicator_light_green);
                                        break;
                                    case R.id.left_close_dore_light:
                                        left_close_dore_light.setBackgroundResource(R.drawable.indicator_light_green);
                                        break;
                                    case R.id.left_horizontal_hr:
                                        left_horizontal_hr.setBackgroundResource(R.drawable.indicator_light_green);
                                        break;
                                    case R.id.left_vertical_hr:
                                        left_vertical_hr.setBackgroundResource(R.drawable.indicator_light_green);
                                        break;
                                    case R.id.left_huo_Shipment_hr:
                                        left_huo_Shipment_hr.setBackgroundResource(R.drawable.indicator_light_green);
                                        break;
                                    case R.id.left_Photoelectricity_vertical_light:
                                        left_Photoelectricity_vertical_light.setBackgroundResource(R.drawable.indicator_light_green);
                                        break;
                                    case R.id.left_Photoelectricity_Shipment_light:
                                        left_Photoelectricity_Shipment_light.setBackgroundResource(R.drawable.indicator_light_green);
                                        break;
                                    default: break;
                                }
                            }
                        }
                    });
                }
            }
        }
    };

    /**
     * 电机检测
     */
    private View left_open_right_indicator_light;  // 开门电机正向
    private View left_open_left_indicator_light;   // 开门电机反向

    private View left_push_right_indicator_light;  //推送正向
    private View left_push_left_indicator_light;   //推送反向

    private View left_Transportation_right_indicator_light;  // 输送正向
    private View left_Transportation_left_indicator_light;   // 输送反向

    private View left_translation_right_indicator_light;    // 平移正向
    private View left_translation_left_indicator_light;     // 平移反向

    private View left_Lifting_up_indicator_light;         // 升降正向
    private View left_Lifting_down_indicator_light;       // 升降反向

    /**
     * 信号检测
     */
    private View left_Upper_limit_light;  //上限
    private View left_lower_limit_light;  //下限
    private View left_no_out_light;       //非出
    private View left_Shipment_light;     //出货
    private View left_reset_light;        //复位
    private View left_open_dore_light;    //开门
    private View left_close_dore_light;   //关门
    private View left_horizontal_hr;      //霍尔 水平
    private View left_vertical_hr;        //霍尔 垂直
    private View left_huo_Shipment_hr;    //霍尔 出货
    private View left_Photoelectricity_vertical_light;   //光电 垂直
    private View left_Photoelectricity_Shipment_light;   //光电 出货


    static {
        checkLight.put("555A0182010100000000", R.id.left_Upper_limit_light);
        checkLight.put("555A0182020100000000", R.id.left_lower_limit_light);
        checkLight.put("555A0182030100000000", R.id.left_no_out_light);
        checkLight.put("555A0182040100000000", R.id.left_Shipment_light);
        checkLight.put("555A0182050100000000", R.id.left_reset_light);
        checkLight.put("555A0182060100000000", R.id.left_open_dore_light);
        checkLight.put("555A0182070100000000", R.id.left_close_dore_light);


        checkLight.put("555A0182080100000000", R.id.left_horizontal_hr);
        checkLight.put("555A0182090100000000", R.id.left_vertical_hr);
        checkLight.put("555A0182100100000000", R.id.left_huo_Shipment_hr);


        checkLight.put("555A0182110100000000", R.id.left_Photoelectricity_vertical_light);
        checkLight.put("555A0182120100000000", R.id.left_Photoelectricity_Shipment_light);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_machine);

        ImageView back = findViewById(R.id.back_left);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView textView = findViewById(R.id.title_left);
        ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
        layoutParams.width = Util.getScreenWidth(this) * 5 / 7;
        textView.setLayoutParams(layoutParams);


        Button left_open_right = findViewById(R.id.left_open_right);
        left_open_right_indicator_light = findViewById(R.id.left_open_right_indicator_light);
        left_open_right.setOnTouchListener(this);

        Button left_open_left = findViewById(R.id.left_open_left);
        left_open_left_indicator_light = findViewById(R.id.left_open_left_indicator_light);
        left_open_left.setOnTouchListener(this);

        Button left_push_right = findViewById(R.id.left_push_right);
        left_push_right_indicator_light = findViewById(R.id.left_push_right_indicator_light);
        left_push_right.setOnTouchListener(this);

        Button left_push_left = findViewById(R.id.left_push_left);
        left_push_left_indicator_light = findViewById(R.id.left_push_left_indicator_light);
        left_push_left.setOnTouchListener(this);

        Button left_Transportation_right = findViewById(R.id.left_Transportation_right);
        left_Transportation_right_indicator_light = findViewById(R.id.left_Transportation_right_indicator_light);
        left_Transportation_right.setOnTouchListener(this);

        Button left_Transportation_left = findViewById(R.id.left_Transportation_left);
        left_Transportation_left_indicator_light = findViewById(R.id.left_Transportation_left_indicator_light);
        left_Transportation_left.setOnTouchListener(this);

        Button left_translation_right = findViewById(R.id.left_translation_right);
        left_translation_right_indicator_light = findViewById(R.id.left_translation_right_indicator_light);
        left_translation_right.setOnTouchListener(this);

        Button left_translation_left = findViewById(R.id.left_translation_left);
        left_translation_left_indicator_light = findViewById(R.id.left_translation_left_indicator_light);
        left_translation_left.setOnTouchListener(this);

        Button left_Lifting_up = findViewById(R.id.left_Lifting_up);
        left_Lifting_up_indicator_light = findViewById(R.id.left_Lifting_up_indicator_light);
        left_Lifting_up.setOnTouchListener(this);

        Button left_Lifting_down = findViewById(R.id.left_Lifting_down);
        left_Lifting_down_indicator_light = findViewById(R.id.left_Lifting_down_indicator_light);
        left_Lifting_down.setOnTouchListener(this);

        /**
         * 信号灯检测初始化
         */

        left_Upper_limit_light = findViewById(R.id.left_Upper_limit_light);  //上限
        left_lower_limit_light = findViewById(R.id.left_lower_limit_light);  //下限
        left_no_out_light = findViewById(R.id.left_no_out_light);       //非出
        left_Shipment_light = findViewById(R.id.left_Shipment_light);     //出货
        left_reset_light = findViewById(R.id.left_reset_light);        //复位
        left_open_dore_light = findViewById(R.id.left_open_dore_light);    //开门
        left_close_dore_light = findViewById(R.id.left_close_dore_light);   //关门
        left_horizontal_hr = findViewById(R.id.left_horizontal_hr);      //霍尔 水平
        left_vertical_hr = findViewById(R.id.left_vertical_hr);        //霍尔 垂直
        left_huo_Shipment_hr = findViewById(R.id.left_huo_Shipment_hr);    //霍尔 出货
        left_Photoelectricity_vertical_light = findViewById(R.id.left_Photoelectricity_vertical_light);   //光电 垂直
        left_Photoelectricity_Shipment_light = findViewById(R.id.left_Photoelectricity_Shipment_light);   //光电 出货

        // 位置全检
        Button left_position_qj = findViewById(R.id.left_position_qj);
        left_position_qj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = "555A0161010000000000";
                sendCmd(cmd);
            }
        });

        //防冻测试
        Button left_Antifreeze_test = findViewById(R.id.left_Antifreeze_test);
        left_Antifreeze_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = "555A0161020000000000";
                sendCmd(cmd);
            }
        });

        //完成存储
        Button left_saved = findViewById(R.id.left_saved);
        left_saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = "555A0161030000000000";
                sendCmd(cmd);
            }
        });

        //位置定检
        Button left__position_fixed = findViewById(R.id.left__position_fixed);
        left__position_fixed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = "555A0161040000000000";
                sendCmd(cmd);
            }
        });

        // 位置定检 层数
        final EditText left_position_fixed_ceng_edit = findViewById(R.id.left_position_fixed_ceng_edit);
        Button left_position_fixed_ceng_edit_go = findViewById(R.id.left_position_fixed_ceng_edit_go);
        left_position_fixed_ceng_edit_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = left_position_fixed_ceng_edit.getText().toString().trim();
                int i = 0;
                try{
                    i = Integer.valueOf(msg);
                } catch (Exception e) {
                    Toast.makeText(Left_MachineActivity.this, "请输入0-9的个位数", Toast.LENGTH_LONG).show();
                }
                String cmd = "555A01610501" + "0" + i + "000000";
                sendCmd(cmd);
            }
        });

        //位置定检 列数
        final EditText left_position_fixed_lie_edit = findViewById(R.id.left_position_fixed_lie_edit);
        Button left_position_fixed_lie_edit_go = findViewById(R.id.left_position_fixed_lie_edit_go);
        left_position_fixed_lie_edit_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = left_position_fixed_lie_edit.getText().toString().trim();
                int i = 0;
                try{
                    i = Integer.valueOf(msg);
                } catch (Exception e) {
                    Toast.makeText(Left_MachineActivity.this, "请输入0-9的个位数", Toast.LENGTH_LONG).show();
                }
                String cmd = "555A01610502" + "0" + i + "000000";
                sendCmd(cmd);
            }
        });

        //出货定检
        Button left_Shipment_fixed = findViewById(R.id.left_Shipment_fixed);
        left_Shipment_fixed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = "555A0161060000000000";
                sendCmd(cmd);
            }
        });

        //出货定检 层数, 列数
        final EditText left_Shipment_fixed_ceng_edit = findViewById(R.id.left_Shipment_fixed_ceng_edit);
        final EditText left_Shipment_fixed_lie_edit = findViewById(R.id.left_Shipment_fixed_lie_edit);
        Button left_Shipment_fixed_ceng_lie_edit_go = findViewById(R.id.left_Shipment_fixed_ceng_lie_edit_go);
        left_Shipment_fixed_ceng_lie_edit_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg1 = left_Shipment_fixed_ceng_edit.getText().toString().trim();
                String msg2 = left_Shipment_fixed_lie_edit.getText().toString().trim();
                int i1 = 0;
                int i2 = 0;
                try{
                    i1 = Integer.valueOf(msg1);
                    i2 = Integer.valueOf(msg2);
                } catch (Exception e) {
                    Toast.makeText(Left_MachineActivity.this, "对层数, 列数编辑框分别请输入0-9的个位数", Toast.LENGTH_LONG).show();
                }
                String cmd = "555A01610701" + "0" + i1 + "0" + i2 + "0000";
                sendCmd(cmd);
            }
        });

        //步进检测
        Button left_stepping_testing = findViewById(R.id.left_stepping_testing);
        left_stepping_testing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = "555A0161080000000000";
                sendCmd(cmd);
            }
        });

        Button left_stepping_right = findViewById(R.id.left_stepping_right);
        left_stepping_right.setOnTouchListener(this);

        Button left_stepping_left = findViewById(R.id.left_stepping_left);
        left_stepping_left.setOnTouchListener(this);

        Button left_stepping_up = findViewById(R.id.left_stepping_up);
        left_stepping_up.setOnTouchListener(this);

        Button left_stepping_down = findViewById(R.id.left_stepping_down);
        left_stepping_down.setOnTouchListener(this);

        //系统重置
        Button left_os_reset = findViewById(R.id.left_os_reset);
        left_os_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = "555A0161090000000000";
                sendCmd(cmd);
            }
        });

        //紧急停止
        Button left_urgent_stop = findViewById(R.id.left_urgent_stop);
        left_urgent_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = "555A0161100000000000";
                sendCmd(cmd);
            }
        });



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

    @SuppressWarnings("all")
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.left_open_right:
                String cmd = "555A0181020100000000";
                longTouchSendCmd(cmd, event, v.getId());
                break;
            case R.id.left_open_left:
                cmd = "555A0181020100000000";
                longTouchSendCmd(cmd, event, v.getId());
                break;
            case R.id.left_push_right:
                cmd = "555A0181030100000000";
                longTouchSendCmd(cmd, event, v.getId());
                break;
            case R.id.left_push_left:
                cmd = "555A0181030100000000";
                longTouchSendCmd(cmd, event, v.getId());
                break;
            case R.id.left_Transportation_right:
                cmd = "555A0181010100000000";
                longTouchSendCmd(cmd, event, v.getId());
                break;
            case R.id.left_Transportation_left:
                cmd = "555A0181010100000000";
                longTouchSendCmd(cmd, event, v.getId());
                break;
            case R.id.left_translation_right:
                cmd = "555A0181050100000000";
                longTouchSendCmd(cmd, event, v.getId());
                break;
            case R.id.left_translation_left:
                cmd = "555A0181050100000000";
                longTouchSendCmd(cmd, event, v.getId());
                break;
            case R.id.left_Lifting_up:
                cmd = "555A0181040100000000";
                longTouchSendCmd(cmd, event, v.getId());
                break;
            case R.id.left_Lifting_down:
                cmd = "555A0181040100000000";
                longTouchSendCmd(cmd, event, v.getId());
                break;
            case R.id.left_stepping_right:
                cmd = "555A0161080100000000";
                longTouchSendCmd(cmd, event, v.getId());
                break;
            case R.id.left_stepping_left:
                cmd = "555A0161080200000000";
                longTouchSendCmd(cmd, event, v.getId());
                break;
            case R.id.left_stepping_up:
                cmd = "555A0161080300000000";
                longTouchSendCmd(cmd, event, v.getId());
                break;
            case R.id.left_stepping_down:
                cmd = "555A0161080400000000";
                longTouchSendCmd(cmd, event, v.getId());
                break;
        }
        return true;
    }

    private Long time_down = 0L;
    private Long time_up = 0L;
    private volatile boolean continuousClick = false;
    private final ThreadPoolExecutor service = new ThreadPoolExecutor(
            1,
            1,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());


    private void longTouchSendCmd(final String cmd, MotionEvent event, final int id){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            final long time_down_old = time_down;
            final long time_up_old = time_up;
            time_down = System.currentTimeMillis();
            time_up = 0L;
            if(time_up_old != 0){
                if((time_down - time_up_old) <= 250){
                    continuousClick = true;
                }
            }
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(250);
                        if(time_up == 0L && !continuousClick){
                            if((time_up_old - time_down_old) > 250){
                                startSendMessage(cmd);
                            } else if(time_down_old == 0){
                                startSendMessage(cmd);
                            } else {
                                if(service.getQueue().size() == 0){
                                    while (true){
                                        long timeTmp = System.currentTimeMillis();
                                        if((timeTmp - time_down) > 250){
                                            startSendMessage(cmd);
                                            break;
                                        }
                                        Thread.sleep(1);
                                    }
                                } else {
                                    sendCmd(cmd);
                                }
                            }

                        } else if(continuousClick){
                            continuousClick = false;
                            sendCmd(cmd);
                        }else if((time_up - time_down) <= 250){
                            sendCmd(cmd);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            handler.post(new Runnable() {
                @Override
                public void run() {
                    switch (id){
                        case R.id.left_open_right:
                            left_open_right_indicator_light.setBackgroundResource(R.drawable.indicator_light_green);
                            break;
                        case R.id.left_open_left:
                            left_open_left_indicator_light.setBackgroundResource(R.drawable.indicator_light_green);
                            break;
                        case R.id.left_push_right:
                            left_push_right_indicator_light.setBackgroundResource(R.drawable.indicator_light_green);
                            break;
                        case R.id.left_push_left:
                            left_push_left_indicator_light.setBackgroundResource(R.drawable.indicator_light_green);
                            break;
                        case R.id.left_Transportation_right:
                            left_Transportation_right_indicator_light.setBackgroundResource(R.drawable.indicator_light_green);
                            break;
                        case R.id.left_Transportation_left:
                            left_Transportation_left_indicator_light.setBackgroundResource(R.drawable.indicator_light_green);
                            break;
                        case R.id.left_translation_right:
                            left_translation_right_indicator_light.setBackgroundResource(R.drawable.indicator_light_green);
                            break;
                        case R.id.left_translation_left:
                            left_translation_left_indicator_light.setBackgroundResource(R.drawable.indicator_light_green);
                            break;
                        case R.id.left_Lifting_up:
                            left_Lifting_up_indicator_light.setBackgroundResource(R.drawable.indicator_light_green);
                            break;
                        case R.id.left_Lifting_down:
                            left_Lifting_down_indicator_light.setBackgroundResource(R.drawable.indicator_light_green);
                            break;
                    }
                }
            });

        } else if(event.getAction() == MotionEvent.ACTION_UP){
            time_up = System.currentTimeMillis();
            if((time_up - time_down) > 250){
                stopSendMessage();
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    switch (id){
                        case R.id.left_open_right:
                            left_open_right_indicator_light.setBackgroundResource(R.drawable.indicator_light_gray);
                            break;
                        case R.id.left_open_left:
                            left_open_left_indicator_light.setBackgroundResource(R.drawable.indicator_light_gray);
                            break;
                        case R.id.left_push_right:
                            left_push_right_indicator_light.setBackgroundResource(R.drawable.indicator_light_gray);
                            break;
                        case R.id.left_push_left:
                            left_push_left_indicator_light.setBackgroundResource(R.drawable.indicator_light_gray);
                            break;
                        case R.id.left_Transportation_right:
                            left_Transportation_right_indicator_light.setBackgroundResource(R.drawable.indicator_light_gray);
                            break;
                        case R.id.left_Transportation_left:
                            left_Transportation_left_indicator_light.setBackgroundResource(R.drawable.indicator_light_gray);
                            break;
                        case R.id.left_translation_right:
                            left_translation_right_indicator_light.setBackgroundResource(R.drawable.indicator_light_gray);
                            break;
                        case R.id.left_translation_left:
                            left_translation_left_indicator_light.setBackgroundResource(R.drawable.indicator_light_gray);
                            break;
                        case R.id.left_Lifting_up:
                            left_Lifting_up_indicator_light.setBackgroundResource(R.drawable.indicator_light_gray);
                            break;
                        case R.id.left_Lifting_down:
                            left_Lifting_down_indicator_light.setBackgroundResource(R.drawable.indicator_light_gray);
                            break;
                    }
                }
            });

        }
    }

    private ScheduledExecutorService scheduledExecutor;

    private void startSendMessage(final String msg) {
        scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                sendCmd(msg);
            }
        }, 0, 50, TimeUnit.MILLISECONDS);    //每间隔50ms发送Message
    }

    private void stopSendMessage() {
        if (scheduledExecutor != null) {
            scheduledExecutor.shutdownNow();
            scheduledExecutor = null;
            try{
                System.gc();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private Intent intent = new Intent(BluetoothService.BT_SEND);

    private void sendCmd(final String cmd){
        handler.post(new Runnable() {
            @Override
            public void run() {
                intent.putExtra(BluetoothService.BT_SEND, cmd);
                sendBroadcast(intent);
            }
        });
    }

    private void registerBroadcast(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothService.BT_RECEIVE_CMD);
        registerReceiver(mBroadcastReceiver, filter);
    }
}
