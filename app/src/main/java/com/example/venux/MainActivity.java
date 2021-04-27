package com.example.venux;

import androidx.appcompat.app.AppCompatActivity;
import com.example.venux.Controller.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    PlayerSetup playerSetup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Testish

    }

    public void navToPlayerSetup(View view) {
        Intent playerSetup = new Intent(this, PlayerSetup.class);
        startActivity(playerSetup);
    }
}