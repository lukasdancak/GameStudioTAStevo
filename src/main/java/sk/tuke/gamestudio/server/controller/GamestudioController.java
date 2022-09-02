package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    UserController userController;
    @Autowired
    SystemMessageController systemMessageController;


    @RequestMapping("/")
    public String mainPage(Model model) {
        // skryje fragmenty pre komntare a score - nechcem ich na homepage
        // model.addAttribute("hideCommentsScores", true);
        systemMessageController.getMessagesForUser().add("Toto je kontrolna sprava. Tuto spravu ma byt vidno len na homepage");

        return "gamestudio";
    }

    @RequestMapping("/games")
    public String gamesPage() {
        return "redirect:/";
    }

    //ak nastane problem s databazou vrati NULL, a to mam osetrene vypisom vo fragmente SCORES
    public List<Score> getTopScoresOfGame(String gameName) {
        List<Score> topScores = null;
        try {
            topScores = scoreService.getBestScores(gameName);
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

    //mapovanie pre pridanie komentu-univerzalne pre akukolvek hru - parametrizovany fragment
    @RequestMapping("/sendcomment")
    public String createComment(String comment, String gameName) {
        //odsekne koniec stringu ak ma viac ako 1000 znakov
        if (comment.length() > 1000) {
            comment = comment.substring(0, 1000);
        }
        try {
            commentService
                    .addComment(new Comment(gameName, userController.getLoggedUser(), comment, new Date()));
        } catch (Exception e) {
            //e.printStackTrace();
            //pridat return na stranku s hlasenim chyby

        }


        return "redirect:/" + gameName;
    }

    //mapovanie pre pridanie/upddate ratingu -univerzalne pre akukolvek hru - parametrizovany fragment
    @RequestMapping("/sendrating")
    public String createOrUpdateRating(int rating, String gameName) {
        if (rating > 0 && rating < 6) {
            try {
                ratingService.setRating(new Rating(gameName, userController.getLoggedUser(), rating, new Date()));
            } catch (Exception e) {
                //e.printStackTrace();
                //pridat return na stranku s hlasenim chyby
            }
        }

        return "redirect:/" + gameName;
    }


}
