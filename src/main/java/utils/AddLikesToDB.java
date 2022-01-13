package utils;

import entities.Assignment;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class AddLikesToDB {

    public Assignment addlikeToMovie(String id, long likes) {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        Assignment like;
        try {
            em.getTransaction().begin();
            like = em.find(Assignment.class, id);
            if (like == null) {

            } else {

                em.merge(like);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return like;
    }
}
