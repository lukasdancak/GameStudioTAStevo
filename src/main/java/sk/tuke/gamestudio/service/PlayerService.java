package sk.tuke.gamestudio.service;


import sk.tuke.gamestudio.entity.Player;

import java.util.List;

public interface PlayerService {

    // - vráti zoznam všetkým hráčov z databázy, kde userName = uName
    public List<Player> getPlayersByUserName(String uName);

    // pridá nového hráča do databázy. Dbajte na to, aby sa správne vytvorili prepojenia s entitnými triedami (tabuľkami) Country a Occupation.
    public void addPlayer(Player player);


}
