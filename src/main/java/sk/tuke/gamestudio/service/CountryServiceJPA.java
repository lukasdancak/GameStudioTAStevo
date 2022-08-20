package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Country;
import sk.tuke.gamestudio.entity.Score;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class CountryServiceJPA implements CountryService{

    @PersistenceContext
    private EntityManager entityManager;


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
