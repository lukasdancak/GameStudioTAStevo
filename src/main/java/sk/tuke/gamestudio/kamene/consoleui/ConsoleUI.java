package sk.tuke.gamestudio.kamene.consoleui;

import sk.tuke.gamestudio.kamene.Kamene;
import sk.tuke.gamestudio.kamene.UserInterface;
import sk.tuke.gamestudio.kamene.core.Field;
import sk.tuke.gamestudio.kamene.core.GameState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class ConsoleUI implements UserInterface {
    private Field field;
    private ArrayList<String> listOfCommands = new ArrayList<String>(
            Arrays.asList("new", "exit", "w", "s", "a", "d", "up", "down", "left", "right"));

    /**
     * Input reader.
     */
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Reads line of text from the reader.
     *
     * @return line as a string
     */
    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void newGameStarted(Field field) {
        this.field = field;
        do {
            update();
            processInput();

            if (field.getGamestate() == GameState.SOLVED) {
                System.out.println("Vyhral si.");

                break;
            }
            //ukonci hru ak hrac zada vstup "exit"
            if (field.getGamestate() == GameState.EXIT_BY_USER) {
                System.out.println("Ukoncil si toto kolo a cely program.");

                break;
            }
            // spusti novu hru na zaklade poziadavky z menu
            if (field.getGamestate() == GameState.ASKED_FOR_NEW_GAME) {
                System.out.println("Toto kolo ukoncujem, spustam ti nove");

                break;
            }


        } while (true);
        if (field.getGamestate() == GameState.EXIT_BY_USER) {
            System.exit(0);
        }
        if (field.getGamestate() == GameState.ASKED_FOR_NEW_GAME) {
            newGameStarted(new Field(Kamene.getInstance().getSetting().getRozmerStvorca(), Kamene.getInstance().getSetting().getRozmerStvorca()));
        }
        if (field.getGamestate() == GameState.SOLVED) {
            if (wantNewRound()) {
                System.out.println("Spustam nove kolo.");
                newGameStarted(new Field(Kamene.getInstance().getSetting().getRozmerStvorca(), Kamene.getInstance().getSetting().getRozmerStvorca()));
            } else {
                System.out.println("Ukoncujem program.");
                System.exit(0);
            }
        }


        //

    }

    private boolean wantNewRound() {
        System.out.println("Chces si zahrat dalsie kolo ? |a - ano| |n - nie|");
        String answer = readLine().toLowerCase(Locale.ROOT);
        if (answer.equalsIgnoreCase("a") || answer.equalsIgnoreCase("ano")) {
            return true;
        }
        if (answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("nie")) {
            return false;
        }
        System.out.println("Odpoved bola zle zadana.");
        return wantNewRound();


    }

    private void processInput() {
        String playerInput = readLine().toLowerCase(Locale.ROOT);

        try {
            handleInput(playerInput);
        } catch (WrongFormatException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
            processInput();
            return;

        }
        //vykona operaciu podla vstupu
        doOperation(playerInput);
        //skontroluje, ci nieje hra vyriesena
        if (field.isSolved()) {
            field.setGamestate(GameState.SOLVED);
        }
        //vypise dobu trvania daneho kola v sekundach
        System.out.printf("Toto kolo hrajes %s sekund.%n", field.getPlayingTimeInSeconds());

    }

    private void doOperation(String playerInput) {
        switch (playerInput) {
            case "w":
            case "up":
                System.out.println(field.moveUp());
                break;
            case "s":
            case "down":
                System.out.println(field.moveDown());
                break;
            case "a":
            case "left":
                System.out.println(field.moveLeft());
                break;
            case "d":
            case "right":
                System.out.println(field.moveRight());
                break;
            case "new":
                field.setGamestate(GameState.ASKED_FOR_NEW_GAME);
                break;
            case "exit":
                field.setGamestate(GameState.EXIT_BY_USER);
                break;
        }

    }


    void handleInput(String playerInput) throws WrongFormatException {

        //overi vstup, porovna s listom prikazov
        if (!listOfCommands.contains(playerInput)) {
//            System.out.println("!!! Zadal si nespravny format vstupu, opakuj vstup.");
            throw new WrongFormatException("!!! Zadal si nespravny format vstupu, opakuj vstup.");

        }
    }

    @Override
    public void update() {
        System.out.println("Menu:  |new - pre novú hru| |exit - pre koniec| |w (up) - hore| " +
                "|s (down) - dole| |a (left) - dolava| |d (right) - doprava|");
        printField();

    }

    private void printField() {


        //vypis riadky so zvislo osou na zaciatku
        for (int i = 0; i < this.field.getRowCount(); i++) {

            for (int j = 0; j < this.field.getColumnCount(); j++) {

                System.out.printf("%3s", this.field.getTile(i, j).toString());


            }
            System.out.println();
        }
    }
}
