package sk.tuke.gamestudio.kamene;

public class Settings {
    private final int rozmerKocky;


    //    Hracie pole pre začiatočníkov - veľkosť 9x9, počet mín 10 -
    public static final Settings BEGINNER = new Settings(4);

    //    Hracie pole pre mierne pokročilých - veľkosť 16x16, počet mín 40 -
    public static final Settings INTERMEDIATE = new Settings(8);

    //    Hracie pole pre pokročilých - veľkosť 16x30, počet mín 99 -
    public static final Settings EXPERT = new Settings(16);

    public Settings(int rowCount) {
        this.rozmerKocky = rowCount;

    }

    public int getRozmerStvorca() {
        return rozmerKocky;
    }
}
