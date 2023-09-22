package org.example;

import config.HibernateConfig;
import dao.MovieDAO;
import model.Movie;
import utils.Scraper;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        HibernateConfig.addAnnotatedClasses(Movie.class);
        var emf = HibernateConfig.getEntityManagerFactoryConfig("imdb_movies");

        MovieDAO movieDAO = MovieDAO.getInstance(emf);

        System.out.println("Scraping imdb top 250...");
        List<Movie> movies = Scraper.fetchData();

        for (Movie m : movies) {
            movieDAO.saveMovie(m);
        }
    }
}