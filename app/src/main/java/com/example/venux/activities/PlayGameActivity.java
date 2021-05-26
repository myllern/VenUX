package com.example.venux.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
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
    private TextView gameInstructionsTV, currentPlayerTV;
    private ConstraintLayout background;
    private Button readyButton;
    private Vibrator v;
    private MediaPlayer mediaPlayer_1;
    private MediaPlayer mediaPlayer_2;
    private MediaPlayer mediaPlayer_3;

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
        currentPlayerTV = findViewById(R.id.currentPlayerTv);
        currentPlayerTV.setText(GameController.getCurrentPlayer().getName());

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
        Runnable countDownTimer;
        if(GameController.isCreateMoveState()) {
            System.out.println("Starting long timer");
            countDownTimer = new CountdownTimer(this, 1000, 3);
        } else {
            System.out.println("Starting short timer.");
            countDownTimer = new CountdownTimer(this, 750, 3);
        }
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
    public void onBackPressed() {
        // Do Nothing as of now.
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof CountdownTimer) {
            background.setBackground(getDrawable(R.drawable.play_game_theme));
            if (GameController.isCreateMoveState()) {
                //Create Move State
                int ticksLeft = (Integer) arg;
                if (ticksLeft > 0) {
                    //Countdown for create move, not done
                    v.vibrate(25);
                    gameInstructionsTV.setText("Record move in " + ticksLeft);
                } else {
                    //Countdown for create move, done
                    boolean couldCreateMove = bumpNextStep();
                    if(couldCreateMove){
                        gameInstructionsTV.setText("Successfully recorded");
                        playSuccessSound();
                        v.vibrate(500);
                    }
                    else{
                        gameInstructionsTV.setText("You can only record allowed moves. Try again!");
                        v.vibrate(1000);
                    }



                    //Start countdown to copy
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(couldCreateMove){
                        gameInstructionsTV.setText("Are you ready to kopy " + GameController.getAmountOfMoves() + " moves?");
                    }
                    else{
                        gameInstructionsTV.setText("Record again");
                    }
                    currentPlayerTV.setText(GameController.getCurrentPlayer().getName());
                    setButtonVisible();
                }
            } else if (GameController.isLastMove()) {
                //Copy Move but last move
                int ticksLeft = (Integer) arg;
                if (ticksLeft > 0) {
                    v.vibrate(25);
                } else {
                    if (bumpNextStep()) {
                        onLastSuccess();
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
                    if (bumpNextStep()) {
                        onSuccess();
                        t1 = new Thread(new CountdownTimer(this, 750, 3));
                        t1.start();
                    } else {
                        onFailure();
                    }
                }
            }
        }
    }

    private boolean bumpNextStep() {
        Move move = new Move(xVal, yVal, zVal);
        return GameController.playNextStep(move);

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void onFailure() {
        //playerName.setText(gameController.getCurrentPlayerName());
        v.vibrate(1000);
        background.setBackgroundColor(0xFFEF5350);
        gameInstructionsTV.setText("YOU ARE OUT");
        playFailSound();
        try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }
        if(GameController.isGameFinished()) {
            Intent intent = new Intent(this, ScoreboardActivity.class);
            startActivity(intent);
        } else {
            background.setBackground(getDrawable(R.drawable.play_game_theme));
            currentPlayerTV.setText(GameController.getCurrentPlayer().getName());
            gameInstructionsTV.setText("Are you ready to kopy " + GameController.getAmountOfMoves() + " moves?");
            setButtonVisible();
        }
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
        mediaPlayer_1 = mediaPlayer_1.create(this, R.raw.correct_move_1);

        mediaPlayer_1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer_1.start();
            }
        });
        mediaPlayer_1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer_1.release();
            }
        });

    }

    /**
     * Plays a sound for a successful/correct move.
     */
    public void playFailSound() {
        mediaPlayer_2 = mediaPlayer_2.create(this, R.raw.failed_move_1);

        mediaPlayer_2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer_2.start();
            }
        });
        mediaPlayer_2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer_2.release();
            }
        });

    }

    /**
     * Plays a sound on countdown.
     */
    public void playCountdownSound() {
        //mediaPlayer_3 = mediaPlayer_3.create(this,);

        mediaPlayer_3.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer_3.start();
            }
        });
        mediaPlayer_3.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer_3.release();
            }
        });

    }
}