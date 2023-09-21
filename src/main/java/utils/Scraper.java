package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
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


}
