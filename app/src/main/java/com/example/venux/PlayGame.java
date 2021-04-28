package com.example.venux;

import androidx.appcompat.app.AppCompatActivity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class PlayGame extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float[] mLastAccelerometer = new float[3];
    float xVal, yVal, zVal;
    static final float ALPHA = 0.25f;
    private TextView X,Y,Z,toDo;
    private Controller controller;
    private Button btn_start;
    private Button btn_rec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        X =  findViewById(R.id.playGame_TW_x);
        Y =  findViewById(R.id.playGame_TW_y);
        Z =  findViewById(R.id.playGame_TW_z);
        toDo = findViewById(R.id.playGame_TW_toDo);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        controller = new Controller();

        btn_start = findViewById(R.id.playGame_btn_start);
        btn_rec = findViewById(R.id.playGame_btn_rec);

        btn_start.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v) {
                startClick();
            }
        });

        btn_rec.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });

    }


    private void startClick(){
        if(controller.needToRecordNewMove()){
            toDo.setText("Rec new move!");
        }else {
            toDo.setText("Kopy the move!");
        }
        //toDo gör en Timeout
        controller.playNextRound(xVal,yVal,zVal);

    };











    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mLastAccelerometer = lowPass(sensorEvent.values.clone(),  mLastAccelerometer);

            xVal = sensorEvent.values[0];
            yVal = sensorEvent.values[1];
            zVal = sensorEvent.values[2];

            X.setText("X: " + Float.toString(xVal));
            Y.setText("Y: " + Float.toString(yVal));
            Z.setText("Z: " + Float.toString(zVal));
        }
    }
    protected float[] lowPass( float[] input, float[] output ) {
        if ( output == null ) return input;
        for ( int i=0; i<input.length; i++ ) {
            output[i] = output[i] + ALPHA * (input[i] - output[i]);
        }
        return output;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}