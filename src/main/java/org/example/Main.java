package org.example;

import config.HibernateConfig;
import dao.MovieDAO;
import model.Movie;
import utils.Scraper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        HibernateConfig.addAnnotatedClasses(Movie.class);
        var emf = HibernateConfig.getEntityManagerFactoryConfig("imdb_movies");
            MovieDAO movieDAO = MovieDAO.getInstance(emf);

        Movie[] movies = movieDAO.getAllMovies();
        System.out.println("\nGet All Movies:");
        for (Movie movie : movies) {
            System.out.println(movie);
        }

        System.out.println("\nGet Movie By Id: " + movieDAO.getMovieById(1));

        System.out.println("\nGet Movie By Rating: ");
        List<Movie> highRatedMovies = movieDAO.getMoviesByRating(8.0);
        for (Movie movie : highRatedMovies) {
            System.out.println(movie);
        }

        System.out.println("\nGet Movie By Release Year: ");
        List<Movie> sortedMovieByRealsedate = movieDAO.getMoviesSortedByReleaseDate();
        for (Movie movie : sortedMovieByRealsedate) {
            System.out.println(movie);
        }

       /* System.out.println("Scraping imdb top 250...");
        List<Movie> movies = Scraper.fetchData(Movie.class);

        for (Movie m : movies) {
            movieDAO.saveMovie(m);
        }
        System.out.println("Done scraping imdb top 250."); */
    }
}