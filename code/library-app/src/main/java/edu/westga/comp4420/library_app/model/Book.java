package edu.westga.comp4420.library_app.model;


public class Book {
    private final String title;
    private final String author;
    private final String coverUrl;
    public Book(String title, String author) {
        if (title == null) {
            throw new IllegalArgumentException("title must not be null");
        }
        if (author == null) {
            throw new IllegalArgumentException("author must not be null");
        }
        this.title = title;
        this.author = author;
        this.coverUrl = "";
    }
    public Book(String title, String author, String coverUrl) {
        if (title == null) {
            throw new IllegalArgumentException("title must not be null");
        }
        if (author == null) {
            throw new IllegalArgumentException("author must not be null");
        }
        this.title = title;
        this.author = author;
        this.coverUrl = coverUrl;
    }
    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }
    public String getCoverUrl() {
        return this.coverUrl;
    }
}

