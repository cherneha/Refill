package com.example.stacy.refill.DBManager;

import com.example.stacy.refill.Item;

public interface SyncDao<T extends Item> {
    int update(final T item);
    int delete(final T item);
    T findByName(final String name);
}
