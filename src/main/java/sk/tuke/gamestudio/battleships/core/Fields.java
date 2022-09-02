package sk.tuke.gamestudio.battleships.core;

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


    public int getScore() {
        return score;
    }

    public int calcualteScore() {

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
}
