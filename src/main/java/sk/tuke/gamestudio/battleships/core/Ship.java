package sk.tuke.gamestudio.battleships.core;

public class Ship extends Tile {

    @Override
    public String toString() {
        if (this.getState() == Tile.State.OPEN) {
            return "X";
        }
        return super.toString();
    }
}
