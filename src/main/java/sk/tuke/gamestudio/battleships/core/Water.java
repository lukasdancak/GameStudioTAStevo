package sk.tuke.gamestudio.battleships.core;

public class Water extends Tile {

    public Water() {
    }

    @Override
    public String toString() {
        if (this.getState() == Tile.State.OPEN) {
            return "W";
        }
        return super.toString();
    }
}
