package dao;

import jakarta.persistence.EntityManagerFactory;
import model.Movie;

public class MovieDAO {

    private static MovieDAO instance;
    private static EntityManagerFactory emf;

    public static MovieDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieDAO();
        }
        return instance;
    }

    public Movie getMovieById(int id) {
        Movie movie = null;

        try (var em = emf.createEntityManager()) {
           movie = em.find(Movie.class, id);

        }
        return movie;
    }

    public int saveMovie(Movie movie) {

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
        }
        return movie.getId();

    }

    public Movie updateMovie(Movie movie) {
            try (var em = emf.createEntityManager()) {
                em.getTransaction().begin();
                em.merge(movie);
                em.getTransaction().commit();
            }
            return movie;
    }

    public Movie deleteMovie(Movie movie) {
            try (var em = emf.createEntityManager()) {
                em.getTransaction().begin();
                em.remove(movie);
                em.getTransaction().commit();
            }
            return movie;
    }



}
