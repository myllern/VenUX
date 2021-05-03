package com.example.venux.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.venux.Controller;
import com.example.venux.R;

import java.util.Timer;
import java.util.concurrent.TimeoutException;


public class PlayGameActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float[] mLastAccelerometer = new float[3];
    private View view;
    float xVal, yVal, zVal;
    static final float ALPHA = 0.25f;
    private TextView gameInstructionsTV, playerName;
    private Controller controller;
    private Button readyButton;
    static Timer timer;
    private Vibrator v;
    private boolean ready;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        view = findViewById(R.id.playGame_view);
        timer = new Timer();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        controller = new Controller();
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        readyButton = findViewById(R.id.activity_play_game_ready_button);
        gameInstructionsTV = findViewById(R.id.activity_play_game_game_info);
        playerName = findViewById(R.id.activity_play_game_player_name);
        ready = false;
        controller.resetGame();
    }




    public void readyClick(View view){
        //toDo some kind of countDown
        readyButton.setVisibility(View.INVISIBLE);
        gameInstructionsTV.setText("Create a MOVE");
        ready=true;
        //toDo startGame with threads
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(e.getActionMasked()==MotionEvent.ACTION_UP) {

            /*
             * ToDo everyting from here (Start)
             *  needs to be on the beat, help by a thread
             */
            if(controller.needToRecordNewMove()) {
                playerName.setTextColor(Color.parseColor("White"));
                controller.playNextRound(xVal, yVal, zVal);
                v.vibrate(50);
            }
            else{
                boolean playSuccess = controller.playNextRound(xVal, yVal, zVal);

                // instead of playerNameColour we should change background to player's colour
                String playerNameColour =  playSuccess ? "Green" : "Red";
                playerName.setTextColor(Color.parseColor(playerNameColour));


                if(playSuccess) v.vibrate(50);
                else v.vibrate(1000);
            }
            String instructionText = controller.needToRecordNewMove() ? "Create a Move" : "Kopy";
            gameInstructionsTV.setText(instructionText);

            //Todo until here (end)
        }

        return true;
    }


    private void changeText(){
        if(controller.isNextRoundRecorderRound()){

            v.vibrate(new long[]{50, 100, 50, 100, 50},-1); //

        }else {

            //v.vibrate(new long[]{50, 100, 50},-1);
        }
    }

    private void startClick(){

        if(controller.playNextRound(xVal,yVal,zVal)){

            v.vibrate(new long[]{50, 100, 50, 100, 50},-1); //

            view.setBackgroundColor(Color.rgb(191, 255,141)); // green

        }else {

            v.vibrate(2000);
            view.setBackgroundColor(Color.rgb(255, 74,74)); // reds

        }

    };


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mLastAccelerometer = lowPass(sensorEvent.values.clone(),  mLastAccelerometer);

            xVal = sensorEvent.values[0];
            yVal = sensorEvent.values[1];
            zVal = sensorEvent.values[2];
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