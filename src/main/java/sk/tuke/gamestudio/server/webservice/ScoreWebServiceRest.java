package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.List;

@RestController
@RequestMapping("/api/score")
public class ScoreWebServiceRest {

    @Autowired
    private ScoreService scoreService;

    //http://localhost:8080/api/score/minesweeper
    @GetMapping("/{game}")
    public List<Score> getBestScores(@PathVariable String game){
        return scoreService.getBestScores (game);
    }

    /*
    //http://localhost:8080/api/score?game=minesweeper
    @GetMapping
    public List<Score> getBestScores(String game){
        return scoreService.getBestScores (game);
    }
     */

    @PostMapping
    public void addScore(@RequestBody Score score){scoreService.addScore(score);}

}