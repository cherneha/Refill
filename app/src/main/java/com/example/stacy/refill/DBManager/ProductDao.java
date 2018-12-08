package com.example.stacy.refill.DBManager;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.stacy.refill.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("select * from Product")
    List<Product> getAll();

    @Query("select * from Product where id in (:productIds)")
    List<Product> loadAllByIds(int[] productIds);

    @Query("select * from Product where name like :name limit 1")
    Product findByName(String name);

    @Query("select * from Product order by currentQuantity limit :amount")
    List<Product> getOrderedProducts(int amount);

    @Insert
    void insertAll(Product... products);

    @Update
    void updateProduct (Product product);

    @Delete
    void delete(Product product);
}
