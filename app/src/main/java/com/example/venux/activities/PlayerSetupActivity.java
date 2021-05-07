package com.example.venux.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.venux.Controller;
import com.example.venux.CustomAdapter;
import com.example.venux.R;

public class PlayerSetupActivity extends AppCompatActivity {
    private Controller controller;
    private RecyclerView playerRecyclerView;
    private CustomAdapter myAdapter;
    private EditText playerName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_setup);
        controller = new Controller(this);
        playerRecyclerView = findViewById(R.id.PlayerSetupRecyclerView);
        myAdapter = new CustomAdapter();
        playerRecyclerView.setAdapter(myAdapter);
        playerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        playerName = findViewById(R.id.playerSetup_addPlayerField);
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
        myAdapter.addNewData(playerName.getText().toString());
        playerName.setText("");
    }

}