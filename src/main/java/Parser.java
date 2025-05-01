import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Parser {
    static List<Game> games = new ArrayList<>();

    public List<Game> sortByName() {
        List<Game> sortedByName = new ArrayList<>(games);
        sortedByName.sort(Comparator.comparing(Game::getName));
        return sortedByName;
    }

    public List<Game> sortByRating() {
        List<Game> sortedByRating = new ArrayList<>(games);
        sortedByRating.sort((g1, g2) -> Double.compare(g2.getRating(), g1.getRating()));
        return sortedByRating;
    }

    public List<Game> sortByPrice() {
        List<Game> sortedByPrice = new ArrayList<>(games);
        sortedByPrice.sort((g1, g2) -> Integer.compare(g2.getPrice(), g1.getPrice()));
        return sortedByPrice;
    }

    public void setUp() throws IOException {
        File input = new File("src/Resources/Video_Games.html");
        Document doc = Jsoup.parse(input, "UTF-8");

        Elements gameElements = doc.select("div.game");
        for (Element gameElement : gameElements) {
            String name = gameElement.select("h3.game-name").text();

            String ratingText = gameElement.select("span.game-rating").text();
            double rating = Double.parseDouble(ratingText.split("/")[0]);

            String priceText = gameElement.select("span.game-price").text().replace("€", "").trim();
            int price = Integer.parseInt(priceText);

            games.add(new Game(name, rating, price));
        }
    }

    public static void main(String[] args) {
        Parser parser = new Parser();
        try {
            parser.setUp();

            System.out.println("Games sorted by name:");
            parser.sortByName().forEach(System.out::println);

            System.out.println("\nGames sorted by rating (highest first):");
            parser.sortByRating().forEach(System.out::println);

            System.out.println("\nGames sorted by price (highest first):");
            parser.sortByPrice().forEach(System.out::println);

        } catch (IOException e) {
            System.err.println("Error reading HTML file: " + e.getMessage());
        }
    }
}