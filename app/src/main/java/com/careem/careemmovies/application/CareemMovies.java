package com.careem.careemmovies.application;

import android.app.Application;

import com.careem.careemmovies.dependencyinjection.component.DaggerNetworkComponent;
import com.careem.careemmovies.dependencyinjection.component.NetworkComponent;

/**
 * Created by alfred afutu on 29/10/17.
 */

public class CareemMovies extends Application {

    private static CareemMovies instance;
    private NetworkComponent networkComponent;

    public static CareemMovies getInstance () {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        networkComponent = DaggerNetworkComponent.create();
    }

    public NetworkComponent getNetworkComponent () {
        return networkComponent;
    }
}
