package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Country;
import sk.tuke.gamestudio.entity.Occupation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class OccupationServiceJPA implements OccupationService {

    @PersistenceContext
    private EntityManager entityManager;

    //toto je moja metoda , nie je v interface
    public Occupation getOccupationByName(String uName) {
        try {
            return (Occupation) entityManager
                    .createQuery("select p from Occupation p where p.occupation = :uName")
                    .setParameter("uName", uName).getSingleResult()
                    ;
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }


    @Override
    public List<Occupation> getOccupations() {
        return entityManager
                .createQuery("select o from Occupation o")
                .getResultList()
                ;
    }

    @Override
    public void addOccupation(Occupation occupation) {
        try {
            entityManager.persist(occupation);
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.printf("Objekt s menom pozicie %s uz existuje, zapis neprebehol.%n", occupation.getOccupation());
        }

    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("DELETE FROM occupation").executeUpdate();
    }
}
