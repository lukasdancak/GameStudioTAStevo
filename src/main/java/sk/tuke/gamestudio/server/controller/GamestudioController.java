package sk.tuke.gamestudio.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GamestudioController {


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


}
