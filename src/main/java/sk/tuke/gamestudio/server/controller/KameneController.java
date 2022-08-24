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
import sk.tuke.gamestudio.minesweeper.core.Clue;
import sk.tuke.gamestudio.minesweeper.core.GameState;
import sk.tuke.gamestudio.kamene.core.Tile;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Date;

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
    private UserController userController;

    Field field = new Field(3,3);


    @RequestMapping("/tileup")
    public String moveTileUp(){
        field.moveUp();

        return"redirect:/kamene";
    }

    @RequestMapping("/tiledown")
    public String moveTileDown(){
        field.moveDown();

        return"redirect:/kamene";
    }

    @RequestMapping("/tileleft")
    public String moveTileLeft(){
        field.moveLeft();

        return"redirect:/kamene";
    }

    @RequestMapping("/tileright")
    public String moveTileRight(){
        field.moveRight();

        return"redirect:/kamene";
    }

    @RequestMapping
    public String kamene(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column, Model model){
        if(userController.getLoggedUser()==null){
            return "redirect:/gamestudio";
        }


        prepareModel(model);
        return "kamene";
    }


//skontrolovat
    @RequestMapping("/sendcomment")
    public String createComment(String comment){
        commentService.addComment( new Comment("kamene", userController.getLoggedUser(), comment, new Date()) );


        return "redirect:/minesweeper";
    }
//skontorlovat
    @RequestMapping("/sendrating")
    public String createOrUpdateRating(int rating){
        if(rating>0 && rating <6) {
            ratingService.setRating(new Rating("kamene", userController.getLoggedUser(), rating, new Date()));
        }

        return "redirect:/minesweeper";
    }

    public String getTileNum(Tile tile) {
        if(tile.getValue()==0){return "";} else{
            return Integer.toString( tile.getValue() );
        }

    }


    private void prepareModel(Model model){

        model.addAttribute("kameneField", field.getTiles());




    }






















}