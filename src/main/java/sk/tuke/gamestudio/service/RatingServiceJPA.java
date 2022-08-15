package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
public class RatingServiceJPA implements RatingService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) {

        try {
            Rating ratingWrite = (Rating) entityManager.createQuery("select r from Rating r " +
                            "where r.username=:user and r.game=:game")
                    .setParameter("user", rating.getUsername())
                    .setParameter("game", rating.getGame())
                    .getSingleResult();
            ratingWrite.setRating(rating.getRating());
            ratingWrite.setRatedOn(rating.getRatedOn());
        } catch (NoResultException e) {
            // e.printStackTrace();

            entityManager.persist(rating);

        }

    }

    @Override
    public int getAverageRating(String game) {

        try {
            return ((Number) entityManager
                    .createQuery("SELECT ROUND(AVG(rating)) FROM Rating where game= :myGame")
                    .setParameter("myGame", game)
                    .getSingleResult())
                    .intValue();
        } catch (NoResultException | NullPointerException e) {
            return 0;
        }
    }

    @Override
    public int getRating(String game, String username) {
        try {
            return ((Number) entityManager
                    .createQuery("SELECT rating FROM Rating WHERE game= :myGame and username= :myUser")
                    .setParameter("myGame", game)
                    .setParameter("myUser", username)
                    .getSingleResult())
                    .intValue();
        } catch (NoResultException | NullPointerException e) {
            return 0;
        }
    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("DELETE FROM rating").executeUpdate();

    }
}
