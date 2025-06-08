package paint.projekt.sport_matcher.utils;

public class UrlBuilder {
    public static String buildUrl(String endpoint, int port) {
        return "http://localhost:" + port + endpoint;
    }
}

