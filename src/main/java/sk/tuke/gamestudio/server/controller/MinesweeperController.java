package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.minesweeper.core.Clue;
import sk.tuke.gamestudio.minesweeper.core.Field;
import sk.tuke.gamestudio.minesweeper.core.GameState;
import sk.tuke.gamestudio.minesweeper.core.Tile;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/minesweeper")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MinesweeperController {

    @Autowired
    ScoreService scoreService;
    @Autowired
    CommentService commentService;
    @Autowired
    RatingService ratingService;
    @Autowired
    GamestudioController gamestudioController;
    @Autowired
    private UserController userController;
    @Autowired
    SystemMessageController systemMessageController;

    private Field field = new Field(9, 9, 10);

    private boolean marking = false;

    private GameState gameState = GameState.PLAYING;

    private boolean isPlaying = true;


    @RequestMapping("/asynch")
    public String loadInAsynchMode() {
        startOrUpdateGame(null, null);
        return "minesweeperAsynch";
    }

    @RequestMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Field processUserInputJson(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column) {
        boolean justFinished = startOrUpdateGame(row, column);
        this.field.setJustFinished(justFinished);
        this.field.setMarking(marking);
        return this.field;
    }

    @RequestMapping(value = "/jsonmark", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Field changeMarkingJson() {
        switchMode();
        this.field.setJustFinished(false);
        this.field.setMarking(marking);
        return this.field;
    }

    @RequestMapping(value = "/jsonnew", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Field newGameJson() {
        startNewGame();
        this.field.setJustFinished(false);
        this.field.setMarking(marking);
        return this.field;
    }

    //metody od steva

    private void startNewGame() {
        this.field = new Field(9, 9, 3);
        this.isPlaying = true;
        this.marking = false;
    }

    private void switchMode() {
        if (this.field.getState() == GameState.PLAYING) {
            this.marking = !this.marking;
        }
    }

    /**
     * Updates the game field and other variables according to the move of the user
     * Also adds the score to the score table if the game just ended.
     * If the game did not start yet, starts the game.
     *
     * @param row    row of the tile on which the user clicked
     * @param column column of the tile on which the user clicked
     */
    private boolean startOrUpdateGame(Integer row, Integer column) {
        boolean justFinished = false;
        if (field == null) {
            startNewGame();
        }

        if (row != null && column != null) {

            if (this.marking) {
                this.field.markTile(row, column);
            } else {
                this.field.openTile(row, column);
            }


            if (this.field.getState() != GameState.PLAYING && this.isPlaying == true) { //I just won/lose
                this.isPlaying = false;

                justFinished = true;

                //ak je hrac prihlaseny a uspesne ukonci hru, tak zapise skore do databazy
                if (userController.isLogged() && this.field.getState() == GameState.SOLVED) {
                    Score newScore = new Score("minesweeper", userController.getLoggedUser(), this.field.getScore(), new Date());
                    try {
                        scoreService.addScore(newScore);
                    } catch (Exception e) {
                        //e.printStackTrace();
                        // vypise hlasku na stranke,ze neulozilo skore kvoli problemu s databazou
                        systemMessageController.messagesForUser.add("!!! Problem s databazou, tvoje skore bohuzial nebolo ulozene.");
                    }

                }
            }
        }
        return justFinished;
    }

    @RequestMapping("/sendcomment")
    public String createComment(String comment) {
        commentService.addComment(new Comment("minesweeper", userController.getLoggedUser(), comment, new Date()));


        return "redirect:/minesweeper";
    }

    @RequestMapping("/sendrating")
    public String createOrUpdateRating(int rating) {
        if (rating > 0 && rating < 6) {
            ratingService.setRating(new Rating("minesweeper", userController.getLoggedUser(), rating, new Date()));
        }

        return "redirect:/minesweeper";
    }


    @RequestMapping
    public String minesweeper(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column, Model model) {

        if (userController.getLoggedUser() == null) {
            return "redirect:/";
        }

        if (row != null && column != null) {

            if (marking) {
                field.markTile(row, column);
            } else {
                field.openTile(row, column);
            }
        }

        if (field.getState() != GameState.PLAYING && field.getScore() != 0) {

            if (userController.isLogged()) {
                scoreService.addScore(new Score("minesweeper", userController.getLoggedUser(), field.getScore(), new Date()));

            }

        }

        prepareModel(model);
        return "minesweeper";
    }

    @RequestMapping("/mark")
    public String changeMarking(Model model) {
        marking = !marking;
        prepareModel(model);
        return "minesweeper";
    }

    @RequestMapping("/new")
    public String newGame(Model model) {
        field = new Field(9, 9, 10);
        return "redirect:/minesweeper";
    }

    public String getCurrTime() {
        return new Date().toString();
    }

    public boolean getMarking() {
        return marking;
    }
    /*
            sb.append("<table class='minefield'>\n");
        for (int row = 0; row < field.getRowCount(); row++) {
        sb.append("<tr>\n");
        for (int column = 0; column < field.getColumnCount(); column++) {
            var tile = field.getTile(row, column);
            sb.append("<td class='" + getTileClass(tile) + "'>\n");
            if (tile.getState() != Tile.State.OPEN)
                sb.append("<a href='/minesweeper?row=" + row + "&column=" + column + "'>\n");
            sb.append("<span>" + getTileText(tile) + "</span>");
            if (tile.getState() != Tile.State.OPEN)
                sb.append("</a>\n");
            sb.append("</td>\n");
        }
        sb.append("</tr>\n");
    }
        sb.append("</table>\n");
*/

    public String getFieldAsHtml() {

        int rowCount = field.getRowCount();
        int colCount = field.getColumnCount();

        StringBuilder sb = new StringBuilder();

        sb.append("<table class='minefield'>\n");

        for (int row = 0; row < rowCount; row++) {
            sb.append("<tr>\n");

            for (int col = 0; col < colCount; col++) {
                Tile tile = field.getTile(row, col);

                sb.append("<td class='" + getTileClass(tile) + "'> ");
                sb.append("<a href='/minesweeper/?row=" + row + "&column=" + col + "'> ");
                sb.append("<span>" + getTileText(tile) + "</span>");
                sb.append(" </a>\n");
                sb.append(" </td>\n");

            }


            sb.append("</tr>\n");
        }


        sb.append("</table>\n");

        return sb.toString();
    }

    public String getTileText(Tile tile) {
        switch (tile.getState()) {
            case CLOSED:
                return "-";
            case MARKED:
                return "M";
            case OPEN:
                if (tile instanceof Clue) {
                    return String.valueOf(((Clue) tile).getValue());
                } else {
                    return "X";
                }
            default:
                throw new IllegalArgumentException("Unsupported tile state " + tile.getState());
        }
    }

    public String getTileClass(Tile tile) {
        switch (tile.getState()) {
            case OPEN:
                if (tile instanceof Clue)
                    return "open" + ((Clue) tile).getValue();
                else
                    return "mine";
            case CLOSED:
                return "closed";
            case MARKED:
                return "marked";
            default:
                throw new RuntimeException("Unexpected tile state");
        }
    }

    private void prepareModel(Model model) {

        boolean shouldContinue = true;
        if (field.getState() == GameState.FAILED || field.getState() == GameState.SOLVED) {
            shouldContinue = false;
        }
        int win1vslose2 = 0;
        if (field.getState() == GameState.SOLVED) {
            win1vslose2 = 1;
        }
        if (field.getState() == GameState.FAILED) {
            win1vslose2 = 2;
        }

        model.addAttribute("minesweeperField", field.getTiles());
        model.addAttribute("minesweeperWinLose", win1vslose2);
        model.addAttribute("minesweeperShouldContinue", shouldContinue);
        model.addAttribute("minesweeperPlayerScore", String.valueOf(field.getScore()));

        //meno hry v databaze a zaroven adresa linku controllera
        String gameName = "minesweeper";
        //data do fragmentov
        model.addAttribute("GameName", gameName);
        model.addAttribute("TopScores", gamestudioController
                .getTopScoresOfGame(gameName));
        model.addAttribute("AllComments", gamestudioController
                .getAllCommentsOfGame(gameName));
        model.addAttribute("AverageRating", gamestudioController
                .getAverageRatingOfGame(gameName));


    }


}