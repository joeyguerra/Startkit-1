package com.thoughtworks.android.startkit;

import android.text.TextUtils;

/**
 * Created by zhuang on 12/03/2017.
 */

public abstract class BookItem {

    public abstract String getTitle();

    public abstract String getSummary();

    public abstract double getRating();

    public abstract String getImage();

    public abstract String getAuthor();

    public abstract String getPublisher();

    public abstract String getPublishDate();

    public String getInformation() {
        return TextUtils.join(" / ", new String[]{getAuthor(), getPublisher(), getPublishDate()});
    }
}
