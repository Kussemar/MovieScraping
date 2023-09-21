package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "movie")
@ToString
public class Movie {

    @Id
    @Column(name = "imdb_id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "thumbnail_image_url")
    private String thumbnailImageURL;

    @Column(name = "rating")
    private double rating;

    @Column(name = "number_of_ratings")
    private int numberOfRatings;

    @Column(name = "release_year")
    private int releaseYear;

    @Column(name = "MPAA_rating")
    private String MPAArating;

    @Column(name = "duration")
    private String duration;

    @Builder
    public Movie(String title, String thumbnailImageURL, double rating, int numberOfRatings, int releaseYear, String MPAArating, String duration) {
        this.title = title;
        this.thumbnailImageURL = thumbnailImageURL;
        this.rating = rating;
        this.numberOfRatings = numberOfRatings;
        this.releaseYear = releaseYear;
        this.MPAArating = MPAArating;
        this.duration = duration;
    }
}
