package com.thoughtworks.android.startkit;

import java.util.List;

public interface BookListView extends BaseView{
    void setRefreshing(boolean refreshing);

    void loadData(List<BookItem> bookArray);

    void showLoadingMore();

    void hideLoadingMore();

    void loadMoreData(List<BookItem> bookArray);

    int getItemCount();
}
