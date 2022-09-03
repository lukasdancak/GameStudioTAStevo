package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;

import sk.tuke.gamestudio.kamene.core.Field;
import sk.tuke.gamestudio.kamene.core.GameState;
import sk.tuke.gamestudio.minesweeper.core.Clue;
import sk.tuke.gamestudio.kamene.core.Tile;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/kamene")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class KameneController {

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

    Field field = new Field(3, 3);


    @RequestMapping("/tileup")
    public String moveTileUp() {
        field.moveUp();

        return "redirect:/kamene";
    }

    @RequestMapping("/tiledown")
    public String moveTileDown() {
        field.moveDown();

        return "redirect:/kamene";
    }

    @RequestMapping("/tileleft")
    public String moveTileLeft() {
        field.moveLeft();

        return "redirect:/kamene";
    }

    @RequestMapping("/tileright")
    public String moveTileRight() {
        field.moveRight();

        return "redirect:/kamene";
    }

    @RequestMapping
    public String kamene(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column, Model model) {
        if (userController.getLoggedUser() == null) {
            return "redirect:/";
        }
        //ak je hrac prihlaseny a uspesne ukonci hru, tak zapise skore do databazy
        if (userController.isLogged() && this.field.getGamestate() == GameState.SOLVED) {
            gamestudioController.addScoreToDatabase("kamene", this.field.getScore());
        }
        prepareModel(model);
        return "kamene";
    }


    @RequestMapping("/new")
    public String newGame(Model model) {
        field = new Field(3, 3);
        return "redirect:/kamene";
    }


//    //skontrolovat
//    @RequestMapping("/sendcomment")
//    public String createComment(String comment) {
//        commentService.addComment(new Comment("kamene", userController.getLoggedUser(), comment, new Date()));
//
//
//        return "redirect:/minesweeper";
//    }
//
//    //skontorlovat
//    @RequestMapping("/sendrating")
//    public String createOrUpdateRating(int rating) {
//        if (rating > 0 && rating < 6) {
//            ratingService.setRating(new Rating("kamene", userController.getLoggedUser(), rating, new Date()));
//        }
//
//        return "redirect:/minesweeper";
//    }


    public String getTileNum(Tile tile) {
        if (tile.getValue() == 0) {
            return "";
        } else {
            return Integer.toString(tile.getValue());
        }
    }

    private void prepareModel(Model model) {
        boolean shouldContinue = true;
        if (field.getGamestate() == GameState.SOLVED) {
            shouldContinue = false;
        }


        model.addAttribute("kameneField", field.getTiles());
        model.addAttribute("kameneShouldContinue", shouldContinue);
        model.addAttribute("kamenePlayerScore", String.valueOf(field.getScore()));


        //meno hry v databaze a zaroven adresa linku controllera
        String gameName = "kamene";
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