package com.example.venux.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.venux.controllers.GameController;
import com.example.venux.model.Move;
import com.example.venux.util.CountdownTimer;
import com.example.venux.R;

import java.util.Observable;
import java.util.Observer;


public class PlayGameActivity extends AppCompatActivity implements SensorEventListener, Observer {


    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float[] mLastAccelerometer = new float[3];
    private View view;
    float xVal, yVal, zVal;
    static final float ALPHA = 0.25f;
    private TextView gameInstructionsTV, playerName;
    private ConstraintLayout background;
    private Button readyButton;
    private Vibrator v;
    private MediaPlayer mediaPlayer;

    Thread t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //View stuff
        setContentView(R.layout.activity_play_game);
        view = findViewById(R.id.playGame_view);
        background = findViewById(R.id.playGame_view);
        readyButton = findViewById(R.id.activity_play_game_ready_button);
        gameInstructionsTV = findViewById(R.id.activity_play_game_game_info);
        playerName = findViewById(R.id.currentPlayerTv);
        readyButton = findViewById(R.id.activity_play_game_ready_button);
        gameInstructionsTV = findViewById(R.id.activity_play_game_game_info);
        playerName = findViewById(R.id.currentPlayerTv);

        //Sensors and vibration motor.
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    /**
     * Ready clicks changes player state to ready.
     *
     * @param view
     */
    public void readyClick(View view) {
        //TODO: Start countdown timer
        readyButton.setVisibility(View.INVISIBLE);
        Runnable countDownTimer = new CountdownTimer(this, 1000, 3);
        t1 = new Thread(countDownTimer);
        t1.start();
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mLastAccelerometer = lowPass(sensorEvent.values.clone(), mLastAccelerometer);

            xVal = sensorEvent.values[0];
            yVal = sensorEvent.values[1];
            zVal = sensorEvent.values[2];
        }
    }

    protected float[] lowPass(float[] input, float[] output) {
        if (output == null) return input;
        for (int i = 0; i < input.length; i++) {
            output[i] = output[i] + ALPHA * (input[i] - output[i]);
        }
        return output;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //Unused
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof CountdownTimer) {
            if (GameController.isCreateMoveState()) {
                //Create Move State
                int ticksLeft = (Integer) arg;
                if (ticksLeft > 0) {
                    //Countdown for create move, not done
                    gameInstructionsTV.setText("Record move in " + ticksLeft);
                } else {
                    //Countdown for create move, done
                    bumpNextStep();
                    gameInstructionsTV.setText("Successfully recorded");
                    playSuccessSound();
                    v.vibrate(500);
                    //Start countdown to copy
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    gameInstructionsTV.setText("Are you ready?");
                    setButtonVisible();
                }
            } else if (GameController.isLastMove()) {
                //Copy Move but last move
                int ticksLeft = (Integer) arg;
                if (ticksLeft > 0) {
                    v.vibrate(25);
                } else {
                    if(bumpNextStep()) {
                        onLastSuccess();
                        t1 = new Thread(new CountdownTimer(this, 1000, 3));
                    } else {
                        onFailure();
                    }
                }
            } else {
                //Copy Move State
                int ticksLeft = (Integer) arg;
                if (ticksLeft > 0) {
                    v.vibrate(25);
                } else {
                    if(bumpNextStep()) {
                        onSuccess();
                        t1 = new Thread(new CountdownTimer(this, 1000, 3));
                    } else {
                        onFailure();
                    }
                }
            }
        }
    }

    private boolean bumpNextStep() {
        Move move = new Move(xVal, yVal, zVal);
        if(!GameController.playNextStep(move)) {
            return false;
        } else {
            return true;
        }
    }

    private void onLastSuccess() {
        setButtonVisible();
        v.vibrate(50);
        playSuccessSound();
        gameInstructionsTV.setText("Great! Ready to record move?");
        background.setBackgroundColor(0xFF66BB6A);
    }

    private void onSuccess() {
        v.vibrate(50);
        playSuccessSound();
        gameInstructionsTV.setText(GameController.getCurrentMove() + "/" + GameController.getAmountOfMoves());
        background.setBackgroundColor(0xFF66BB6A);
    }

    private void onFailure() {
        //playerName.setText(gameController.getCurrentPlayerName());
        setButtonVisible(); //See code below for this method if you are wondering
        v.vibrate(1000);
        background.setBackgroundColor(0xFFEF5350);
        setButtonVisible();
        gameInstructionsTV.setText("YOU ARE OUT");
        playFailSound();
    }

    private void setButtonVisible() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                readyButton.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Plays a sound for a successful/correct move.
     */
    public void playSuccessSound() {
        mediaPlayer = mediaPlayer.create(this, R.raw.correct_move_1);

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
            }
        });

    }

    /**
     * Plays a sound for a successful/correct move.
     */
    public void playFailSound() {
        mediaPlayer = mediaPlayer.create(this, R.raw.failed_move_1);

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
            }
        });

    }
}