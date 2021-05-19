package com.example.venux.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.venux.R;
import com.example.venux.controllers.GameController;
import com.example.venux.model.Player;

import java.util.LinkedList;

public class ScoreboardActivity extends AppCompatActivity {

    TextView winnerNameTV;
    TextView scoreboardTV;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        winnerNameTV = findViewById(R.id.winnerNameTV);
        winnerNameTV.setText("#1 " + GameController.getCurrentPlayer().getName());
        scoreboardTV = findViewById(R.id.scoreboardTV);

        StringBuilder sb = new StringBuilder();
        LinkedList<Player> scoreboardList = GameController.getDeadPlayrs();
        for (int i = 0; i <= scoreboardList.size(); i++) {
            Player p = scoreboardList.pop();
            sb.append("#" + (i+2) + " " + p.getName() + "\n");
        }
        scoreboardTV.setText(sb.toString());
    }

    public void continueHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
