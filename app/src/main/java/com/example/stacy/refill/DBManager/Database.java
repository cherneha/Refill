package com.example.stacy.refill.DBManager;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.stacy.refill.Product;

import java.util.concurrent.Executors;

public class Database {
    private static Database database;
    private AppDatabase appDatabase;

    private Database(Context context){
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "database")
                .allowMainThreadQueries()
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                appDatabase.productDao().insertAll(
                                    new Product("Lime", 3, "kg"),
                                    new Product("Milk", 3.7, "l"),
                                    new Product("Bread", 1, "loaf"),
                                    new Product("Coffee", 5.6, "kg"),
                                    new Product("Salt", 8, "kg"),
                                    new Product("Water", 2.3, "l"),
                                    new Product("Tea", 1.7, "packet"));
                            }
                        });
                    }
                })
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
