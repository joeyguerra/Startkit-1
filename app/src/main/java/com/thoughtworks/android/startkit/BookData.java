package com.thoughtworks.android.startkit;

import com.thoughtworks.android.startkit.retrofit.DouBanResponseData;

import java.util.List;

/**
 * Created by zhuang on 12/03/2017.
 */

public class BookData {
    public BookData(List<BookItem> items, int total, int count, int index) {
        this.books = items;
        this.bookTotal = total;
        this.bookCount = count;
        this.bookIndex = index;
    }

    List<BookItem> books;
    int bookTotal;
    int bookCount;
    int bookIndex;

    public List<BookItem> getBooks() {
        return books;
    }

    public int getTotal() {
        return bookTotal;
    }

    public int getCount() {
        return bookCount;
    }

    public int getStart() {
        return bookIndex;
    }
}
