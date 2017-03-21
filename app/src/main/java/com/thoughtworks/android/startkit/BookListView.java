package com.thoughtworks.android.startkit;

import com.thoughtworks.android.startkit.book.Book;

import java.util.List;

interface BookListView {
    void setRefreshing(boolean refreshing);

    void loadData(List<Book> bookArray);

    void showLoadingMore();

    void hideLoadingMore();

    void loadMoreData(List<Book> bookArray);

    int getItemCount();
}
