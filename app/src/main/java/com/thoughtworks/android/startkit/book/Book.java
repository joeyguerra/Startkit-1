package com.thoughtworks.android.startkit.book;

import com.thoughtworks.android.startkit.BookItem;

public class Book extends BookItem {
    private String title;
    private String image;
    private String author;
    private String publisher;
    private String publishDate;
    private String summary;
    private double rating;

    public Book(String title, String image, String author, String publisher, String publishDate, String summary, double rating) {
        this.title = title;
        this.image = image;
        this.author = author;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.summary = summary;
        this.rating = rating;
    }

    public String getItemTitle() {
        return title;
    }

    public String getItemImage() {
        return image;
    }

    public String getItemSummary() {
        return summary;
    }


    public String getItemAuthor() {
        return author;
    }

    public String getItemPublisher() {
        return publisher;
    }

    public String getItemPublishDate() {
        return publishDate;
    }

    public double getItemRating() {
        return rating;
    }
}
