package com.ferysyukur.daggerretrofit.injection.component;

import com.ferysyukur.daggerretrofit.injection.module.ApplicationModule;
import com.ferysyukur.daggerretrofit.injection.module.NetworkModule;
import com.ferysyukur.daggerretrofit.ui.detail.DetailActivity;
import com.ferysyukur.daggerretrofit.ui.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ferysyukur on 5/26/17.
 */
@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

    void inject(MainActivity activity);

    void inject(DetailActivity activity);

}
