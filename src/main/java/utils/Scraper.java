package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import model.Movie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static javax.management.Query.attr;
public class Scraper {

    static String url = "https://www.imdb.com/chart/top/?ref_=nv_mv_250";

    static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();


    public static List<Movie> fetchData() throws IOException, InterruptedException {
        List<Movie> IMDbMovieList = new ArrayList<>();
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                        "(KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36")
                .get();
        Thread.sleep(1000); // 1 second


        // vi starter med at finde den helt store container, som er en fællesklasse for alle filmene:
        // ipc-metadata-list ipc-metadata-list--dividers-between sc-3f13560f-0 sTTRj compact-list-view ipc-metadata-list--base - ul container
        Elements biggestContainer = doc.select("ul.sc-3f13560f-0");


        //Herefter finder vi den mindre kasse som hver film ligger i:
        // ipc-metadata-list-summary-item sc-59b6048d-0 jemTre cli-parent - li container
        Elements mediumContainer = biggestContainer.select("li.sc-59b6048d-0");


        //Vi løber mediumcontainer igennem som indeholder alt vi skal bruge ifht titler på de 250 film
        //Scraper alt:
        for(Element movieContainer : mediumContainer) {
            String title = movieContainer.select("h3.ipc-title__text").text();
            String imdbIdLink = movieContainer.select("a.ipc-title-link-wrapper").attr("href");
            String imageURL = movieContainer.select("img.ipc-image").attr("src");
            String ratingString = movieContainer.select("span.ipc-rating-star").attr("aria-label").replace("IMDb rating: ", "");
            String numberOfRatingsString = movieContainer.select("span.ipc-rating-star--voteCount").text();
            Element parentContainer = movieContainer.select("div.sc-b51a3d33-5").first();
            String releaseYearString = parentContainer.child(0).text();
            String duration = parentContainer.child(1).text();
            String mpaaRating = parentContainer.child(2).text();

            double rating = Double.parseDouble(ratingString);
            int releaseYear = Integer.parseInt(releaseYearString);

            //Target er det vi ønsker at replace -> replacement: hvad vi ønsker at replace MED
            numberOfRatingsString = numberOfRatingsString.replace("&nbsp;(", "").replace(")", "");

            // Hvis vi nu prøver at parse '2M' så vil den klage, så vi laver en metode til dette.
            // M laves om til mio og K om til tusind

            int numberOfRatings = parseNumberOfRatings(numberOfRatingsString);

            //laver imdbIdLink om
            //href="/title/tt0068646/?ref_=chttp_t_2"
            //batman: href="/title/tt0468569/?ref_=chttp_t_3"
            //Fjerner først /title, forefter at starte op på index 0, og køre hen til den rammer /, for derefter at slette alt
            //Substring tager fra de to ting du har valgt og gemmer fra 0 index

            String imdbId = imdbIdLink.replace("/title/", "");
            imdbId = imdbId.substring(0, imdbId.indexOf("/"));

            IMDbMovieList.add(new Movie(title, imageURL, rating, numberOfRatings, releaseYear, mpaaRating, duration));

        }

        return IMDbMovieList;
    }


    // M laves om til mio og K om til tusind
    // Og vi vil gerne have ,8 med,
    public static int parseNumberOfRatings(String pas) {
        char KMs = pas.charAt(pas.length() - 1);
        switch (KMs) {
            case 'K':
                double d = Double.parseDouble(pas.replace("K", "")) * 1000;
                return (int) d;
            case 'M':
                double d2 = Double.parseDouble(pas.replace("M", ""))* 1000000;
                return (int) d2;
            default:
                System.out.println("Does not contain K or M: " + pas);
                return Integer.parseInt(pas);
        }
    }
}
