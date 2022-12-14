package sk.tuke.gamestudio.minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.*;
import sk.tuke.gamestudio.minesweeper.core.Field;
import sk.tuke.gamestudio.minesweeper.core.GameState;
import sk.tuke.gamestudio.minesweeper.core.Tile;
import sk.tuke.gamestudio.service.*;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
    /**
     * Playing field. MA1 OB99
     */
    private Field field;
    Pattern OPEN_MARK_PATTERN = Pattern.compile("([OM]{1})([A-Z]{1})([0-9]{1,2})");

    /**
     * Input reader.
     */
    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    /**
     * object for accessing the persistent storage of player score
     */

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private OccupationService occupationService;
    @Autowired
    private RatingService ratingService;

    private Settings setting;

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


    /**
     * Starts the game.
     *
     * @param field field of mines and clues
     */
    @Override
    public void newGameStarted(Field field) {

        int gameScore = 0;
        this.field = field;
        String userName="";

        // moja vlastna premmenna, zapisem do nej objekt Player, ktori patri prave hrajucemu hraocvi
        Player playingPlayer= new Player("host","anonymous",    // defaultny player prep pripad,
                1, new Country("Zemegula"), new Occupation("nezamestnany"));      //ze sa nepodari nacitat/vytvorit Playera

        String userNamePlusFullName; // input for services Score, Rating, Comment


        //Test uloha 8/1. nacita userName - dlzka do 32 vratane
        System.out.println("Zadaj svoje meno:");
       userName = readStringWithLengthFrom1To(32);

        //Test uloha 8/2.	V datab??ze sa vyh??adaj?? z??znamy pre zadan?? pou????vate??sk?? meno a vyp????u sa.
        System.out.println("Hladam v databaze Player objekt s tvojim username");
        System.out.printf("Porovnavam tvoj  username: %s s databazou.%n", userName);
        List<Player> listOfPlayersFindedByUserName = null;

        try {
            listOfPlayersFindedByUserName = playerService.getPlayersByUserName(userName);
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Problem s databazou. Premenna: listOfPlayersFindedByUserName;" +
                    " Metoda: playerService.getPlayersByUserName()");
        }

        // situacia ak najde prave jedneho hraca s danym username v databaze
        if (listOfPlayersFindedByUserName != null && listOfPlayersFindedByUserName.size() == 1) {
            System.out.println("Nasiel som prave jeden objekt s tvojim username");
            playingPlayer = listOfPlayersFindedByUserName.get(0);
        }

        // situacia ak najde viac ako  jedneho hraca s danym username v databaze
        if (listOfPlayersFindedByUserName != null && listOfPlayersFindedByUserName.size() > 1) {
            System.out.printf("Nasiel som v databaze viacerych hracov s danym s username: %S%n.",userName);
            printListToRowsWithIndexInTheBegining(listOfPlayersFindedByUserName);
            System.out.println("Ktory si ty? Zadaj index:");
            int selectedInt = readIntFromXToY(0,listOfPlayersFindedByUserName.size()-1);
            playingPlayer = listOfPlayersFindedByUserName.get(selectedInt);
        }


        // situacia ak nenajde ziadneho hraca s danym username v databaze
        if (listOfPlayersFindedByUserName == null || listOfPlayersFindedByUserName.size()==0) {
            System.out.printf("Nenasiel som v databaze Player hraca s username %s%n", userName);
            System.out.println("Musim vytvorit objekt Player s tvojimi datami");
           Player newPlayer = pridanieNovehoHracaDoDatabazy(userName);
           playingPlayer=newPlayer; //zbytocne 2 premenne , ale lespie sa to cita
            System.out.println("Novy Player bol vytvoreny. Ukladam ho do databazy");
            try {
                playerService.addPlayer(newPlayer);
                System.out.println("Ulozenie noveho Player do databazy prebehlo vporiadku");
            } catch (Exception e) {
                //e.printStackTrace();
                System.out.println("Ulozenie newPlayer do databazy sa nepodarilo");
            }

        }

        userNamePlusFullName = playingPlayer.getUsername()+"("+playingPlayer.getFullname()+")";




        System.out.println("Vyber obtiaznost:");
        System.out.println("(1) BEGINNER, (2) INTERMEDIATE, (3) EXPERT, (ENTER) NECHAT DEFAULT");
        String level = readLine();
        if (level != null && !level.equals("")) {
            try {
                int intLevel = Integer.parseInt(level);
                Settings s = switch (intLevel) {
                    case 2 -> Settings.INTERMEDIATE;
                    case 3 -> Settings.EXPERT;
                    default -> Settings.BEGINNER;
                };
                this.setting = s;
                this.setting.save();
                this.field = new Field(s.getRowCount(), s.getColumnCount(), s.getMineCount());
            } catch (NumberFormatException e) {
                //empty naschval
            }
        }

        boolean gameShouldContinue = true;

        do {
            update();
            processInput();

            var fieldState = this.field.getState();

            if (fieldState == GameState.FAILED) {
                System.out.println(userName + ", odkryl si minu. Prehral si. Tvoje skore je " + gameScore + ".");
                gameShouldContinue = false;
            }
            if (fieldState == GameState.SOLVED) {
                gameScore = this.field.calcualteScore();
                System.out.println(userName + ", vyhral si. Tvoje skore je " + gameScore + ".");
                gameShouldContinue = false;
            }
        } while (gameShouldContinue);

        try {
            scoreService.addScore(new Score("minesweeper", userNamePlusFullName, gameScore, new Date()));
        } catch (Exception e) {
            System.out.println("Nepodarilo sa zapisat skore do databazy (" + e.getMessage() + ")");
        }

        printBestScores();

        //vypyta si komentar a zapise ho do databazy
        askForComment(userNamePlusFullName);

        // vypise vsetky komentare, ak nejake existuju
        printAllComments();

        //poziada hraca o jeho hodnotenie hry
        askForRating(userNamePlusFullName);

        System.out.printf("Vypisujem prirmerny rating-ako INT: %s.%n",ratingService.getAverageRating("minesweeper"));

        System.exit(0);

    }

    private void askForRating(String userNamePlusFullName) {
        int inputRating=0;
        System.out.println("Zadaj svoj rating hry");
        inputRating = readIntFromXToY(1,5);

        try {
            ratingService.setRating(new Rating("minesweeper", userNamePlusFullName, inputRating, new Date()));
            System.out.println("Spojenie s databazou prebehlo uspesne, tvoj rating bol ulozeny");
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Problem s databazou pri zapise ratingu");
        }

    }

    private int readIntFromXToY(int i, int k) {
        String s =readLine();
        if(s.matches("\\d+")){
            int j = Integer.parseInt(s);
            if(j>=i && j<=k){return j;}
        }
        System.out.printf("Zly vstup, zadaj integer od %s do %s%n", i,k);
        return readIntFromXToY(i,k);
    }

    private void printListToRowsWithIndexInTheBegining(List listOfPlayersFindedByUserName) {
        for (int i = 0; i < listOfPlayersFindedByUserName.size(); i++) {
            System.out.printf("Index: %s |-> %s%n", i, listOfPlayersFindedByUserName.get(i).toString());

        }
    }
    //vypyta si data na  vytvorenie noveho objektu Player, username je uz vstup metody
    private Player pridanieNovehoHracaDoDatabazy(String userName) {
        System.out.printf("Tvoje username mam, je to: %s%n", userName);
        System.out.println("Vloz dalsie data");

        System.out.println("Zadaj svoje fullname, 1-128 znakov:");
        String fullnameInput= readStringWithLengthFrom1To(128);

        System.out.println("Zadaj selfEvaluation, cele cislo od 1 do 10, vratane 1 a 10:");
        int selfEvaluationInput = readIntFromXToY(1,10);

        System.out.println("Vyber svoju krajinu zo zoznamu alebo pridaj novu.");
        Country countryInput=new Country("Zemegula"); //default
        System.out.println("Nacitavam zoznam krajin z databazy");
        List<Country> listOfcountries = null;
        try {
            listOfcountries = countryService.getCountries();
            System.out.println("Spojenie s databazou prebehlo v poriadku");
        } catch (Exception e) {
           // e.printStackTrace();
            System.out.println("Problem s databazou, metoda countryService.getCountries() v pridanieNovehoHracaDoDatabazy()");
                    }
        if(listOfcountries!=null){
            System.out.println("Vypisujem zoznam krajin:");
            printListToRowsWithIndexInTheBegining(listOfcountries);
            System.out.println("Chces zadat svoju vlastnu krajinu ?: A-ano | ENTER - nie, prejdi na vyber krajiny");
            if(readLine().equalsIgnoreCase("A")){
                System.out.println("Napis meno svojej krajiny");
                countryInput=new Country(readLine());
            } else {
                System.out.println("Vyber krajinu zadanim indexu zo zoznamu vypisanych krajin");
                countryInput =listOfcountries.get(readIntFromXToY(0,listOfcountries.size()-1));
            }

        }


        System.out.println("Vyber svoju pracovnu poziciu zo zoznamu");
        Occupation occupationInput = new Occupation("nezamestnany"); //default

        System.out.println("Nacitavam z databazy zoznam pozicii");
        List<Occupation> listOfOccupations = null;
        try{
            listOfOccupations = occupationService.getOccupations();
            System.out.println("Spojenie s databazou prebehlo v poriadku");
        }catch(Exception e){
            System.out.println("Problem s databazou, metoda getOccupations()");

        }
        if(listOfOccupations!=null){
            System.out.println("Vypisujem zoznam pozicii");
            printListToRowsWithIndexInTheBegining(listOfOccupations);
            System.out.println("Vyber poziciu zadanim indexu zo zoznamu vypisanyc pozicii");
            occupationInput = listOfOccupations.get(readIntFromXToY(0, listOfOccupations.size())-1);

        }
        System.out.println("Mam vsetky data, vytvaram objekt Player.");
        return new Player(userName,fullnameInput,selfEvaluationInput,countryInput,occupationInput);

    }

    // read String with length from 1 to n
    private String readStringWithLengthFrom1To(int n) {
        String s = readLine();
        if (s.length() > n || s.length() == 0) {
            System.out.printf("Zly vstup, vstup musi mat 1 az %s znakov. Opakuj vstup.%n",n);
            return readStringWithLengthFrom1To(n);
        } else {
            return s;
        }
    }


    private void printAllComments() {
        System.out.println("Vypisujem vsetky komentare: ");
        List<Comment> comments = null;
        try {
            comments = commentService.getComments("minesweeper");
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Nepodarilo sa nacitat komentare pomocou getComments()");
        }
        if (comments != null && !comments.isEmpty()) {
            for (Comment c : comments) {
                System.out.println(c);
            }
        } else {
            System.out.println("Ziadne komentare nie su ulozene v databaze");
        }
    }

    private void askForComment(String userName) {
        System.out.println("Pridaj svoj komentar (nacitanych bude prvych 1000 znakov komentaru): ");
        String userComment = readLine();
        if (userComment.length() > 0) {
            userComment = userComment.substring(0, Math.min(userComment.length(), 1000));

            try {
                commentService.addComment(new Comment("minesweeper", userName, // prerorbit-pole username daat do field
                        userComment, new Date()));
            } catch (Exception e) {
                //e.printStackTrace();
                System.out.println("Problem s databazou. addComment() neprebehol.");
            }
        } else {
            System.out.println("Komentar nemoze byt prazdny. Komentar nebol pridany.");
        }
    }

    private void printBestScores() {
        System.out.println("------------------------------------------------");
        System.out.println("5 najlepsich skore (hrac/ka, skore, datum):");
        try {
            List<Score> bestScores = scoreService.getBestScores("minesweeper");
            for (Score score : bestScores) {
                System.out.printf("%s, %d, %tD %n", score.getUsername(), score.getPoints(), score.getPlayedOn());
            }
        } catch (Exception e) {
            System.out.println("Nepodarilo sa ziskat skore z databazy (" + e.getMessage() + ")");
        }


    }

    /**
     * Updates user interface - prints the field.
     */
    @Override
    public void update() {
        //System.out.println("Metoda update():");
        System.out.printf("Cas hrania: %d%n",
                field.getPlayTimeInSeconds()
        );
        System.out.printf("Pocet poli neoznacenych ako mina je %s (pocet min: %s)%n", field.getRemainingMineCount(), field.getMineCount());

        //vypis horizontalnu os
        StringBuilder hornaOs = new StringBuilder("   ");
        for (int i = 0; i < field.getColumnCount(); i++) {
            hornaOs.append(String.format("%3s", i));
        }
        System.out.println(hornaOs);

        //vypis riadky so zvislo osou na zaciatku
        for (int r = 0; r < field.getRowCount(); r++) {
            System.out.printf("%3s", Character.toString(r + 65));
            for (int c = 0; c < field.getColumnCount(); c++) {
                System.out.printf("%3s", field.getTile(r, c));
            }
            System.out.println();
        }


    }

    @Override
    public void play() {
        setting = Settings.load();


        Field field = new Field(
                setting.getRowCount(),
                setting.getColumnCount(),
                setting.getMineCount()
        );

        newGameStarted(field);

    }

    /**
     * Processes user input.
     * Reads line from console and does the action on a playing field according to input string.
     */
    private void processInput() {
        System.out.println("Zadaj svoj vstup.");
        System.out.println("Ocakavany vstup:  X - ukoncenie hry, M - mark/unmark, O - open. Napr.: MA1 - oznacenie dlazdice v riadku A a stlpci 1");
        String playerInput = readLine().toUpperCase(Locale.ROOT);


        if (playerInput.trim().equals("X")) {
            System.out.println("Ukoncujem hru");
            System.exit(0);
        }

        // overi format vstupu - exception handling
        try {
            handleInput(playerInput);
        } catch (WrongFormatException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
            processInput();
        }
    }

    private void doOperation(char operation, char osYRow, int osXCol) {

        int osYRowInt = osYRow - 65;

        // M - oznacenie dlzadice
        if (operation == 'M') {
            field.markTile(osYRowInt, osXCol);

        }

        // O - Odkrytie dlazdice
        if (operation == 'O') {
            if (field.getTile(osYRowInt, osXCol).getState() == Tile.State.MARKED) {
                System.out.println("!!! Nie je mozne odkryt dlazdicu v stave MARKED");
                return;
            } else {
                field.openTile(osYRowInt, osXCol);
            }

        }

        System.out.println("Vykonal som pozadovanu operaciu");
    }

    private boolean isInputInBorderOfField(String suradnicaZvislaPismeno, String suradnicaHorizontalnaCislo) {
        boolean result = true;

        if ((int) suradnicaZvislaPismeno.charAt(0) >= (65 + field.getRowCount())) {
            result = false;
            System.out.print("!!! Pismeno prekracuje pocet riadkov.");
        }
        if (Integer.parseInt(suradnicaHorizontalnaCislo) >= field.getColumnCount()) {
            result = false;
            System.out.print(" !!! Cislo prekracuje pocet stlpcov.");

        }
        if (!result) {
            System.out.println(" Opakuj vstup.");
        }

        return result;
    }

    void handleInput(String playerInput) throws WrongFormatException {
        Matcher matcher1 = OPEN_MARK_PATTERN.matcher(playerInput);

        if (!OPEN_MARK_PATTERN.matcher(playerInput).matches()) {
            throw new WrongFormatException("!!! Zadal si nespravny format vstupu, opakuj vstup.");
        }

        matcher1.find();

        if (!isInputInBorderOfField(matcher1.group(2), matcher1.group(3))) {
            System.out.println();
            processInput();
            return;
        }

        if (OPEN_MARK_PATTERN.matcher(playerInput).matches()) {
            doOperation(matcher1.group(1).charAt(0), matcher1.group(2).charAt(0), Integer.parseInt(matcher1.group(3)));
        }

    }

}