package com.example.stacy.refill;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ListItemDao {
        @Query("select * from ListItem")
        List<ListItem> getAll();

        @Query("select * from ListItem where name like :name limit 1")
        ListItem findByName(String name);

        @Insert
        void insertAll(ListItem... listItems);

        @Update
        void updateListItem(ListItem listItem);

        @Delete
        void delete(ListItem listItem);
}
