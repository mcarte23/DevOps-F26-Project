package edu.westga.comp4420.library_app.model;


public class Book {
    private final String title;
    private final String author;
    private final String coverUrl;
    private final String firstPublishYear;
    private final String pageCount;
    public Book(String title, String author, String coverUrl, String firstPublishYear, String pageCount) {
        if (title == null) {
            throw new IllegalArgumentException("title must not be null");
        }
        if (author == null) {
            throw new IllegalArgumentException("author must not be null");
        }
        if (coverUrl == null) {
            throw new IllegalArgumentException("cover url must no be null");
        }
        this.title = title;
        this.author = author;
        this.coverUrl = coverUrl;
        this.firstPublishYear = firstPublishYear;
        this.pageCount = pageCount;
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

    public String getPublishYear() {
        return this.firstPublishYear;
    }

    public String getPageCount() {
        return this.pageCount;
    }
}

