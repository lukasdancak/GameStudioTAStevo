package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Country;
import sk.tuke.gamestudio.entity.Occupation;
import sk.tuke.gamestudio.entity.Player;
import sk.tuke.gamestudio.service.CountryService;
import sk.tuke.gamestudio.service.OccupationService;
import sk.tuke.gamestudio.service.PlayerService;

import java.util.List;

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

        //je vlozeny userName String length >0 a <=32 ???
        if (userName.length() == 0 || userName.length() > 32) {
            inputDataAreOK = false;
            systemMessageController.messagesForUser.add("!!! Dlzka zadaneho userName nesplna podmienky dlzky.");
        }

        //existuje uz hrac s danym username v databaze ??? overi to iba ak zadany username splna podmienky dlzky stringu
        if (inputDataAreOK) {
            List<Player> tempVArForCheckPlayer = null;
            try {
                tempVArForCheckPlayer = playerService.getPlayersByUserName(userName);
            } catch (Exception e) {
                //e.printStackTrace();
                systemMessageController.messagesForUser.add("!!! Problem s databazou. Neviem overit, ci username uz existuje.");
                inputDataAreOK = false;
            }
            if (tempVArForCheckPlayer != null && tempVArForCheckPlayer.isEmpty()) {
                systemMessageController.messagesForUser.add("Username je OK. Hrac danym username neexistuje v databaze.");
                //inputDataAreOK ostava true;
            }
            if (tempVArForCheckPlayer != null && tempVArForCheckPlayer.size() > 0) {
                inputDataAreOK = false;
                systemMessageController.messagesForUser.add("!!! Hrac so zadanym username uz existuje v databaze.");
            }
        }

        //nieje fullName null ? ma fullname aspon jeden znak ? ak je fullname prilis dlhe  (>128) tak ho oseknem
        if (fullName != null && fullName.length() != 0) {
            if (fullName.length() > 128) {
                fullName = fullName.substring(0, 128);
                systemMessageController.messagesForUser.add("=== Skratil som fullname, malo viac ako 128 znakov.");

            }
            systemMessageController.messagesForUser.add("OK- Zadana hodnota fullname je ok.");
            //inputDataAreOK ostava true;

        } else {
            inputDataAreOK = false;
            systemMessageController.messagesForUser.add("!!! Zadane fullname je NULL, alebo prazdny String");
        }

        // kontrolu hesla zatial neriesim, heslo nie je v zadani

        // kontrola selfEvaluation - je to int 1 az 5 vratane ???
        if (selfEvaluation >= 1 && selfEvaluation <= 5) {
            systemMessageController.messagesForUser.add("OK- Zadana hodnota selfEvaluation je v poriadku");
            //inputDataAreOK ostava true;
        } else {
            inputDataAreOK = false;
            systemMessageController.messagesForUser.add("!!! Zadana hodnota selfEvaluation je mimo povoleny rozsah");
        }

        //kontrola Country:  countryName != NULL ? je countryName v databaze ?
        List<Country> tempVArForCheckCountryList = null;
        try {
            tempVArForCheckCountryList = countryService.getCountries();
            systemMessageController.messagesForUser.add("OK- Nacital som Countries z databazy.");

        } catch (Exception e) {
            //e.printStackTrace();
            systemMessageController.messagesForUser.add("!!! Problem s databazou. Neviem ziskat zoznam krajin z databazy");
            inputDataAreOK = false;
        }
        Country tempVarForCheckCountry2 = null;
        if (tempVArForCheckCountryList != null) {
            tempVarForCheckCountry2 = returnCountryFromListWithName(tempVArForCheckCountryList, countryName);
        }

        if (countryName != null) {
            if (tempVarForCheckCountry2 != null) {
                systemMessageController.messagesForUser.add("OK- Zadana hodnota countryName  je v poriadku, nachadza sa v databaze");
                //inputDataAreOK ostava true;
            } else {
                systemMessageController.messagesForUser.add("!!! Zadana hodnota countryName  sa nenachadza v databaze");
                inputDataAreOK = false;
            }
        } else {
            systemMessageController.messagesForUser.add("!!! Zadana hodnota countryName je NULL");
            inputDataAreOK = false;
        }

        //kontrola Occupation:  occupationName != NULL ? je occupationName v databaze ?
        List<Occupation> tempVArForCheckOccupationList = null;
        try {
            tempVArForCheckOccupationList = occupationService.getOccupations();
            systemMessageController.messagesForUser.add("OK- Nacital som Occupations z databazy.");

        } catch (Exception e) {
            //e.printStackTrace();
            systemMessageController.messagesForUser.add("!!! Problem s databazou. Neviem ziskat zoznam zam. pozicii z databazy");
            inputDataAreOK = false;
        }
        Occupation tempVarForCheckOccupation2 = null;
        if (tempVArForCheckOccupationList != null) {
            tempVarForCheckOccupation2 = returnOccupationFromListWithName(tempVArForCheckOccupationList, occupationName);
        }

        if (occupationName != null) {
            if (tempVarForCheckOccupation2 != null) {
                systemMessageController.messagesForUser.add("OK- Zadana hodnota occupationName  je v poriadku, nachadza sa v databaze");
                //inputDataAreOK ostava true;
            } else {
                systemMessageController.messagesForUser.add("!!! Zadana hodnota occupationName  sa nenachadza v databaze");
                inputDataAreOK = false;
            }
        } else {
            systemMessageController.messagesForUser.add("!!! Zadana hodnota occupationName je NULL");
            inputDataAreOK = false;
        }


        //ak data nie su v poriadku > redirect na registracnu stranku, tam sa zobrazia chybove hlasky pre uzivatela
        if (!inputDataAreOK) {
            systemMessageController.messagesForUser.add("!!! Data, ktore si zadal nie su v poriadku. Prosim opakuj registraciu.");
            return "redirect:/registration";
        }

        //pridam spravu, ze data boli spravne
        systemMessageController.messagesForUser.add("OK- Data, ktore si zadal su v poriadku.");

        //data su v poriadku vytvori playera
        Player newPlayer =
                new Player(userName, fullName, selfEvaluation, tempVarForCheckCountry2, tempVarForCheckOccupation2);


        // prida playera do databazy
        try {
            playerService.addPlayer(newPlayer);
            systemMessageController.messagesForUser.add("Tvoj hracsky profil bol uspesne vlozeny do databazy.");
            systemMessageController.messagesForUser.add("Teraz sa mozes prihlasit.");
        } catch (Exception e) {
            //e.printStackTrace();
            systemMessageController.messagesForUser.add("Nastal problem s databazou, tvoj hracsky profil nebol ulozeny.");
            systemMessageController.messagesForUser.add("Prosim opakuj registraciu");
        }
        return "redirect:/";
    }

    private Occupation returnOccupationFromListWithName(List<Occupation> occupationsList, String occupationName) {
        for (Occupation o : occupationsList) {
            if (o.getOccupation().equals(occupationName)) {
                return o;
            }
        }
        return null;

    }

    private Country returnCountryFromListWithName(List<Country> countriesList, String countryName) {
        for (Country c : countriesList) {
            if (c.getCountry().equals(countryName)) {
                return c;
            }
        }
        return null;
    }

    public String getLoggedUser() {
        return loggedUser;
    }

    public boolean isLogged() {
        return !(loggedUser == null);
    }
}
