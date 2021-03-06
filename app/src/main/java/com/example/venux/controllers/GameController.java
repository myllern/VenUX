package com.example.venux.controllers;

import com.example.venux.model.Move;
import com.example.venux.model.NewGame;
import com.example.venux.model.Player;

import java.util.ArrayList;
import java.util.LinkedList;

public class GameController {

    private static NewGame game;

    public static void createNewGame(ArrayList<Player> players) {
        game = new NewGame(players);
    }

    public static boolean playNextStep(Move move) {
        return game.playNextStep(move);
    }

    public static boolean isCreateMoveState() {
        return game.isCreateMoveState();
    }

    public static boolean isLastMove() {
        return game.isLastMove();
    }

    public static int getCurrentMove() {
        return game.getCurrentMove();
    }

    public static int getAmountOfMoves() {
        return game.getAmountOfMoves();
    }

    public static LinkedList<Player> getDeadPlayrs() { return game.getDeadPlayers(); }

    public static Player getCurrentPlayer() {
        return game.getCurrentPlayer();
    }

    public static boolean isGameFinished() {
        return game.isGameFinished();
    }

}
