package com.thoughtworks.android.startkit;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtworks.android.startkit.book.Data;
import com.thoughtworks.android.startkit.book.LoadDataTask;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static java.util.Locale.ENGLISH;

public class BooklistFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "BooklistFragment";

    private static final String DATA_URL = "https://api.douban.com/v2/book/search?tag=%s&count=%d&start=%d";
    private static final String DATA_TAG = "IT";
    private static final int DATA_PER_PAGE = 20;
    private static final int DATA_INITIAL_START = 0;

    private RecyclerView mListView;
    private BooklistAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private SwipeRefreshLayout swipeRefreshLayout;
    private View loadingView;

    private boolean isLoading;
    private boolean hasMoreItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);


        mListView = (RecyclerView) view.findViewById(android.R.id.list);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mListView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mListView.setLayoutManager(mLayoutManager);

        mAdapter = new BooklistAdapter();
        mListView.setAdapter(mAdapter);

        loadingView = view.findViewById(R.id.view_loading_more);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mListView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int firstVisibleItem = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();

                if (totalItemCount > 0) {
                    int lastVisibleItem = firstVisibleItem + visibleItemCount;
                    if (!isLoading && hasMoreItems && (lastVisibleItem == totalItemCount)) {
                        doLoadMoreData();
                    }
                }
            }
        });

        doRefreshData();

        return view;
    }

    private String getDataUrl(int start) {
        return String.format(ENGLISH, DATA_URL, DATA_TAG, DATA_PER_PAGE, start);
    }

    @Override
    public void onRefresh() {
        doRefreshData();
    }

    private void doRefreshData() {
        new LoadDataTask() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                isLoading = true;
                if (!swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(true);
                }
            }

            @Override
            protected void onPostExecute(Data data) {
                super.onPostExecute(data);
                isLoading = false;
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                hasMoreItems = data.getTotal() - (data.getStart() + data.getCount()) > 0;
                mAdapter.clearAll();
                mAdapter.addAll(data.getBookArray());
            }
        }.execute(getDataUrl(DATA_INITIAL_START));
    }

    private void doLoadMoreData() {
        Log.d(TAG, "load more data for ListView");

        new LoadDataTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showLoadingMore();
                isLoading = true;
            }

            @Override
            protected void onPostExecute(Data data) {
                super.onPostExecute(data);
                isLoading = false;
                hasMoreItems = data.getTotal() - (data.getStart() + data.getCount()) > 0;
                hideLoadingMore();
                mAdapter.addAll(data.getBookArray());
            }
        }.execute(getDataUrl(mAdapter.getItemCount()));

    }

    private void showLoadingMore() {
        loadingView.setVisibility(VISIBLE);
    }

    private void hideLoadingMore() {
        loadingView.setVisibility(GONE);
    }
}
