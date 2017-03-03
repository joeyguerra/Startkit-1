package com.thoughtworks.android.startkit.book;

import android.os.AsyncTask;

import static com.thoughtworks.android.startkit.book.Data.from;
import static com.thoughtworks.android.startkit.book.DataLoader.loadJSONData;

public class LoadDataTask extends AsyncTask<String, Void, Data> {
    @Override
    protected Data doInBackground(String... params) {
        return from(loadJSONData(params[0]));
    }
}
