package com.example.stacy.refill.DBManager;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.stacy.refill.ListItem;
import com.example.stacy.refill.Product;

@Database(entities = {Product.class, ListItem.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
    public abstract ListItemDao listItemDao();
}
