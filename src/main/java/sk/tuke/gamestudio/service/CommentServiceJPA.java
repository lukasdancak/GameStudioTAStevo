package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class CommentServiceJPA implements CommentService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addComment(Comment comment) {
        entityManager.persist(comment);

    }

    //vrati vsetky komentare v danej hre
    @Override
    public List<Comment> getComments(String game) {
                return entityManager
                .createQuery("select c from Comment c where c.game = :mygame")
                .setParameter("mygame", game).getResultList()
        ;

    }

    @Override
    public void reset() {
    entityManager.createNativeQuery("DELETE FROM comment").executeUpdate();
    }
}
