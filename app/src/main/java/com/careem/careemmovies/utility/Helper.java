package com.careem.careemmovies.utility;

import android.content.Context;
import android.widget.ImageView;

import com.careem.careemmovies.dependencyinjection.module.NetworkModule;
import com.squareup.picasso.Picasso;

/**
 * Created by alfred afutu on 30/10/17.
 */

public class Helper {

    public static void loadPhotoWithPicasso(String width, String path, ImageView view, Context context) {
        String imagePath = NetworkModule.imageBaseUrl + width + path;
        Picasso.with(context).load(imagePath).into(view);
    }
}
