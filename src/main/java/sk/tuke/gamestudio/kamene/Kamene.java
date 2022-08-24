package sk.tuke.gamestudio.kamene;

import sk.tuke.gamestudio.kamene.consoleui.ConsoleUI;
import sk.tuke.gamestudio.kamene.core.Field;

public class Kamene {
    private UserInterface userInterface;
    private static Kamene instance;
    private Settings setting; //nastavenie obtiaznosti

    private Kamene() {
        setting = Settings.BEGINNER;
        instance = this;
        userInterface = new ConsoleUI();
        Field field = new Field(setting.getRozmerStvorca(), setting.getRozmerStvorca());
        userInterface.newGameStarted(field);
    }

    public static Kamene getInstance() {
        if (instance == null) {
            instance = new Kamene();
        }
        return instance;

    }

    public static void main(String[] args) {

        System.out.println("Hello " + System.getProperty("user.name") + " !");
        new Kamene();

    }

    public Settings getSetting() {
        return setting;
    }
}
