package com.careem.careemmovies.model.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.careem.careemmovies.model.dao.MovieDao;
import com.careem.careemmovies.model.entity.Movie;

/**
 * Created by alfred afutu on 29/10/17.
 */
@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class CareemMoviesDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();
    private static CareemMoviesDatabase INSTANCE;
    private static final String DATABASE_NAME = "careem_movies";

    public static synchronized CareemMoviesDatabase getDatabaseInstance(Context context) {
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, CareemMoviesDatabase.class, DATABASE_NAME).build();
        }
        return INSTANCE;
    }

    public void destroyDatabaseInstance () {
        INSTANCE = null;
    }
}
