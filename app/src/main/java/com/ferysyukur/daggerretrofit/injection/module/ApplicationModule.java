package com.ferysyukur.daggerretrofit.injection.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ferysyukur on 5/26/17.
 */

@Module
public class ApplicationModule {
    Application mApplication;

    public ApplicationModule(Application app){
        this.mApplication = app;
    }

    @Singleton
    @Provides
    Application provideApplication(){
        return mApplication;
    }
}
