package sk.tuke.gamestudio.battleships.core;

public abstract class Tile {

    /**
     * Tile states.
     */
    public enum VisibilityState {
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
     * Hitted/UnTouched states.
     */
    public enum HitState {
        /**
         * Open tile.
         */
        HIT,
        /**
         * Closed tile.
         */
        UNHIT,
    }

    /**
     * Tile state.
     */
    private VisibilityState visibilityState = VisibilityState.CLOSED;
    private HitState hitState = HitState.UNHIT;

    @Override
    public String toString() {
        return "-";
    }

    public VisibilityState getVisibilityState() {
        return visibilityState;
    }

    public void setVisibilityState(VisibilityState state) {
        this.visibilityState = state;
    }

    public HitState getHitState() {
        return hitState;
    }

    public void setHitState(HitState hitState) {
        this.hitState = hitState;
    }
}
