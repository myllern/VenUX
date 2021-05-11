package com.example.venux.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.venux.controllers.GameController;
import com.example.venux.util.CustomAdapter;
import com.example.venux.R;

public class PlayerSetupActivity extends AppCompatActivity {
    private GameController gameController;
    private RecyclerView playerRecyclerView;
    private CustomAdapter myAdapter;
    private EditText playerName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_setup);
        gameController = new GameController(this);
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
        /*
         *  Todo: send all the players into the game.
         *   Get players with
         *       myAdapter.getData();
         */
        Intent playGame = new Intent(this, PlayGameActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("playerNames", myAdapter.getData());
        playGame.putExtras(bundle);
        startActivity(playGame);




    };

    public void goToInstructions(View view){
        Intent intent = new Intent(this, InstructionsActivity.class);
        startActivity(intent);
    };


    public void addPlayer(View view){
        //Todo: randomize player colour
        String newPlayerName = this.playerName.getText().toString();
        if(newPlayerName.isEmpty()){
            newPlayerName = "Player " + (myAdapter.getItemCount()+1);
        }
        myAdapter.addNewData(newPlayerName);
        playerName.setText("");
    }

    //Todo: method to remove players.

    //Todo: method to choose player colour.

}