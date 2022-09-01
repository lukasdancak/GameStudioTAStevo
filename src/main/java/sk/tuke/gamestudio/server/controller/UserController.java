package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Player;
import sk.tuke.gamestudio.service.CountryService;
import sk.tuke.gamestudio.service.OccupationService;
import sk.tuke.gamestudio.service.PlayerService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {
    @Autowired
    CountryService countryService;
    @Autowired
    OccupationService occupationService;
    @Autowired
    PlayerService playerService;
    @Autowired
    SystemMessageController systemMessageController;

    private String loggedUser;

    @RequestMapping("/login")
    public String login(String login, String password) {
        if ("heslo".equals(password)) {
            this.loggedUser = login.trim();
            if (this.loggedUser.length() > 0) {
                return "redirect:/";
            }

        }
        this.loggedUser = null; // neprijal som uzivatela -->> explicitne nastavenie uzivatela na null
        return "redirect:/";
    }


    @RequestMapping("/logout")
    public String logout() {

        this.loggedUser = null;
        return "redirect:/";
    }

    @RequestMapping("/registration")
    public String registration(Model model) {
        if (isLogged()) {
            return "redirect:/";
        }
        model.addAttribute("MessagesForUser", systemMessageController.messagesForUser);
        model.addAttribute("hideLoginForm", true);
        model.addAttribute("countries", countryService.getCountries());
        model.addAttribute("occupations", occupationService.getOccupations());
        return "registration";
    }

    @RequestMapping("/registernew")
    public String proccesDataFromRegistrationForm(String userName, String fullName, int selfEvaluation, String password,
                                                  String countryName, String occupationName) {
        /******* Kontrola vstupnych dat ************/

        // ak bude niektory vstup zly, premenna sa zmeni na false
        boolean inputDataAreOK = true;
        //je vlozeny userName String length >0 a <=32
        if (userName.length() == 0 || userName.length() > 32) {
            inputDataAreOK = false;
            systemMessageController.messagesForUser.add("Dlzka zadaneho userName nesplna podmienky dlzky.");
        }

        //existuje uz taky user?

        //ak je fullname prilis dlhe tak ho oseknem

        //ak data nie su v poriadku > redirect na registracnu stranku, tam sa zobrazia chybove hlasky pre uzivatela
        if (!inputDataAreOK) {
            return "redirect:/registration";
        }

        //ak su  data v poriadku vytvori playera a redirektne na homepage,
        // ak bude cas zobrazi hlasku o uspesnom zaregistrovani a vyzvu na prihlasenie
        Player newPlayer = new Player();

        // prida playera do databazy
        //playerService.addPlayer(newPlayer);
        systemMessageController.messagesForUser.add("Novy Player bol uspesne zaregistrovany a vlozeny do databazy");


        return "redirect:/";
    }

    public String getLoggedUser() {
        return loggedUser;
    }

    public boolean isLogged() {
        return !(loggedUser == null);
    }
}
