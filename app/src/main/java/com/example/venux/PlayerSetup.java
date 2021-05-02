package com.example.venux;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerSetup extends AppCompatActivity {
    private Controller controller;
    private Button btn_add_player0,btn_add_player1;
    private EditText TW_edit_player0,TW_edit_player1;
    private TextView tw;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_setup);
        controller = new Controller(this);

        TW_edit_player0 = findViewById(R.id.setup_editName_player0);
        TW_edit_player1 = findViewById(R.id.setup_editName_player1);
        btn_add_player0 = findViewById(R.id.setup_add_player0_btn);
        btn_add_player1 = findViewById(R.id.setup_add_player1_btn);
        tw =findViewById(R.id.setup_showName_player0);

        btn_add_player0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = TW_edit_player0.getText().toString();
                int id = 0;
                if (TW_edit_player0.length() != 0) {
                    addPlayer(id, name);
                    toastMessage(name + " added as Player!");

                } else {
                    toastMessage("You must put something in the text field!");
                }
            }
        });

        btn_add_player1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = TW_edit_player1.getText().toString();
                int id = 1;
                if (TW_edit_player1.length() != 0) {
                    addPlayer(id, name);
                    toastMessage(name + " added as Player!");
                } else {
                    toastMessage("You must put something in the text field!");
                }
            }
        });
    }


    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }



    public String getPlayer(int id) {
        return controller.db.getPlayer(id);
    }




    public void addPlayer(int id, String name){
        boolean isPlayerAdded = controller.db.addPlayer(id, name);
        if(isPlayerAdded){
            toastMessage(name+ " added!");

        } else{
            updatePlayer(id,name);
            toastMessage("player: " + id + " updated to: " + name);
        }

    }

    public void updatePlayer(int id, String newName){
       controller.db.updatePlayer(id, newName);


    }


    public void startGame(View view){
        Intent playGame = new Intent(this, PlayGame.class);
        startActivity(playGame);
    };

}