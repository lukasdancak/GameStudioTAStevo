package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Player;
import sk.tuke.gamestudio.entity.Score;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class PlayerServiceJPA implements PlayerService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Player> getPlayersByUserName(String uName) {
        return entityManager
                .createQuery("select p from Player p where p.username = :uName")
                .setParameter("uName", uName).getResultList()
                ;
    }

    @Override
    public void addPlayer(Player player) {
        entityManager.persist(player);

    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("DELETE FROM player").executeUpdate();
    }
}
