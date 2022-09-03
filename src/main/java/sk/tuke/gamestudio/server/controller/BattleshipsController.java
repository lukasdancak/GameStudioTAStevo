package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.battleships.core.Fields;
import sk.tuke.gamestudio.battleships.core.GameState;
import sk.tuke.gamestudio.battleships.core.Tile;
import sk.tuke.gamestudio.battleships.core.Water;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Date;

@Controller
@RequestMapping("/battleships")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class BattleshipsController {

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

    private Fields fields = new Fields();

//    private boolean marking = false;
//
//    private GameState gameState = GameState.PLAYING;
//
//    private boolean isPlaying = true;


    @RequestMapping
    public String battleships(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column, Model model) {

        if (userController.getLoggedUser() == null) {
            return "redirect:/";
        }

        if (row != null && column != null) {
            fields.hitEnemyAndGetHit(row, column);

        }

        prepareModel(model);

        if (fields.getGameState() == GameState.WIN) {

            if (userController.isLogged() && fields.getScore() != 0) {
                gamestudioController.addScoreToDatabase("battleships", fields.getScore());

                fields.setScore(0); // iba jeden pokus na zapisanie skore do databazy

            }

        }


        return "battleships";
    }


    @RequestMapping("/new")
    public String newGame() {
        fields = new Fields();
        return "redirect:/battleships";
    }

    public String getTileText(Tile tile) {
        return tile.toString();
    }

    //vrati ci je closed, water ship
    public String getTileClassByVisbility(Tile tile) {
        switch (tile.getVisibilityState()) {
            case CLOSED:
                return "closed";
            case OPEN:
                if (tile instanceof Water) {
                    return "water";
                } else {
                    return "ship";
                }

            default:
                throw new RuntimeException("Unexpected tile state");
        }
    }

    //vrati ci dlazdica bola HIT, alebo je UNHIT - preto aby na hracovom poli zobrazovalo zasahy supera(pocitaca)
    public String getTileClassByHitState(Tile tile) {
        switch (tile.getHitState()) {
            case HIT:

                return "hit";
            case UNHIT:
                return "";

            default:
                throw new RuntimeException("Unexpected tile state");
        }
    }

    //vrati ci dlazdica bola HIT, alebo je UNHIT - preto aby na hracovom poli zobrazovalo zasahy supera(pocitaca)
//    public String getTileClassByTileType(Tile tile) {
//        if (tile instanceof Water) {
//            return "water";
//        } else {
//            return "ship";
//        }
//    }

    private void prepareModel(Model model) {

        boolean shouldContinue = true;
        if (fields.getGameState() == GameState.FAILED || fields.getGameState() == GameState.WIN) {
            shouldContinue = false;
        }
        int win1vslose2 = 0;
        if (fields.getGameState() == GameState.WIN) {
            win1vslose2 = 1;
        }
        if (fields.getGameState() == GameState.FAILED) {
            win1vslose2 = 2;
        }
        model.addAttribute("shipsToFind", fields.getComputersField().getRemainingShipsTilesCount());
        model.addAttribute("computersShipsToFind", fields.getPlayersField().getRemainingShipsTilesCount());
        model.addAttribute("battleshipsField", fields.getComputersField().getTiles());
        model.addAttribute("battleshipsField2", fields.getPlayersField().getTiles());

        model.addAttribute("battleshipsWinLose", win1vslose2);
        model.addAttribute("battleshipsShouldContinue", shouldContinue);
        model.addAttribute("battleshipsPlayerScore", String.valueOf(fields.getScore()));
        // skore sa zobrazi iba raz


        //meno hry v databaze a zaroven adresa linku controllera
        String gameName = "battleships";
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