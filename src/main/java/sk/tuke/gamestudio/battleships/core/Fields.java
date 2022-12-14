package sk.tuke.gamestudio.battleships.core;

import com.sun.xml.bind.v2.runtime.output.SAXOutput;

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
        this.playersField.makeAllTilesOpen(); // hrac vidi s voje pole otvorene
        this.computersField = new Field(10, 10, 31);
        this.startMillis = System.currentTimeMillis();
    }

    public void hitEnemyAndGetHit(int row, int column) {
        //ak sa FIELDS dostane do stavu WIN alebo FAILED, uz nie je mozne na nom hrat
        if (gameState == GameState.PLAYING) {
            this.computersField.hitTile(row, column);
            int[] coordinates = getCoordinatesFromComputerForHisAttack();
            this.playersField.hitTile(coordinates[0], coordinates[1]);
            gameState = refreshGameState();
        }
        System.out.println("Hodnota skore je: " + this.score);

    }

    private GameState refreshGameState() {
        if (this.computersField.getFieldState() == FieldState.SOLVED) {
            System.out.println("pocitam skore a zapisujem");
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
        System.out.println("c1: " + coordinates[0] + " c2: " + coordinates[1]);
        if (this.playersField.getTile(coordinates[0], coordinates[1]).getHitState() ==
                Tile.HitState.HIT) {
            return getCoordinatesFromComputerForHisAttack();
        } else {
            return coordinates;
        }
    }


    public int getScore() {
        return score;
    }

    public int calculateScore() {

        if (this.computersField.getFieldState() == FieldState.SOLVED) {
            return (10000 / ((getPlayTimeInSeconds() + 1))) + 1;
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

    public void setScore(int score) {
        this.score = score;
    }
}
