package com.example.venux.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.venux.R;

public class GameFlowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_flow);
    }

    public void navToNextInstruction(View view) {
        Intent intent = new Intent(this, GamePositionsActivity.class);
        startActivity(intent);
    }

}
