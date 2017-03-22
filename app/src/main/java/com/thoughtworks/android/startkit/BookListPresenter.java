package com.thoughtworks.android.startkit;

import android.util.Log;

import com.thoughtworks.android.startkit.book.Data;
import com.thoughtworks.android.startkit.book.LoadDataTask;
import com.thoughtworks.android.startkit.retrofit.DouBanDataTask;

import static java.util.Locale.ENGLISH;

class BookListPresenter {


    private BookListView view;
    private boolean isLoading;
    private boolean hasMoreItems;


    private static final String TAG = "BookListFragment";

    private static final String DATA_URL = "https://api.douban.com/v2/book/search?tag=%s&count=%d&start=%d";
    private static final String DATA_TAG = "IT";
    private static final int DATA_PER_PAGE = 20;
    private static final int DATA_INITIAL_START = 0;

    public BookListPresenter(BookListView view) {

        this.view = view;
    }

    public void refreshData() {
        new DouBanDataTask() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                isLoading = true;
                view.setRefreshing(true);
            }

            @Override
            protected void onPostExecute(BookData data) {
                super.onPostExecute(data);
                isLoading = false;
                view.setRefreshing(false);
                hasMoreItems = data.getTotal() - (data.getStart() + data.getCount()) > 0;
                view.loadData(data.getBooks());
            }
        }.execute(DATA_INITIAL_START);
    }


    private String getDataUrl(int start) {
        return String.format(ENGLISH, DATA_URL, DATA_TAG, DATA_PER_PAGE, start);
    }

    public void doLoadMoreData() {
        Log.d(TAG, "load more data for ListView");

        new DouBanDataTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                view.showLoadingMore();
                isLoading = true;
            }

            @Override
            protected void onPostExecute(BookData data) {
                super.onPostExecute(data);
                isLoading = false;
                hasMoreItems = data.getTotal() - (data.getStart() + data.getCount()) > 0;
                view.hideLoadingMore();
                view.loadMoreData(data.getBooks());
            }
        }.execute(view.getItemCount());
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean hasMoreItems() {
        return hasMoreItems;
    }


    public void loadMore(int visibleItemCount, int totalItemCount, int firstVisibleItem) {
        if (totalItemCount > 0) {
            int lastVisibleItem = firstVisibleItem + visibleItemCount;
            if (!isLoading() && hasMoreItems() && (lastVisibleItem == totalItemCount)) {
                doLoadMoreData();
            }
        }
    }
}
