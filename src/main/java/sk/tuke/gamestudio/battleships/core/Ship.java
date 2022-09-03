package sk.tuke.gamestudio.battleships.core;

public class Ship extends Tile {

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("");

        if (this.getVisibilityState() == VisibilityState.OPEN) {
            s.append("S");
            if (this.getHitState() == HitState.HIT) {
                s.append("H");
            } else {
                s.append("U");
            }
            return s.toString();
        }
        return super.toString();
    }
}
