package com.careem.careemmovies.dependencyinjection.component;

import com.careem.careemmovies.dependencyinjection.module.ApplicationModule;
import com.careem.careemmovies.dependencyinjection.module.NetworkModule;
import com.careem.careemmovies.viewmodel.MovieDetailViewModel;
import com.careem.careemmovies.viewmodel.MoviesViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by alfred afutu on 29/10/17.
 */
@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface NetworkComponent {
    void inject (MoviesViewModel moviesViewModel);
    void inject(MovieDetailViewModel movieDetailViewModel);
}
