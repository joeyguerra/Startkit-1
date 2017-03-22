package com.thoughtworks.android.startkit;

import java.util.List;

interface BookListView {
    void setRefreshing(boolean refreshing);

    void loadData(List<BookItem> bookArray);

    void showLoadingMore();

    void hideLoadingMore();

    void loadMoreData(List<BookItem> bookArray);

    int getItemCount();
}
