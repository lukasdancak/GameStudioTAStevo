package sk.tuke.gamestudio.service;


import sk.tuke.gamestudio.entity.Country;

import java.util.List;

public interface CountryService {

    //- vráti zoznam všetkým uložených krajín z databázy
    public List<Country> getCountries() ;

    //- pridá novú krajinu do databázy
    public void addCountry(Country country);


    void reset();



}
