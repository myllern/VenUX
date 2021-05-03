package com.example.venux;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    PlayerSetupActivity playerSetup;
    private Button btn_start;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_start = findViewById(R.id.main_start_btn);

        
    }



    public void navToPlayerSetup(View view) {
        Intent playerSetup = new Intent(this, PlayerSetupActivity.class);
        startActivity(playerSetup);
    }


}