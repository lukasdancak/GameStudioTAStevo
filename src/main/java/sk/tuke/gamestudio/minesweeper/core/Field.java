package sk.tuke.gamestudio.minesweeper.core;

import java.util.Random;

/**
 * Field represents playing field and game logic.
 */
public class Field {
    /**
     * Playing field tiles.
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
     * Mine count.
     */
    private final int mineCount;

    /**
     * Game state.
     */
    private GameState state = GameState.PLAYING;

    /**
     * time when the game started
     */
    private long startMillis;

    private int score =0;

    /**
     * Constructor.
     *
     * @param rowCount    row count
     * @param columnCount column count
     * @param mineCount   mine count
     */
    public Field(int rowCount, int columnCount, int mineCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.mineCount = mineCount;
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

    public int getMineCount() {
        return mineCount;
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
    public void openTile(int row, int column) {
        Tile tile = tiles[row][column];
        if (tile.getState() == Tile.State.CLOSED) {
            tile.setState(Tile.State.OPEN);
            if(tile instanceof Clue
                && ((Clue)tile).getValue() == 0) {
                getOpenAdjacentTiles(row, column);
            }

            if (tile instanceof Mine) {
                state = GameState.FAILED;
               this.score = this.calcualteScore();
                return;
            }

            if (isSolved()) {
                state = GameState.SOLVED;
               this.score = this.calcualteScore();
                return;
            }
        }
    }

    private void getOpenAdjacentTiles(int row, int column) {
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            int actRow = row + rowOffset;
            if (actRow >= 0 && actRow < rowCount) {
                for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
                    int actColumn = column + columnOffset;
                    if (actColumn >= 0 && actColumn < columnCount) {
                        openTile(actRow, actColumn);
                    }
                }
            }
        }
    }

    /**
     * Marks tile at specified indeces.
     *
     * @param row    row number
     * @param column column number
     */
    public void markTile(int row, int column) {
        if (state == GameState.PLAYING) {
            var tile = getTile(row, column);
            if (tile.getState() == Tile.State.CLOSED) {
                tile.setState(Tile.State.MARKED);
            } else if (tile.getState() == Tile.State.MARKED) {
                tile.setState(Tile.State.CLOSED);
            }
        }
    }



    public int getRemainingMineCount() {
        return getMineCount() - this.getNumberOf(Tile.State.MARKED);
    }

    private int getNumberOf(Tile.State state) {
        int count = 0;
        for (int r = 0; r < rowCount; r++) {
//            count += Arrays.asList(tiles[r])
//                    .stream()
//                    .filter((Tile t) -> t.getState() == state)
//                    .count();
            for(Tile t : tiles[r]) {
                if(t.getState() == state)
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

        while(counter < mineCount) {
            r = rand.nextInt(rowCount);
            c = rand.nextInt(columnCount);
            if(tiles[r][c] == null) {
                tiles[r][c] = new Mine();
                counter++;
                //assert counter > 0;
            }
        }

        for (r = 0; r < rowCount; r++) {
            for (c = 0; c < columnCount; c++) {
                if(tiles[r][c] == null) {
                    tiles[r][c] = new Clue(countAdjacentMines(r, c));
                }
            }
        }

        startMillis = System.currentTimeMillis();
    }

    /**
     * Returns true if game is solved, false otherwise.
     *
     * @return true if game is solved, false otherwise
     */
    public boolean isSolved() {
        return (rowCount * columnCount) - mineCount
                == getNumberOf(Tile.State.OPEN);
    }
    /**
     * Returns number of adjacent mines for a tile at specified position in the field.
     *
     * @param row    row number.
     * @param column column number.
     * @return number of adjacent mines.
     */
    private int countAdjacentMines(int row, int column) {
        int count = 0;
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            int actRow = row + rowOffset;
            if (actRow >= 0 && actRow < rowCount) {
                for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
                    int actColumn = column + columnOffset;
                    if (actColumn >= 0 && actColumn < columnCount) {
                        if (tiles[actRow][actColumn] instanceof Mine) {
                            count++;
                        }
                    }
                }
            }
        }

        return count;
    }

    public GameState getState() {
        return state;
    }

    public int getPlayTimeInSeconds(){
        return (int) ((System.currentTimeMillis() - startMillis)/1000);
    }



    public int calcualteScore() {

        if (this.getState() == GameState.SOLVED) {
            return rowCount * columnCount * 10 - getPlayTimeInSeconds();
        } else {return 0;}
    }

    public int getScore() {
        return score;
    }
}
