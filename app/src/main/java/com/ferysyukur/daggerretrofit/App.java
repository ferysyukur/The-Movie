package com.ferysyukur.daggerretrofit;

import android.app.Application;

import com.ferysyukur.daggerretrofit.injection.component.ApplicationComponent;
import com.ferysyukur.daggerretrofit.injection.component.DaggerApplicationComponent;
import com.ferysyukur.daggerretrofit.injection.module.ApplicationModule;
import com.ferysyukur.daggerretrofit.injection.module.NetworkModule;

/**
 * Created by ferysyukur on 5/26/17.
 */

public class App extends Application {

    private ApplicationComponent commponent;

    @Override
    public void onCreate() {
        super.onCreate();

        commponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule(BuildConfig.BASE_URL))
                .build();

    }

    public ApplicationComponent getAppCommponent(){
        return commponent;
    }
}
