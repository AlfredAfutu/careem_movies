package com.careem.careemmovies.dependencyinjection.module;

import android.app.Application;

import com.careem.careemmovies.application.CareemMovies;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alfred afutu on 29/10/17.
 */
@Module
public class ApplicationModule {

    private Application application = CareemMovies.getInstance();

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }
}
