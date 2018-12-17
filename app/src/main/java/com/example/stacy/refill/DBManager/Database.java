package com.example.stacy.refill.DBManager;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.stacy.refill.Product;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.Executors;

public class Database {
    private static Database database;
    private AppDatabase appDatabase;

    private Database(Context context){
        final Calendar calendar = Calendar.getInstance();
        calendar.set(2018, 12, 11);

        final Date six_days_ago = new GregorianCalendar(2018 , Calendar.DECEMBER, 12).getTime();
        final Date five_days_ago = new GregorianCalendar(2018, Calendar.DECEMBER, 13).getTime();
        final Date seven_days_ago = new GregorianCalendar(2018, Calendar.DECEMBER, 11).getTime();

        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "database")
                .allowMainThreadQueries()
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                appDatabase.productDao().insertAll(
                                    //new Product("Lime", 3, "kg"),
                                    //new Product("Milk", 3.7, "l"),
                                    //new Product("Bread", 1, "loaf"),
                                    //new Product("Coffee", 5.6, "kg"),
                                    //new Product("Salt", 8, "kg"),
                                    //new Product("Water", 2.3, "l"),
                                    //new Product("Tea", 1.7, "packet"));
                                        new Product("ShouldUpdate_100%_1", 1, "kg",
                                                six_days_ago, 10, false),
                                        new Product("ShouldUpdate_100%_2", 1, "kg",
                                                six_days_ago, 10, false),
                                        new Product("ShouldNotUpdate_200%", 2, "kg",
                                                six_days_ago, 10, false),
                                        new Product("ShouldUpdate_50%", 0.5, "kg",
                                                six_days_ago, 10, false),
                                        new Product("ShouldNotUpdate_100%", 1, "kg",
                                                five_days_ago, 10, false),
                                        new Product("ShouldUpdate_100%_3", 1, "kg",
                                                seven_days_ago, 10, false)
                                        );
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
