package com.example.venux.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.venux.R;


public class MainActivity extends AppCompatActivity {

    PlayerSetupActivity playerSetup;
    private Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_start = findViewById(R.id.main_start_btn);

        //Disable the go-back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onBackPressed() {
    }

    public void navToPlayerSetup(View view) {
        Intent playerSetup = new Intent(this, PlayerSetupActivity.class);
        startActivity(playerSetup);
    }


}