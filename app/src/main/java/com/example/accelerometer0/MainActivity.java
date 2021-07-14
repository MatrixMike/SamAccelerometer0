package com.example.accelerometer0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.Log;
import android.hardware.SensorManager;

import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
int jumpCount=0;

    int Mode;
    final int xiuxianModeId=1;
    final int jingsuModeId=2;
    final int running=1;
    final int stopped=2;
    final int pause=3;
    int State=stopped;
    ////

    int count=0;
    int today;
    int goal;
    ///


    SensorManager sm;
    private static final int FORCE_THRESHOLD = 2000;
    private static final int TIME_THRESHOLD = 100;
    private static final int SHAKE_TIMEOUT = 500;
    private static final int SHAKE_DURATION = 350;
    private static final int SHAKE_COUNT = 2;
    private int mShakeCount = 0;
    private long mLastShake;
    private long mLastForce;
    private long mLastTime;
    private float mLastX=-1.0f, mLastY=-1.0f, mLastZ=-1.0f;
    private byte[]log=new byte[1024*1024];
    private  int index=0;
    private final static long DURATION_SHORT = 400;



    private boolean isOpen = false;///

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //////accelerometer sensor
        //Toast.makeText(getApplicationContext(),"JUMP",Toast.LENGTH_SHORT).show();

        ///
        sm=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        final Sensor acceleromererSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        final SensorEventListener acceleromererListener = new SensorEventListener() {
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                //什么也不干
            }

            //传感器数据变动事件
            @Override
            public void onSensorChanged(SensorEvent event) {

               // Toast.makeText(getApplicationContext(),"JUMP",Toast.LENGTH_SHORT).show();

                //获取加速度传感器的三个参数
                float x = event.values[SensorManager.DATA_X];
                float y = event.values[SensorManager.DATA_Y];
                float z = event.values[SensorManager.DATA_Z];


//                byte[]bx=fm.getBytes(x);
//                byte[]by=fm.getBytes(y);
//                byte[]bz=fm.getBytes(z);
//                for(int i=0;i<4;i++)
//                {
//                    log[index+i]=bx[i];
//                    log[index+4+i]=by[i];
//                    log[index+8+i]=bz[i];
//                }
//                index+=12;



                long now = System.currentTimeMillis();

                if ((now - mLastForce) > SHAKE_TIMEOUT) {
                    mShakeCount = 0;
                }

                if ((now - mLastTime) > TIME_THRESHOLD) {
                    long diff = now - mLastTime;
                    float speed = Math.abs(event.values[SensorManager.DATA_X] + event.values[SensorManager.DATA_Y] + event.values[SensorManager.DATA_Z] - mLastX - mLastY - mLastZ) / diff * 10000;
                    if (speed > FORCE_THRESHOLD) {
                        if ((++mShakeCount >= SHAKE_COUNT) && (now - mLastShake > SHAKE_DURATION)) {
                            mLastShake = now;
                            mShakeCount = 0;
                            if (true) {
                                jumpCount++;
                                //jumpSignal(400);
                                //Toast.makeText(getActivity(), "JUMP!!", Toast.LENGTH_SHORT).show();
                                Log.e("JUMP!!",String.valueOf(jumpCount));
                                Toast.makeText(getApplicationContext(),"JUMP : "+String.valueOf(jumpCount),Toast.LENGTH_SHORT).show();

                                //ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                                //toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,50);
                                //Toast.makeText,"Jump!!!",Toast.LENGTH_SHORT)
                            }
                        }
                        mLastForce = now;
                    }
                    mLastTime = now;
                    mLastX = event.values[SensorManager.DATA_X];
                    mLastY = event.values[SensorManager.DATA_Y];
                    mLastZ = event.values[SensorManager.DATA_Z];
                }
            }
        };
        sm.registerListener(acceleromererListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);


    }
}