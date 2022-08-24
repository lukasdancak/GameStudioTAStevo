package sk.tuke.gamestudio.kamene.core;

public class Tile {
    private final int value;
    private int positionRow; // zatial to nepouzivam, mozno vobec nebudem
    private int postiionColumn; // zatial to nepouzivam, mozno vobec nebudem

    public Tile(int value, int positionRow, int postiionColumn) {
        this.value = value;
        this.positionRow = positionRow;
        this.postiionColumn = postiionColumn;
    }

    public int getValue() {
        return value;
    }

    public int getPositionRow() {
        return positionRow;
    }

    public void setPositionRow(int positionRow) {
        this.positionRow = positionRow;
    }

    public int getPostiionColumn() {
        return postiionColumn;
    }

    public void setPostiionColumn(int postiionColumn) {
        this.postiionColumn = postiionColumn;
    }

    @Override
    public String toString() {
        if (value == 0) {
            return "#";
        } else {
            return String.valueOf(value);
        }

    }


}
