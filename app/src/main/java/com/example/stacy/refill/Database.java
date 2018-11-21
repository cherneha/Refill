package com.example.stacy.refill;

import android.arch.persistence.room.Room;
import android.content.Context;

public class Database {
    private static Database database;
    private AppDatabase appDatabase;

    private Database(Context context){
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
    }

    synchronized public static Database getInstance(Context context){
        if(database == null){
            database = new Database(context);
        }

        return database;
    }

    public AppDatabase getAppDatabase(){
        return appDatabase;
    }
}
