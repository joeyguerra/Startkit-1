package com.thoughtworks.android.startkit;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtworks.android.startkit.book.Data;

import static com.thoughtworks.android.startkit.book.Data.from;
import static com.thoughtworks.android.startkit.book.DataLoader.loadJSONData;

public class BooklistFragment extends Fragment {

    private static final String TAG = "BooklistFragment";

    private RecyclerView listView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);


        listView = (RecyclerView) view.findViewById(android.R.id.list);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        listView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        listView.setLayoutManager(mLayoutManager);


        new AsyncTask<Void, Void, Data>() {

            @Override
            protected Data doInBackground(Void... params) {
                return from(loadJSONData(getContext()));
            }

            @Override
            protected void onPostExecute(Data data) {
                super.onPostExecute(data);

                // specify an adapter (see also next example)
                mAdapter = new BooklistAdapter(data.getBookArray());
                listView.setAdapter(mAdapter);
            }
        }.execute();

        return view;
    }

}
