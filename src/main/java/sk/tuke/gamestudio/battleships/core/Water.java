package sk.tuke.gamestudio.battleships.core;

public class Water extends Tile {

    public Water() {
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("");

        if (this.getVisibilityState() == VisibilityState.OPEN) {
            s.append("W");
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
