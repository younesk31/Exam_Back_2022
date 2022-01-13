package facades;

import datamappers.MovieMapper;
import dtos.MovieDTO;
import entities.Assignment;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class MovieFacade {


    private static EntityManagerFactory emf;
    private static MovieFacade facade;


    private MovieFacade() {
    }


    public static MovieFacade getMovieFacade(EntityManagerFactory _emf) {
        if (facade == null) {
            emf = _emf;
            facade = new MovieFacade();
        }
        return facade;
    }


    public Assignment addlikeToMovie(String id) {
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


    public String addWatchLater(String username, String id) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            User user = em.find(User.class, username);
            em.merge(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return "added "+ id;
    }


    public String deleteWatchLater(String username,String id) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            User user = em.find(User.class, username);
            em.merge(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return "Deleted! " + id;
    }


    public List<MovieDTO> getWatchLaterList(String username) throws Exception {
        EntityManager em = emf.createEntityManager();
        List<MovieDTO> movieDTOList = new ArrayList<>();
        MovieMapper movieMapper = new MovieMapper();
        User user;

        try {
            em.getTransaction().begin();
            user = em.find(User.class, username);
            em.getTransaction().commit();
        } finally {
            em.close();
        }



        movieDTOList = (movieMapper.getMovieById(movieDTOList));

        return movieDTOList;
    }

    public List<MovieDTO> getTopLikedList() throws Exception {
        EntityManager em = emf.createEntityManager();
        List<MovieDTO> movieDTOList = new ArrayList<>();
        MovieMapper movieMapper = new MovieMapper();
        List<Assignment> movieLikes;

        movieDTOList = movieMapper.getMovieById(movieDTOList);

        return movieDTOList;
    }


}
