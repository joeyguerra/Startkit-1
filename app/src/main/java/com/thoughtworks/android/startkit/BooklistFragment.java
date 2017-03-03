package com.thoughtworks.android.startkit;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtworks.android.startkit.book.Data;
import com.thoughtworks.android.startkit.book.LoadDataTask;

import static java.util.Locale.ENGLISH;

public class BooklistFragment extends Fragment {

    private static final String TAG = "BooklistFragment";

    private static final String DATA_URL = "https://api.douban.com/v2/book/search?tag=%s&count=%d&start=%d";
    private static final String DATA_TAG = "IT";
    private static final int DATA_PER_PAGE = 40;
    private static final int DATA_INITIAL_START = 0;

    private RecyclerView mListView;
    private BooklistAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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

        doRefreshData();

        return view;
    }

    private String getDataUrl(int start) {
        return String.format(ENGLISH, DATA_URL, DATA_TAG, DATA_PER_PAGE, start);
    }

    private void doRefreshData() {
        new LoadDataTask() {

            @Override
            protected void onPostExecute(Data data) {
                super.onPostExecute(data);
                Log.d(TAG, String.valueOf(data.getBookArray().size()));
                mAdapter.addAll(data.getBookArray());
            }
        }.execute(getDataUrl(DATA_INITIAL_START));
    }
}
