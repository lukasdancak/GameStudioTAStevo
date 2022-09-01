package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Country;
import sk.tuke.gamestudio.entity.Player;
import sk.tuke.gamestudio.entity.Score;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class CountryServiceJPA implements CountryService {

    @PersistenceContext
    private EntityManager entityManager;

    //moja metoda , nie je v interface
    public Country getCountryByName(String uName) {
        try {
            return (Country) entityManager
                    .createQuery("select p from Country p where p.country = :uName")
                    .setParameter("uName", uName).getSingleResult();
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }

    }


    @Override
    public List<Country> getCountries() {
        return entityManager
                .createQuery("select c from Country c")
                .getResultList()
                ;
    }

    @Override
    public void addCountry(Country country) {
        try {
            entityManager.persist(country);
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.printf("Objekt s menom krajiny %s uz existuje, zapis neprebehol.%n", country.getCountry());
        }

    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("DELETE FROM country").executeUpdate();
    }
}
