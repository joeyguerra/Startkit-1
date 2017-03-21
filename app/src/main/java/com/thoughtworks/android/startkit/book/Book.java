package com.thoughtworks.android.startkit.book;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class Book implements Parcelable{
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

    protected Book(Parcel in) {
        title = in.readString();
        image = in.readString();
        author = in.readString();
        publisher = in.readString();
        publishDate = in.readString();
        summary = in.readString();
        rating = in.readDouble();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getSummary() {
        return summary;
    }

    public String getInformation() {
        return TextUtils.join(" / ", new String[]{getAuthor(), getPublisher(), getPublishDate()});
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(author);
        dest.writeString(publisher);
        dest.writeString(publishDate);
        dest.writeString(summary);
        dest.writeDouble(rating);
    }
}
