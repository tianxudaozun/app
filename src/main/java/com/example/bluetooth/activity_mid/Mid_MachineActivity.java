package com.example.bluetooth.activity_mid;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Mid_MachineActivity extends AppCompatActivity implements View.OnTouchListener {

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
            if (action != null) {
                if (action.equals(BluetoothService.BT_RECEIVE_CMD)) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String cmd = intent.getStringExtra(BluetoothService.BT_RECEIVE_CMD);
                            Integer id = checkLight.get(cmd);
                            if(id != null){
                                switch (id) {
                                    case R.id.mid_Upper_limit_light:
                                        mid_Upper_limit_light.setBackgroundResource(R.drawable.indicator_light_green);
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mid_Upper_limit_light.setBackgroundResource(R.drawable.indicator_light_red);
                                            }
                                        }, 1000);
                                        break;
                                    case R.id.mid_platform_light:
                                        mid_platform_light.setBackgroundResource(R.drawable.indicator_light_green);
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mid_platform_light.setBackgroundResource(R.drawable.indicator_light_red);
                                            }
                                        }, 1000);
                                        break;
                                    case R.id.mid_lower_limit_light:
                                        mid_lower_limit_light.setBackgroundResource(R.drawable.indicator_light_green);
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mid_lower_limit_light.setBackgroundResource(R.drawable.indicator_light_red);
                                            }
                                        }, 1000);
                                        break;
                                    case R.id.mid_Pick_up_goods_open_light:
                                        mid_Pick_up_goods_open_light.setBackgroundResource(R.drawable.indicator_light_green);
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mid_Pick_up_goods_open_light.setBackgroundResource(R.drawable.indicator_light_red);
                                            }
                                        }, 1000);
                                        break;
                                    case R.id.mid_Pick_up_goods_close_light:
                                        mid_Pick_up_goods_close_light.setBackgroundResource(R.drawable.indicator_light_green);
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mid_Pick_up_goods_close_light.setBackgroundResource(R.drawable.indicator_light_red);
                                            }
                                        }, 1000);
                                        break;
                                    case R.id.mid_Drop_goods_ps1_light:
                                        mid_Drop_goods_ps1_light.setBackgroundResource(R.drawable.indicator_light_green);
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mid_Drop_goods_ps1_light.setBackgroundResource(R.drawable.indicator_light_red);
                                            }
                                        }, 1000);
                                        break;
                                    case R.id.mid_platform_ps2_light:
                                        mid_platform_ps2_light.setBackgroundResource(R.drawable.indicator_light_green);
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mid_platform_ps2_light.setBackgroundResource(R.drawable.indicator_light_red);
                                            }
                                        }, 1000);
                                        break;
                                    case R.id.mid_huo_Shipment_ps3_light:
                                        mid_huo_Shipment_ps3_light.setBackgroundResource(R.drawable.indicator_light_green);
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mid_huo_Shipment_ps3_light.setBackgroundResource(R.drawable.indicator_light_red);
                                            }
                                        }, 1000);
                                        break;
                                    case R.id.mid_open_up_hr_light:
                                        mid_open_up_hr_light.setBackgroundResource(R.drawable.indicator_light_green);
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mid_open_up_hr_light.setBackgroundResource(R.drawable.indicator_light_red);
                                            }
                                        }, 1000);
                                        break;
                                    case R.id.mid_close_down_hr_light:
                                        mid_close_down_hr_light.setBackgroundResource(R.drawable.indicator_light_green);
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mid_close_down_hr_light.setBackgroundResource(R.drawable.indicator_light_red);
                                            }
                                        }, 1000);
                                        break;
                                    default:
                                        break;
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
    private View mid_Pick_up_goods_Electric_machinery_up_indicator_light;     // 取货电机正向
    private View mid_Pick_up_goods_Electric_machinery_down_indicator_light;   // 取货电机反向

    private View mid_rotate_left_indicator_light;    // 旋转正向
    private View mid_rotate_right_indicator_light;   // 旋转反向

    private View mid_Open_up_left_indicator_light;   // 开腔正向
    private View mid_Open_up_right_indicator_light;  // 开腔反向

    private View mid_Lifting_up_indicator_light;     // 升降正向
    private View mid_Lifting_down_indicator_light;   // 升降反向


    /**
     * 信号检测
     */
    private View mid_Upper_limit_light;  //上限U
    private View mid_platform_light;     //平台P
    private View mid_lower_limit_light;  //下限D
    private View mid_Pick_up_goods_open_light;     //开门O
    private View mid_Pick_up_goods_close_light;    //关门C
    private View mid_Drop_goods_ps1_light;         //落货PS1
    private View mid_platform_ps2_light;           //平台PS2
    private View mid_huo_Shipment_ps3_light;      //出货PS3
    private View mid_open_up_hr_light;             //开腔O
    private View mid_close_down_hr_light;          //关腔C


    static {
        checkLight.put("555A0183010100000000", R.id.mid_Upper_limit_light);
        checkLight.put("555A0183020100000000", R.id.mid_platform_light);
        checkLight.put("555A0183030100000000", R.id.mid_lower_limit_light);
        checkLight.put("555A0183040100000000", R.id.mid_Pick_up_goods_open_light);
        checkLight.put("555A0183050100000000", R.id.mid_Pick_up_goods_close_light);

        checkLight.put("555A0183060100000000", R.id.mid_Drop_goods_ps1_light);
        checkLight.put("555A0183070100000000", R.id.mid_platform_ps2_light);
        checkLight.put("555A0183080100000000", R.id.mid_huo_Shipment_ps3_light);

        checkLight.put("555A0183090100000000", R.id.mid_open_up_hr_light);
        checkLight.put("555A0183100100000000", R.id.mid_close_down_hr_light);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mid_machine);

        ImageView back = findViewById(R.id.back_mid);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView textView = findViewById(R.id.title_mid);
        ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
        layoutParams.width = Util.getScreenWidth(this) * 5 / 7;
        textView.setLayoutParams(layoutParams);

        Button mid_Pick_up_goods_Electric_machinery_up = findViewById(R.id.mid_Pick_up_goods_Electric_machinery_up);
        mid_Pick_up_goods_Electric_machinery_up_indicator_light = findViewById(R.id.mid_Pick_up_goods_Electric_machinery_up_indicator_light);
        mid_Pick_up_goods_Electric_machinery_up.setOnTouchListener(this);

        Button mid_Pick_up_goods_Electric_machinery_down = findViewById(R.id.mid_Pick_up_goods_Electric_machinery_down);
        mid_Pick_up_goods_Electric_machinery_down_indicator_light = findViewById(R.id.mid_Pick_up_goods_Electric_machinery_down_indicator_light);
        mid_Pick_up_goods_Electric_machinery_down.setOnTouchListener(this);

        Button mid_rotate_left = findViewById(R.id.mid_rotate_left);
        mid_rotate_left_indicator_light = findViewById(R.id.mid_rotate_left_indicator_light);
        mid_rotate_left.setOnTouchListener(this);

        Button mid_rotate_right = findViewById(R.id.mid_rotate_right);
        mid_rotate_right_indicator_light = findViewById(R.id.mid_rotate_right_indicator_light);
        mid_rotate_right.setOnTouchListener(this);

        Button mid_Open_up_left = findViewById(R.id.mid_Open_up_left);
        mid_Open_up_left_indicator_light = findViewById(R.id.mid_Open_up_left_indicator_light);
        mid_Open_up_left.setOnTouchListener(this);

        Button mid_Open_up_right = findViewById(R.id.mid_Open_up_right);
        mid_Open_up_right_indicator_light = findViewById(R.id.mid_Open_up_right_indicator_light);
        mid_Open_up_right.setOnTouchListener(this);

        Button mid_Lifting_up = findViewById(R.id.mid_Lifting_up);
        mid_Lifting_up_indicator_light = findViewById(R.id.mid_Lifting_up_indicator_light);
        mid_Lifting_up.setOnTouchListener(this);

        Button mid_Lifting_down = findViewById(R.id.mid_Lifting_down);
        mid_Lifting_down_indicator_light = findViewById(R.id.mid_Lifting_down_indicator_light);
        mid_Lifting_down.setOnTouchListener(this);


        /**
         * 信号灯检测初始化
         */
        mid_Upper_limit_light = findViewById(R.id.mid_Upper_limit_light);  //上限U
        mid_platform_light = findViewById(R.id.mid_platform_light);     //平台P
        mid_lower_limit_light = findViewById(R.id.mid_lower_limit_light);  //下限D
        mid_Pick_up_goods_open_light = findViewById(R.id.mid_Pick_up_goods_open_light);     //开门O
        mid_Pick_up_goods_close_light = findViewById(R.id.mid_Pick_up_goods_close_light);    //关门C
        mid_Drop_goods_ps1_light = findViewById(R.id.mid_Drop_goods_ps1_light);         //落货PS1
        mid_platform_ps2_light = findViewById(R.id.mid_platform_ps2_light);           //平台PS2
        mid_huo_Shipment_ps3_light = findViewById(R.id.mid_huo_Shipment_ps3_light);      //出货PS3
        mid_open_up_hr_light = findViewById(R.id.mid_open_up_hr_light);             //开腔O
        mid_close_down_hr_light = findViewById(R.id.mid_close_down_hr_light);          //关腔C


        //位置定检
        Button mid_position_Fixed_inspection = findViewById(R.id.mid_position_Fixed_inspection);
        mid_position_Fixed_inspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = "555A0184010000000000";
                sendCmd(cmd);
            }
        });
        Button mid_superior = findViewById(R.id.mid_superior);
        mid_superior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = "555A0184010100000000";
                sendCmd(cmd);
            }
        });
        Button mid_platform_position_fixed = findViewById(R.id.mid_platform_position_fixed);
        mid_platform_position_fixed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = "555A0184010200000000";
                sendCmd(cmd);
            }
        });
        Button mid_down_position_fixed = findViewById(R.id.mid_down_position_fixed);
        mid_down_position_fixed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = "555A0184010300000000";
                sendCmd(cmd);
            }
        });

        //内腔检测
        Button mid_Intracavity_detection = findViewById(R.id.mid_Intracavity_detection);
        mid_Intracavity_detection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = "555A0184020000000000";
                sendCmd(cmd);
            }
        });
        Button mid_Intracavity_detection_open = findViewById(R.id.mid_Intracavity_detection_open);
        mid_Intracavity_detection_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = "555A0184020100000000";
                sendCmd(cmd);
            }
        });
        Button mid_Intracavity_detection_close = findViewById(R.id.mid_Intracavity_detection_close);
        mid_Intracavity_detection_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = "555A0184020200000000";
                sendCmd(cmd);
            }
        });

        //取货检测
        Button mid_pick_up_goods_detection = findViewById(R.id.mid_pick_up_goods_detection);
        mid_pick_up_goods_detection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = "555A0184030000000000";
                sendCmd(cmd);
            }
        });
        Button mid_pick_up_goods_detection_open = findViewById(R.id.mid_pick_up_goods_detection_open);
        mid_pick_up_goods_detection_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = "555A0184030100000000";
                sendCmd(cmd);
            }
        });
        Button mid_pick_up_goods_detection_close = findViewById(R.id.mid_pick_up_goods_detection_close);
        mid_pick_up_goods_detection_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = "555A0184030200000000";
                sendCmd(cmd);
            }
        });

        //光电检测
        Button mid_Photoelectricity_inspection = findViewById(R.id.mid_Photoelectricity_inspection);
        mid_Photoelectricity_inspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = "555A0184040000000000";
                sendCmd(cmd);
            }
        });
        Button mid_Photoelectricity_inspection_Drop_goods = findViewById(R.id.mid_Photoelectricity_inspection_Drop_goods);
        mid_Photoelectricity_inspection_Drop_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = "555A0184040100000000";
                sendCmd(cmd);
            }
        });
        Button mid_Photoelectricity_inspection_Shipment = findViewById(R.id.mid_Photoelectricity_inspection_Shipment);
        mid_Photoelectricity_inspection_Shipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = "555A0184040200000000";
                sendCmd(cmd);
            }
        });
        Button mid_Photoelectricity_inspection_platform = findViewById(R.id.mid_Photoelectricity_inspection_platform);
        mid_Photoelectricity_inspection_platform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = "555A0184040300000000";
                sendCmd(cmd);
            }
        });

        //微波测试
        Button mid_microwave_testing = findViewById(R.id.mid_microwave_testing);
        mid_microwave_testing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = "555A0184050000000000";
                sendCmd(cmd);
            }
        });

        final EditText mid_microwave_testing_m_edit = findViewById(R.id.mid_microwave_testing_m_edit);
        final EditText mid_microwave_testing_s_edit = findViewById(R.id.mid_microwave_testing_s_edit);
        Button mid_microwave_testing_go = findViewById(R.id.mid_microwave_testing_go);
        mid_microwave_testing_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg1 = mid_microwave_testing_m_edit.getText().toString().trim();
                String msg2 = mid_microwave_testing_s_edit.getText().toString().trim();
                int i1 = 0;
                int i2 = 0;
                try {
                    i1 = Integer.parseInt(msg1);
                    i2 = Integer.parseInt(msg2);
                } catch (Exception e) {
                    Toast.makeText(Mid_MachineActivity.this, "对分, 秒编辑框分别请输入0-9的个位数", Toast.LENGTH_LONG).show();
                }
                String cmd = "555A01840501" + "0" + i1 + "0" + i2 + "0000";
                sendCmd(cmd);
            }
        });

        //系统重置
        Button mid_os_reset = findViewById(R.id.mid_os_reset);
        mid_os_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = "555A0184070000000000";
                sendCmd(cmd);
            }
        });

        //紧急停止
        Button mid_urgent_stop = findViewById(R.id.mid_urgent_stop);
        mid_urgent_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = "555A0184080000000000";
                sendCmd(cmd);
            }
        });

        //蓝牙连线
        View mid_bt_conn = findViewById(R.id.mid_bt_conn);
        if(BluetoothService.connected){
            mid_bt_conn.setBackgroundResource(R.drawable.indicator_light_green);
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
            case R.id.mid_Pick_up_goods_Electric_machinery_up:
                String cmd = "555A0281020100000000";
                longTouchSendCmd(cmd, event, v.getId());
                break;
            case R.id.mid_Pick_up_goods_Electric_machinery_down:
                cmd = "555A0281020100000000";
                longTouchSendCmd(cmd, event, v.getId());
                break;
            case R.id.mid_rotate_left:
                cmd = "555A0281030100000000";
                longTouchSendCmd(cmd, event, v.getId());
                break;
            case R.id.mid_rotate_right:
                cmd = "555A0281030100000000";
                longTouchSendCmd(cmd, event, v.getId());
                break;
            case R.id.mid_Open_up_left:
                cmd = "555A0281010100000000";
                longTouchSendCmd(cmd, event, v.getId());
                break;
            case R.id.mid_Open_up_right:
                cmd = "555A0281010100000000";
                longTouchSendCmd(cmd, event, v.getId());
                break;
            case R.id.mid_Lifting_up:
                cmd = "555A0281040100000000";
                longTouchSendCmd(cmd, event, v.getId());
                break;
            case R.id.mid_Lifting_down:
                cmd = "555A0281040100000000";
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

    private void longTouchSendCmd(final String cmd, MotionEvent event, final int id) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
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
                    switch (id) {
                        case R.id.mid_Pick_up_goods_Electric_machinery_up:
                            mid_Pick_up_goods_Electric_machinery_up_indicator_light.setBackgroundResource(R.drawable.indicator_light_green);
                            break;
                        case R.id.mid_Pick_up_goods_Electric_machinery_down:
                            mid_Pick_up_goods_Electric_machinery_down_indicator_light.setBackgroundResource(R.drawable.indicator_light_green);
                            break;
                        case R.id.mid_rotate_left:
                            mid_rotate_left_indicator_light.setBackgroundResource(R.drawable.indicator_light_green);
                            break;
                        case R.id.mid_rotate_right:
                            mid_rotate_right_indicator_light.setBackgroundResource(R.drawable.indicator_light_green);
                            break;
                        case R.id.mid_Open_up_left:
                            mid_Open_up_left_indicator_light.setBackgroundResource(R.drawable.indicator_light_green);
                            break;
                        case R.id.mid_Open_up_right:
                            mid_Open_up_right_indicator_light.setBackgroundResource(R.drawable.indicator_light_green);
                            break;
                        case R.id.mid_Lifting_up:
                            mid_Lifting_up_indicator_light.setBackgroundResource(R.drawable.indicator_light_green);
                            break;
                        case R.id.mid_Lifting_down:
                            mid_Lifting_down_indicator_light.setBackgroundResource(R.drawable.indicator_light_green);
                            break;
                    }
                }
            });

        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            time_up = System.currentTimeMillis();
            if ((time_up - time_down) > 250) {
                stopSendMessage();
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    switch (id) {
                        case R.id.mid_Pick_up_goods_Electric_machinery_up:
                            mid_Pick_up_goods_Electric_machinery_up_indicator_light.setBackgroundResource(R.drawable.indicator_light_gray);
                            break;
                        case R.id.mid_Pick_up_goods_Electric_machinery_down:
                            mid_Pick_up_goods_Electric_machinery_down_indicator_light.setBackgroundResource(R.drawable.indicator_light_gray);
                            break;
                        case R.id.mid_rotate_left:
                            mid_rotate_left_indicator_light.setBackgroundResource(R.drawable.indicator_light_gray);
                            break;
                        case R.id.mid_rotate_right:
                            mid_rotate_right_indicator_light.setBackgroundResource(R.drawable.indicator_light_gray);
                            break;
                        case R.id.mid_Open_up_left:
                            mid_Open_up_left_indicator_light.setBackgroundResource(R.drawable.indicator_light_gray);
                            break;
                        case R.id.mid_Open_up_right:
                            mid_Open_up_right_indicator_light.setBackgroundResource(R.drawable.indicator_light_gray);
                            break;
                        case R.id.mid_Lifting_up:
                            mid_Lifting_up_indicator_light.setBackgroundResource(R.drawable.indicator_light_gray);
                            break;
                        case R.id.mid_Lifting_down:
                            mid_Lifting_down_indicator_light.setBackgroundResource(R.drawable.indicator_light_gray);
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

    private void sendCmd(final String cmd) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                intent.putExtra(BluetoothService.BT_SEND, cmd);
                sendBroadcast(intent);
            }
        });
    }

    private void registerBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothService.BT_RECEIVE_CMD);
        registerReceiver(mBroadcastReceiver, filter);
    }
}
