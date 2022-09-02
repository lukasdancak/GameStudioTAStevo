package sk.tuke.gamestudio.battleships.core;

import java.util.Random;

public class Fields {
    Field playersField;
    Field computersField;
    GameState gameState = GameState.PLAYING;

    /**
     * time when the game started
     */
    private long startMillis;

    private int score = 0;

    //konstruktor


    public Fields() {
        //Field-y naplni konstruktor Field-ov
        this.playersField = new Field(10, 10, 31);
        this.computersField = new Field(10, 10, 31);
        this.startMillis = System.currentTimeMillis();
    }

    public void hitEnemyAndGetHit(int row, int column) {
        this.computersField.hitTile(row, column);
        int[] coordinates = getCoordinatesFromComputerForHisAttack();
        this.playersField.hitTile(coordinates[0], coordinates[1]);
        gameState = refreshGameState();

    }

    private GameState refreshGameState() {
        if (this.computersField.getFieldState() == FieldState.SOLVED) {
            this.score = calculateScore(); // vypocita a zapise skore
            return GameState.WIN;

        }
        if (this.playersField.getFieldState() == FieldState.SOLVED) {
            return GameState.FAILED;
        }
        return GameState.PLAYING;
    }

    private int[] getCoordinatesFromComputerForHisAttack() {
        int[] coordinates = new int[2];
        Random r = new Random();
        coordinates[0] = r.nextInt(10);
        coordinates[1] = r.nextInt(10);
        return coordinates;
    }


    public int getScore() {
        return score;
    }

    public int calculateScore() {

        if (this.getGameState() == GameState.WIN) {
            return 1000 / (getPlayTimeInSeconds() + 1);
        } else {
            return 0;
        }
    }

    public int getPlayTimeInSeconds() {
        return (int) ((System.currentTimeMillis() - startMillis) / 1000);
    }

    public GameState getGameState() {
        return gameState;
    }

    public Field getPlayersField() {
        return playersField;
    }

    public void setPlayersField(Field playersField) {
        this.playersField = playersField;
    }

    public Field getComputersField() {
        return computersField;
    }

    public void setComputersField(Field computersField) {
        this.computersField = computersField;
    }
}
