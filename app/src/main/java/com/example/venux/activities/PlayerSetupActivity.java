package com.example.venux.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.venux.Controller;
import com.example.venux.R;

public class PlayerSetupActivity extends AppCompatActivity {
    private Controller controller;
    private TextView [] namesTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_setup);
        controller = new Controller(this);
        controller.db.resetTable();
        namesTV = new TextView[6];

        //   namesTV [0] = findViewById(R.id.playerSetup_player_1_nameTV);
    }





    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }



    public void startGame(View view){
        Intent playGame = new Intent(this, PlayGameActivity.class);
        startActivity(playGame);




    };

    public void goToInstructions(View view){
        Intent intent = new Intent(this, InstructionsActivity.class);
        startActivity(intent);
    };


    public void addPlayer(View view){

        TextView playerName = new TextView(this);
        playerName.setText("Heheh");

        LinearLayout layout = (LinearLayout)findViewById(R.id.LinearLayout);
        layout.addView(playerName);






    }

}