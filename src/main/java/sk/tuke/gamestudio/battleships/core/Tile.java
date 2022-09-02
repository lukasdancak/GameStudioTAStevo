package sk.tuke.gamestudio.battleships.core;

public abstract class Tile {

    /**
     * Tile states.
     */
    public enum State {
        /**
         * Open tile.
         */
        OPEN,
        /**
         * Closed tile.
         */
        CLOSED,
    }

    /**
     * Tile state.
     */
    private State state = State.CLOSED;

    @Override
    public String toString() {
        return "-";
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
