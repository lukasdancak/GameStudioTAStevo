package sk.tuke.gamestudio.kamene.core;

import java.util.Random;

public class Field {
    private long startMillis;
    int rowCount;
    int columnCount;
    GameState gamestate = GameState.PLAYING;
    Tile[][] tiles;
    // suradnice na sledovanie prazdnej dlazdice:
    int emptyTileRow;
    int emptyTileColumn;

    // dva premenne int  - sirka a vyska
    public Field(int rowCount, int columnCount) {
        startMillis = System.currentTimeMillis();
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        tiles = new Tile[rowCount][columnCount];
        //generate the field content
        generate();
    }

    //vygeneruje pole s dlazdicami
    private void generate() {
        int pocitadlo = 1;
        for (int i = 0; i < this.getRowCount(); i++) {
            for (int j = 0; j < this.getColumnCount(); j++) {
                tiles[i][j] = new Tile(pocitadlo++, i, j);

            }
        }
        // na poslednu poziciu vlozi Tile s hodnotou nula, to bude medzera
        tiles[this.getRowCount() - 1][this.getColumnCount() - 1] =
                new Tile(0, this.getRowCount() - 1, this.getColumnCount() - 1);
        emptyTileRow = this.getRowCount() - 1;
        emptyTileColumn = this.getColumnCount() - 1;

        // zamesaj dlazdice v poli
        mixTilesInField();


    }

    // zamiesa dlazdice
    private void mixTilesInField() {
        Random r = new Random();
        for (int i = 0; i < 10; i++) {

            switch (r.nextInt(4)) {
                case 0:
                    this.moveDown();
                    break;
                case 1:
                    this.moveLeft();
                    break;
                case 2:
                    this.moveRight();
                    break;
                case 3:
                    this.moveUp();
                    break;

            }


        }
    }

    public GameState getGamestate() {
        return gamestate;
    }

    public void setGamestate(GameState gamestate) {
        this.gamestate = gamestate;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    // do metody je mozne zadavat iba  4 vstupy: down, up, left, right. Mozno to prerobit na ENUM,
    public boolean isMovementOfEmptyTilePossibleTo(String s) {
        switch (s) {
            case "down":
                if (emptyTileRow + 1 >= rowCount) {
                    return false;
                } else {
                    return true;
                }
            case "up":
                if (emptyTileRow - 1 < 0) {
                    return false;
                } else {
                    return true;
                }
            case "left":
                if (emptyTileColumn - 1 < 0) {
                    return false;
                } else {
                    return true;
                }
            case "right":
                if (emptyTileColumn + 1 >= columnCount) {
                    return false;
                } else {
                    return true;
                }

        }

        return true;
    }

    public String moveUp() { // posunie dlazdicu s cislom hore, prazdnu posunie dole
        if (isMovementOfEmptyTilePossibleTo("down")) {
            swapDown();
            return "Vykonal som pohyb hore.";
        } else {
            return "Pohyb dalzdice smerom hore nie je mozny";
        }


    }

    public int getPlayingSeconds() {
        return (int) (System.currentTimeMillis() - startMillis) / 1000;
    }

    //posunie prazdnu dlazdicu dole
    private void swapDown() {
        Tile temp = tiles[emptyTileRow][emptyTileColumn];
        tiles[emptyTileRow][emptyTileColumn] = tiles[emptyTileRow + 1][emptyTileColumn];
        tiles[emptyTileRow + 1][emptyTileColumn] = temp;
        emptyTileRow++;

    }

    //posunie dlazdicu s cislom dole
    public String moveDown() {
        if (isMovementOfEmptyTilePossibleTo("up")) {
            swapUp();
            return "Vykonal som pohyb dole.";
        } else {
            return "Pohyb dalzdice smerom dole nie je mozny";
        }
    }

    // posunie prazdnu dlazdicu hore
    private void swapUp() {
        Tile temp = tiles[emptyTileRow][emptyTileColumn];
        tiles[emptyTileRow][emptyTileColumn] = tiles[emptyTileRow - 1][emptyTileColumn];
        tiles[emptyTileRow - 1][emptyTileColumn] = temp;
        emptyTileRow--;
    }

    //posunie dlazdicu s cislom dolava
    public String moveLeft() {
        if (isMovementOfEmptyTilePossibleTo("right")) {
            swapRight();
            return "Vykonal som pohyb dolava.";
        } else {
            return "Pohyb dalzdice smerom dolava nie je mozny";
        }
    }

    //posunie prazdnu dlazdicu s cislom doprava
    private void swapRight() {
        Tile temp = tiles[emptyTileRow][emptyTileColumn];
        tiles[emptyTileRow][emptyTileColumn] = tiles[emptyTileRow][emptyTileColumn + 1];
        tiles[emptyTileRow][emptyTileColumn + 1] = temp;
        emptyTileColumn++;


    }

    //posunie dlazdicu s cislom do prava
    public String moveRight() {
        if (isMovementOfEmptyTilePossibleTo("left")) {
            swapLeft();
            return "Vykonal som pohyb doprava.";
        } else {
            return "Pohyb dalzdice smerom doprava nie je mozny";
        }
    }

    // posunie prazdnu dlazdicu dolava
    private void swapLeft() {
        Tile temp = tiles[emptyTileRow][emptyTileColumn];
        tiles[emptyTileRow][emptyTileColumn] = tiles[emptyTileRow][emptyTileColumn - 1];
        tiles[emptyTileRow][emptyTileColumn - 1] = temp;
        emptyTileColumn--;

    }

    public boolean isSolved() {
        int variableToCompare = 1;

        for (int i = 0; i < this.getRowCount(); i++) {
            for (int j = 0; j < this.getColumnCount(); j++) {
                if (i == this.getRowCount() - 1 && j == this.getColumnCount() - 1) {
                    return true;
                }
                if (this.getTile(i, j).getValue() == variableToCompare) {
                } else {
                    return false;
                }
                variableToCompare++;


            }
        }

        return true;
    }

    public Tile[][] getTiles() {
        return tiles;
    }
}
