package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.List;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class GamestudioController {

    @Autowired
    ScoreService scoreService;
    @Autowired
    CommentService commentService;
    @Autowired
    RatingService ratingService;


    @RequestMapping("/")
    public String mainPage(Model model){
        // skryje fragmenty pre komntare a score - nechcem ich na homepage
        model.addAttribute("hideCommentsScores", true);
        return "gamestudio";
    }

    @RequestMapping("/games")
    public String gamesPage(){
        return "redirect:/gamestudio";
    }

    //ak nastane problem s databazou vrati NULL, a to mam osetrene vypisom vo fragmente SCORES
    public List<Score> getTopScoresOfGame(String gameName){
        List<Score> topScores=null;
        try {
            topScores=scoreService.getBestScores(gameName);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return topScores;
    }

    //ak nastane problem s databazou vrati NULL, a to mam osetrene vypisom vo fragmente COMMENTS
    public List<Comment> getAllCommentsOfGame(String gameName) {
        List<Comment> allComments = null;
        try {
            allComments = commentService.getComments(gameName);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return allComments;
    }

    //ak nastane problem s databazou vrati (-1), a to mam osetrene vypisom vo fragmente avgrating
    public int getAverageRatingOfGame(String gameName) {
        int averageRating = -1;
        try {
            averageRating = ratingService.getAverageRating(gameName);
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return averageRating;

    }


}
