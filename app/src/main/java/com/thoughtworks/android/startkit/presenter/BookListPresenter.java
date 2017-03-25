package com.thoughtworks.android.startkit.presenter;

import android.util.Log;

import com.thoughtworks.android.startkit.BaseView;
import com.thoughtworks.android.startkit.BookData;
import com.thoughtworks.android.startkit.BookListView;
import com.thoughtworks.android.startkit.retrofit.DouBanDataTask;

import static java.util.Locale.ENGLISH;

public class BookListPresenter implements BasePresenter {

    private BookListView view;
    private boolean isLoading;
    private boolean hasMoreItems;


    private static final String TAG = "BookListFragment";

    private static final String DATA_URL = "https://api.douban.com/v2/book/search?tag=%s&count=%d&start=%d";
    private static final String DATA_TAG = "IT";
    private static final int DATA_PER_PAGE = 20;
    private static final int DATA_INITIAL_START = 0;

    private DouBanDataTask refreshTask;
    private DouBanDataTask loadMoreTask;

    public BookListPresenter() {
    }

    public void refreshData() {
        refreshTask.execute(DATA_INITIAL_START);
    }


    private String getDataUrl(int start) {
        return String.format(ENGLISH, DATA_URL, DATA_TAG, DATA_PER_PAGE, start);
    }

    public void doLoadMoreData() {
        Log.d(TAG, "load more data for ListView");
        loadMoreTask.execute(view.getItemCount());
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

    private void startViewLoading(boolean isLoadingMore) {
        if (view != null) {
            view.setRefreshing(true);
            isLoading = true;
            if (isLoadingMore) {
                view.showLoadingMore();
            }
        }
    }

    private void finishViewLoading(BookData data, boolean isLoadingMore) {
        if (view != null) {
            isLoading = false;
            view.setRefreshing(false);
            hasMoreItems = data.getTotal() - (data.getStart() + data.getCount()) > 0;
            view.loadData(data.getBooks());
            if (isLoadingMore) {
                view.hideLoadingMore();
            }
        }
    }

    @Override
    public void onTakeView(BaseView view) {
        this.view = (BookListView) view;
        refreshTask = new DouBanDataTask(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                startViewLoading(false);
            }

            @Override
            protected void onPostExecute(BookData data) {
                super.onPostExecute(data);
                finishViewLoading(data, false);
            }
        };
        loadMoreTask = new DouBanDataTask(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                startViewLoading(true);
            }

            @Override
            protected void onPostExecute(BookData data) {
                super.onPostExecute(data);
                finishViewLoading(data, true);
            }
        };
    }

    @Override
    public void onDropView() {
        this.view = null;
        if (!refreshTask.isCancelled())  {
            refreshTask.cancel(true);
            refreshTask = null;
        }
        if (!loadMoreTask.isCancelled()) {
            loadMoreTask.cancel(true);
            loadMoreTask = null;
        }
    }
}
