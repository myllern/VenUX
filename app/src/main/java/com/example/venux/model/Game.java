package com.example.venux.model;

import com.example.venux.model.Move;
import com.example.venux.model.MoveList;
import com.example.venux.model.Player;

import java.util.ArrayList;

public abstract class Game {

    protected ArrayList<Player> players;
    protected Player currentPlayer;
    protected MoveList moveList;
    protected Move currentMove;
    protected int numberOfRounds;
    protected int currentRound;

    public Game() {
        this.players = new ArrayList<Player>();
        this.moveList = new MoveList();
        this.numberOfRounds = 0;
        this.currentRound = 0;

    }

    public void startGame() {
        if (players.size() == 0) {
            players.add(new Player("Player 1"));
        }
        currentPlayer = players.get(0); //Keep this line
    }

    /**
     * Creates a new "empty" Move and sets it to be the currentMove
     *
     * @return currentMove that is now "empty"
     */
    protected Move createNewMove() {
        currentMove = new Move();
        return currentMove;
    }

    /**
     * Sets the positions for the currentMove
     *
     * @param xRot - the xValue for the Move
     * @param yRot - the yValue for the Move
     * @param zRot - the zValue for the Move
     * @return the currentMove that is now updated with positions
     */
    protected Move setMovePositions(float xRot, float yRot, float zRot) {
        currentMove.getxRotation()[0] = xRot;
        currentMove.getyRotation()[0] = yRot;
        currentMove.getzRotation()[0] = zRot;
        return currentMove;
    }

    /**
     * Checks wether the currentMove was correct compared
     * to the Move that should be Kopied this round
     *
     * @return true if the Move was correct, false otherwise
     */
    protected boolean wasMoveCorrect() {
        return currentMove.isSimpleMoveCloseEnough(moveList.getMove(currentRound));
    }

    /**
     * Records a new Move and adds it to the MoveList.
     * Also increases the number of rounds.
     * Also sets the currentRound to 0, so next player will
     * start to Kopy from the first Move in MoveList
     *
     * @param xRot - xValue of the new Move
     * @param yRot - yValue of the new Move
     * @param zRot - zValue of the new Move
     *             ToDO: check if the move corresponds to an allowed move before adding
     */
    public void recordNewMove(float xRot, float yRot, float zRot) {
        createNewMove();
        setMovePositions(xRot, yRot, zRot);
        moveList.addMove(currentMove);
        numberOfRounds++;
        currentRound = 0;
        currentPlayer = getNextPlayer();
    }


    /**
     * Sets currentRound to the next value.
     *
     * @return the new currentRound
     */
    public int nextRound() {
        currentRound++;
        return currentRound;
    }

    /**
     * sets currentRound to 0.
     * Is used to start again from the beginning.
     *
     * @return currentRound that is now 0
     */
    public int restartRounds() {
        currentRound = 0;
        return currentRound;
    }

    /**
     * Tells us if we need to record a new Move
     * (instead of Kopying a move). This happens
     * when currentRound is the same as numberOfRounds
     *
     * @return true if we need to record a new round, otherwise false
     */
    public boolean needToRecordNewMove() {
        return currentRound == numberOfRounds;
    }

    /**
     * Gets the currentRound
     *
     * @return int currentRound
     */
    public int getCurrentRound() {
        return currentRound;
    }

    public int getMovesLeft() {
        return numberOfRounds - currentRound;
    }

    /**
     * Resets the game to have no Moves, no rounds
     * and start from round 0.
     */
    public void resetGame() {
        moveList = new MoveList();
        restartRounds();
        numberOfRounds = 0;
        //ToDo perhaps reset all players? Or do we keep players?
    }

    /**
     * Checks weather the next round is going to
     * be a recorder round (instead of a Kopy round)
     *
     * @return true if next round is recorder round,
     * otherwise false.
     */
    public boolean isNextRoundRecorderRound() {
        return currentRound == numberOfRounds - 1;
    }


    public abstract boolean playNextRound(float xRot, float yRot, float zRot);

    public abstract void readyNextRound();

    //----------------------------//
    // Methods for players//

    /**
     * Creates a new Player with name name and puts
     * it in the players list.
     *
     * @param name - name of the player
     */
    public void addNewPlayer(String name) {
        //ToDo: if we should give player's colours, this is the place to do it
        players.add(new Player(name));
    }

    public void addAllPlayers(ArrayList<String> playerNames) {
        for (String playerName : playerNames) {
            addNewPlayer(playerName);
        }
    }

    /**
     * Gets the name of the currentPlayer
     *
     * @return - name of the currentPlayer
     */
    public String getCurrentPlayerName() {
        return currentPlayer.getName();
    }

    /**
     * Gets the color of the currentPlayer
     *
     * @return - color of the currentPlayer
     */
    public String getCurrentPlayerColor() {
        return currentPlayer.getColor();
    }

    /**
     * Gets the total score of the currentPlayer
     *
     * @return - total score of the currentPlayer
     */
    public int getCurrentPlayerScore() {
        return currentPlayer.getPlayerTotalScore();
    }

    /*
     * ToDo: implement method that removes a player
     *  (probably based on index). Used when you need to remove
     *  a player you accidentally added during playerSetup.
     *  Do not remove when they died.
     */

    /**
     * Gets the next Player that will play the game
     *
     * @return - the next Player that will play the game
     */
    protected Player getNextPlayer() {
        int index = players.indexOf(currentPlayer);
        for (int i = 1; i <= players.size(); i++) {
            int newIndex = (index + i) % players.size(); //uses modulo to be able to loop around the arraylist
            if (!players.get(newIndex).isDead()) return players.get(newIndex);
        }
        //ToDo test this method. I think it works but...
        return players.get(index);
    }

    /**
     * Checks wether the player is the last player alive.
     * If it is true, it means that the player has
     * won the game.
     *
     * @return true if the player is the last player alive,
     * otherwise false
     */
    public boolean isPlayerLastPlayerAlive() {
        return currentPlayer == getNextPlayer();
    }

    /**
     * Checks weather the Player is dead or not
     *
     * @return - true if the Player is dead, otherwise false
     */
    private boolean isPlayerDead() {
        return currentPlayer.isDead();
    }


    //ToDo: create method that returns how many players are in the game currently


    /**
     * Randomizes what player will play the game.
     */
    private void randomizePlayer() {
        /*
        ToDo !LOW PRIORITY! - maybe not even a thing to do?
         if we want to randomize what player should play now
         Example: currentPlayer = (do something to get a random player)
         remember to check if player is dead
         */
    }


}
