package com.thoughtworks.android.startkit.retrofit;

import com.thoughtworks.android.startkit.BookItem;

/**
 * Created by zhuang on 12/03/2017.
 */

public class DouBanBook extends BookItem {
    DouBanResponseData.BooksBean mData;
    public DouBanBook(DouBanResponseData.BooksBean booksBean) {
        mData = booksBean;
    }

    @Override
    public String getItemTitle() {
        return mData.getTitle();
    }

    @Override
    public String getItemSummary() {
        return mData.getSummary();
    }

    @Override
    public double getItemRating() {
        return Double.valueOf(mData.getRating().getAverage());
    }

    @Override
    public String getItemImage() {
        return mData.getImages().getLarge();
    }

    @Override
    public String getItemAuthor() {
        return mData.getAuthor().toString();
    }

    @Override
    public String getItemPublisher() {
        return mData.getPublisher();
    }

    @Override
    public String getItemPublishDate() {
        return mData.getPubdate();
    }
}
