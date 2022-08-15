package sk.tuke.gamestudio.service;


import sk.tuke.gamestudio.entity.Occupation;

import java.util.List;

public interface OccupationService {

    // - vráti zoznam všetkým uložených typov povolaní z databázy
    public List<Occupation> getOccupations();

    // - pridá nový typ typom povolania do databázy.
    public void addOccupation(Occupation  occupation);




}
