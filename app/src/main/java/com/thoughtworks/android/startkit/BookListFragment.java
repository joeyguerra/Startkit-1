package com.thoughtworks.android.startkit;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtworks.android.startkit.book.Book;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class BookListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, BookListView{

    private RecyclerView mListView;
    private BookListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private SwipeRefreshLayout swipeRefreshLayout;
    private View loadingView;

    private BookListPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        presenter = new BookListPresenter(this);


        View view = inflater.inflate(R.layout.fragment_book_list, container, false);


        mListView = (RecyclerView) view.findViewById(android.R.id.list);
        mListView.setHasFixedSize(true);


        final int columns = getResources().getInteger(R.integer.gallery_columns);
        mLayoutManager = new GridLayoutManager(getContext(), columns);
        mListView.setLayoutManager(mLayoutManager);

        mAdapter = new BookListAdapter();
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

                presenter.loadMore(visibleItemCount, totalItemCount, firstVisibleItem);
            }
        });

        presenter.refreshData();

        return view;
    }

    @Override
    public void onRefresh() {
        presenter.refreshData();

    }

    public void showLoadingMore() {
        loadingView.setVisibility(VISIBLE);
    }

    public void hideLoadingMore() {
        loadingView.setVisibility(GONE);
    }

    @Override
    public void loadMoreData(List<Book> bookArray) {
        mAdapter.addAll(bookArray);
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount();
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        if (swipeRefreshLayout.isRefreshing() != refreshing) {
            swipeRefreshLayout.setRefreshing(refreshing);
        }
    }

    @Override
    public void loadData(List<Book> bookArray) {
        mAdapter.clearAll();
        mAdapter.addAll(bookArray);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }
}
