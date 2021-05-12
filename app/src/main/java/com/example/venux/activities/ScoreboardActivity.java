package com.example.venux.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.venux.R;
import com.example.venux.controllers.GameController;

public class ScoreboardActivity extends AppCompatActivity {

    TextView winnerTV;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        winnerTV = findViewById(R.id.winnerTV);
        winnerTV.setText(GameController.getCurrentPlayer().getName() + ": " + GameController.getCurrentPlayer().getScore());
    }
}
