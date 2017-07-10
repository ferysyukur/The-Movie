package com.ferysyukur.daggerretrofit.ui.base;

/**
 * Created by ferysyukur on 6/2/17.
 */

public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();

}
