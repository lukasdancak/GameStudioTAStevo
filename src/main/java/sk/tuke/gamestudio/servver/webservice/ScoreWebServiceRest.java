package sk.tuke.gamestudio.servver.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.tuke.gamestudio.service.ScoreService;

@RestController
@RequestMapping("/api")
public class ScoreWebServiceRest {

    @Autowired
    ScoreService scoreService;

    @GetMapping("/score")
    public String someSimpleService(){
        return "Ahoj";
    }
}
