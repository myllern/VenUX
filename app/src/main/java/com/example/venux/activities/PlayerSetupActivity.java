package com.example.venux.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.venux.controllers.GameController;
import com.example.venux.model.Player;
import com.example.venux.util.CustomAdapter;
import com.example.venux.R;

import java.util.ArrayList;

public class PlayerSetupActivity extends AppCompatActivity {
    private RecyclerView playerRecyclerView;
    private CustomAdapter myAdapter;
    private EditText playerName;
    private ArrayList<Player> playerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playerList = new ArrayList<Player>();

        setContentView(R.layout.activity_player_setup);
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
        //Check whether 2 or more players are added.
        if(playerList.size() < 2) {
            Context context = getApplicationContext();
            CharSequence text = "You need to play with at least 2 players!";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        GameController.createNewGame(playerList);
        Intent playGame = new Intent(this, PlayGameActivity.class);
        startActivity(playGame);
    };

    public void addPlayer(View view){
        closeKeyboard();
        if(playerList.size() >= 6) {
            Context context = getApplicationContext();
            CharSequence text = "You have reached the maximum amount of players!";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        String newPlayerName = this.playerName.getText().toString();
        if(newPlayerName.isEmpty()){
            newPlayerName = "Player " + (myAdapter.getItemCount()+1);
        }
        myAdapter.addNewData(newPlayerName);
        playerName.setText("");
        Player newPlayer = new Player(newPlayerName);
        playerList.add(newPlayer);
    }

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        if(view == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}