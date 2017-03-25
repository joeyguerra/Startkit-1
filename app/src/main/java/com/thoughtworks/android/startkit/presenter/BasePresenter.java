package com.thoughtworks.android.startkit.presenter;

import com.thoughtworks.android.startkit.BaseView;

/**
 * Created by binma on 24/03/2017.
 */

public interface BasePresenter {

    void onTakeView(BaseView view);

    void onDropView();
}
