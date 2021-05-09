package com.example.venux.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
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

import com.example.venux.Controller;
import com.example.venux.Metronome;
import com.example.venux.R;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;


public class PlayGameActivity extends AppCompatActivity implements SensorEventListener, Observer {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float[] mLastAccelerometer = new float[3];
    private View view;
    float xVal, yVal, zVal;
    static final float ALPHA = 0.25f;
    private TextView gameInstructionsTV, playerName;
    private ConstraintLayout background;
    private Controller controller;
    private Button readyButton;
    static Timer timer;
    private Vibrator v;
    private boolean ready;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        view = findViewById(R.id.playGame_view);
        background = findViewById(R.id.playGame_view);
        timer = new Timer();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        Bundle bundle = getIntent().getExtras();
        controller = new Controller();
        controller.addPlayersToGame(bundle.getStringArrayList("playerNames"));
        controller.startGame();
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        readyButton = findViewById(R.id.activity_play_game_ready_button);
        gameInstructionsTV = findViewById(R.id.activity_play_game_game_info);
        playerName = findViewById(R.id.currentPlayerTv);
        ready = false;
        //ToDo: line below MIGHT cause trouble after multiple players are implemented. Check that.
        controller.resetGame(); //This line might cause trouble
    }


    public void readyClick(View view) {
        readyButton.setVisibility(View.INVISIBLE);
        ready = true;
        Runnable countDown = new com.example.venux.Timer(this);
        Thread countDownThread = new Thread(countDown);
        countDownThread.start();
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

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void update(Observable o, Object arg) {
        if(!ready) return;
        if (o instanceof com.example.venux.Timer) {
            int i = (Integer) arg;
            if (i == 0) {
                ((com.example.venux.Timer) o).stop(); //Stops the Timer from notifying
                Runnable metronome = new com.example.venux.Metronome(this);
                Thread gameThread = new Thread(metronome);
                gameThread.start();
            }
            else{
                /*
                 * ToDo: make text for countDown prettier, maybe big,
                 *  maybe two separate textViews. Artistic freedom Yes
                 */


                String textToSet = controller.needToRecordNewMove() ?
                        "Create move in " + String.valueOf(i) :
                        "Kopy " + controller.getMovesLeft() + " Moves in " + String.valueOf(i);
                gameInstructionsTV.setText(textToSet);
                v.vibrate(50);
            }
        } else if (o instanceof Metronome) {
            playGame((Metronome) o);
            if(controller.needToRecordNewMove())
            {
                /*
                 * ToDo: Set backgroundColour to currentPlayer's colour.
                 *  A few things in controller needs to be fixed for this, and also possibly in
                 *  Player Class (the colour thing right now is not really in work).
                 *  Also see to-do below
                 */
                ((Metronome) o).exit();
                setButtonVisible();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void playGame(Metronome metronome){
        /*
         * ToDo: Generally, maybe explore what kind of vibrations
         *  are suitable and then change them to that. Maybe create
         *  methods for good or bad vibrations and replace all
         *  vibrations in this activity with those methods.
         */
            background.setBackground(getDrawable(R.drawable.play_game_theme));
            if (controller.needToRecordNewMove()) { //This runs if we record a move
                playerName.setTextColor(Color.parseColor("White"));
                controller.playNextRound(xVal, yVal, zVal);
                v.vibrate(50);
                setButtonVisible(); //See code below for this method if you are wondering
                ready=false;
                metronome.exit(); //Exiting metronome mode if you have copied a move
                playerName.setText(controller.getCurrentPlayerName());

            } else { //This runs if we Kopy a MOVE
                boolean playSuccess = controller.playNextRound(xVal, yVal, zVal);
                String instructionText = controller.needToRecordNewMove() ? "Create Move" : "Kopy " + controller.getMovesLeft() + " Moves";
                gameInstructionsTV.setText(instructionText);
                /* ToDo:
                 *   Instead of playerNameColour below we should change background to green or
                 *   player's colour.
                 *   A few things in controller needs to be fixed for this, and also possibly in
                 *   Player Class (the colour thing right now is not really in work)
                 *   Also see to-do above
                 */
                if (playSuccess) {
                    onSucess();
                } else {
                    onFailure(metronome);
                }
            }


    }

    private void onSucess(){
        v.vibrate(50);
        playSuccessSound();
        gameInstructionsTV.setText("SUCCESS");
        background.setBackgroundColor(0xFF66BB6A);
    }

    private void onFailure(Metronome metronome){
        playerName.setText(controller.getCurrentPlayerName());
        ready=false;
        metronome.exit(); //Exiting the metronome mode if you die
        setButtonVisible(); //See code below for this method if you are wondering
        v.vibrate(1000);
        background.setBackgroundColor(0xFFEF5350);
        ready=false;
        setButtonVisible();
        gameInstructionsTV.setText("YOU ARE OUT");
        playFailSound();
    }

    //ToDo make a better(more correct) comment for setButtonVisible method.
    /**
     * This method sets the button to visible.
     * A Button is a View (I think), and Views can only
     * be touched on a special thread called UiThread. Idk why.
     * Therefore if we touch it in other threads, it crashes.
     * runOnUiThread runs this from the UiThread and therefore
     * it does not crash.
     *
     * Use this kind of thing if you need to touch other
     * View objects also.
     */
    private void setButtonVisible(){
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
    public void playSuccessSound(){
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
    public void playFailSound(){
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