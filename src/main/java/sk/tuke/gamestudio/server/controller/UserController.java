package sk.tuke.gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {

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
        model.addAttribute("hideLoginForm", true);
        return "registration";
    }

    public String getLoggedUser() {
        return loggedUser;
    }

    public boolean isLogged() {
        return !(loggedUser == null);
    }
}
