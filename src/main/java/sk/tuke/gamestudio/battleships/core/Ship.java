package sk.tuke.gamestudio.battleships.core;

public class Ship extends Tile {

    @Override
    public String toString() {
        if (this.getVisibilityState() == VisibilityState.OPEN) {
            return "X";
        }
        return super.toString();
    }
}
