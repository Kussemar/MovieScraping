package dao;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import model.Movie;

import java.util.List;

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

    //getAll
    public Movie[] getAllMovies() {
        Movie[] movies = null;
        try (var em = emf.createEntityManager()) {
            Query query = em.createQuery("SELECT m FROM Movie m");
            return (Movie[]) query.getResultList().toArray(new Movie[0]);
        }
    }

    //getByImdbId
    public Movie getMovieByImdbId(String imdbId) {
        Movie movie = null;
        try (var em = emf.createEntityManager()) {
            Query query = em.createQuery("SELECT m FROM Movie m WHERE m.imdbId = :imdbId");
            query.setParameter("imdbId", imdbId);
            return movie = (Movie) query.getSingleResult();
        }
    }
    //getByRating
    public List<Movie> getMoviesByRating(double rating) {
        try (var em = emf.createEntityManager()) {
            return em.createQuery("SELECT m FROM Movie m WHERE m.rating = :rating", Movie.class)
                    .setParameter("rating", rating)
                    .getResultList();
        }
    }

    public List<Movie> getMoviesSortedByReleaseDate() {
        try (var em = emf.createEntityManager()) {
            return em.createQuery("SELECT m FROM Movie m ORDER BY m.releaseYear", Movie.class)
                    .getResultList();
        }


    }
}
