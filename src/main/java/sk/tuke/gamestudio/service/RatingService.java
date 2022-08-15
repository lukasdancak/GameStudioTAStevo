package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;

import java.util.List;

public interface RatingService {

    //); - zapíše hodnotenie do tabuľky rating. Ak už v tabuľke hodnotenie od daného hráča je, toto sa prepíše.
    // Ak nie, pridá sa nový záznam (riadok).
    void setRating(Rating rating);

    //vráti priemerné hodnotenie hry, zaokrúhlené na celé číslo.
    int getAverageRating(String game);

    //vráti hodnotenie hry s názvom game od hráča s menom username.
    int getRating(String game, String username);

    //vymaže údaje v tabuľke rating.
    void reset();
}
