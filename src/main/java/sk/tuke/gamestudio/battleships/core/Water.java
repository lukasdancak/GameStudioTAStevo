package sk.tuke.gamestudio.battleships.core;

public class Water extends Tile {

    public Water() {
    }

    @Override
    public String toString() {
        if (this.getVisibilityState() == VisibilityState.OPEN) {
            return "W";
        }
        return super.toString();
    }
}
