package com.toure.santepourtous.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

/**
 * Created by Toure Nathan on 6/30/2018.
 */

@Database(entities = {SantePourTous.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final Object LOCK = new Object();
    private static final String LOG_TAC = AppDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "santepourtous_db";

    private static AppDatabase sInstance;

    public static AppDatabase getsInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAC, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAC, "Getting the database instance");
        return sInstance;
    }

    public abstract SantePourTousDao santePourTousDao();

}
