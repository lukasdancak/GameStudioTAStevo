package sk.tuke.gamestudio.battleships.core;

import java.util.Random;

/**
 * Field represents playing field and game logic.
 */
public class Field {
    /**
     * Playing field1: computers field, user is attacking on it.
     */
    private final Tile[][] tiles;


    /**
     * Field row count. Rows are indexed from 0 to (rowCount - 1).
     */
    private final int rowCount;

    /**
     * Column count. Columns are indexed from 0 to (columnCount - 1).
     */
    private final int columnCount;

    /**
     * Ships tiles count.
     */
    private final int shipsTilesCount;

    //uncovered ships
    private int foundedShipTilesCount = 0;

    /**
     * Game state.
     */
    private FieldState fieldState = FieldState.PLAYING;


    /**
     * Constructor.
     *
     * @param rowCount       row count
     * @param columnCount    column count
     * @param shipTilesCount mine count
     */
    public Field(int rowCount, int columnCount, int shipTilesCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.shipsTilesCount = shipTilesCount;
        tiles = new Tile[rowCount][columnCount];

        //generate the field content
        generate();
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getShipsTilesCount() {
        return shipsTilesCount;
    }

    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Opens tile at specified indeces.
     *
     * @param row    row number
     * @param column column number
     */
    public void hitTile(int row, int column) {
        Tile tile = tiles[row][column];
        tile.setVisibilityState(Tile.VisibilityState.OPEN);
        tile.setHitState(Tile.HitState.HIT);

        if (tile instanceof Ship) {
            this.foundedShipTilesCount++;

        }

        if (isSolved()) {
            fieldState = FieldState.SOLVED;

        }

    }


    public int getRemainingShipsTilesCount() {
        return getShipsTilesCount() - this.getFoundedShipTilesCount();
    }

    private int getNumberOf(Tile.VisibilityState state) {
        int count = 0;
        for (int r = 0; r < rowCount; r++) {
//            count += Arrays.asList(tiles[r])
//                    .stream()
//                    .filter((Tile t) -> t.getState() == state)
//                    .count();
            for (Tile t : tiles[r]) {
                if (t.getVisibilityState() == state)
                    count++;
            }
        }
        return count;
    }

    /**
     * Generates playing field.
     */
    private void generate() {
        Random rand = new Random();
        int counter = 0, r, c;

        while (counter < shipsTilesCount) {
            r = rand.nextInt(rowCount);
            c = rand.nextInt(columnCount);
            if (tiles[r][c] == null) {
                tiles[r][c] = new Ship();
                counter++;
                //assert counter > 0;
            }
        }

        for (r = 0; r < rowCount; r++) {
            for (c = 0; c < columnCount; c++) {
                if (tiles[r][c] == null) {
                    tiles[r][c] = new Water();
                }
            }
        }


    }

    /**
     * Returns true if game is solved, false otherwise.
     *
     * @return true if game is solved, false otherwise
     */
    public boolean isSolved() {
        return this.foundedShipTilesCount == this.shipsTilesCount;
    }

    /**
     * Returns number of adjacent mines for a tile at specified position in the field.
     */


    public FieldState getFieldState() {
        return fieldState;
    }

    public int getFoundedShipTilesCount() {
        return foundedShipTilesCount;
    }

}
