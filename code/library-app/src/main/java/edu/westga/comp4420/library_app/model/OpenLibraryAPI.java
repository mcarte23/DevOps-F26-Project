package edu.westga.comp4420.library_app.model;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class OpenLibraryAPI {
    private static final String SEARCH_URL = "https://openlibrary.org/search.json";
    private static final String COVER_URL = "https://covers.openlibrary.org/b/olid/";
    private static final String SEARCH_FIELDS = "key,title,author_name,cover_edition_key,first_publish_year,number_of_pages_median,subject";
    private final HttpClient client = HttpClient.newHttpClient();

    public CompletableFuture<List<Book>> searchBooks(String query) {
        if (query.isEmpty()) {
            throw new IllegalArgumentException("query must not be empty");
        }
        String url = SEARCH_URL + "?q=" + URLEncoder.encode(query, StandardCharsets.UTF_8) + "&fields=" + SEARCH_FIELDS + "&limit=10";
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        return this.client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(response -> this.parseBooks(response.body()));
    }

    private List<Book> parseBooks(String responseBody) {
        JsonMapper mapper = JsonMapper.builder().build();
        List<Book> bookList = new ArrayList<Book>();
        try {
            JsonNode root = mapper.readTree(responseBody);
            for (JsonNode doc : root.get("docs")) {
                String title = doc.get("title").asText();
                JsonNode authors = doc.path("author_name");
                String first_author = authors.isArray() && !authors.isEmpty() ? authors.get(0).asText() : "Unknown";
                String cover_id = doc.path("cover_edition_key").asText();
                String url = !cover_id.isEmpty() ?  COVER_URL + cover_id + "-L.jpg" : "https://placehold.co/100x160.png?text=Placeholder";
                String firstPublishYear = doc.path("first_publish_year").asText();
                String pageCount = doc.path("number_of_pages_median").asText();
                Book book = new Book(title, first_author, url, firstPublishYear, pageCount);
                bookList.add(book);
            }
            return bookList;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
